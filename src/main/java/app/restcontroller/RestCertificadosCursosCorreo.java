package app.restcontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.Drive;
import org.springframework.http.HttpStatus;
import app.dto.ParticipanteDTO;
import app.entity.CertificadoEntity;
import app.entity.PersonaEntity;
import app.service.CertificadoCursoServiceImpl;
import app.service.DepartamentoService;
import app.service.GradoAcademicoService;
import app.service.MailService;
//import app.service.ArchivoService;
import app.service.ProfesionService;
import app.service.S3Service;
import app.util.Constantes;
//import app.util.Constantes;
import app.util.GeneradorReportes;
import app.util.URIS;
//import app.util.URIS;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
//@EnableAsync  // Habilita ejecución asíncrona
@RestController
@RequestMapping("/RestCertificadosCursosCorreos") 
public class RestCertificadosCursosCorreo extends RestControllerGenericNormalImpl<CertificadoEntity,CertificadoCursoServiceImpl> {

	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
	@Autowired DepartamentoService departamentoService;
//	@Autowired private ArchivoService archivoService;
    @Autowired private S3Service s3Service;
    
	@Autowired
	private MailService mailService;
    
    private final DataSource dataSource;
    
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
   
    private JasperReport reporte;
    
    @Autowired
    public RestCertificadosCursosCorreo(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    @PostConstruct
    public void cargarReporte() {
        URIS ubicacion = new URIS();
        String urlReporte = ubicacion.jasperReport + "certificadobyid.jrxml";

        try (InputStream jrxmlStream = getClass().getClassLoader().getResourceAsStream(urlReporte)) {
            if (jrxmlStream == null) {
                throw new RuntimeException("No se encontró el archivo .jrxml.");
            }

            // Compilar el .jrxml a un JasperReport en memoria
            reporte = JasperCompileManager.compileReport(jrxmlStream);
            System.out.println("Reporte compilado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error compilando el reporte Jasper.", e);
        }
    }

    public JasperReport getReporte() {
        return reporte;
    }
	 
	
    
    @RequestMapping(value = "enviarcertificado", method = RequestMethod.POST)
    public ResponseEntity<?> enviarcertificado(@RequestParam Integer idcertificado) {
        try {
            System.out.println("Generando certificado para ID: " + idcertificado);

            // Generar el PDF
            File pdfFile = generarCertificadoPDF(idcertificado);
            if (pdfFile == null || !pdfFile.exists()) {
                throw new RuntimeException("Error al generar el PDF.");
            }

            // Enviar el correo
            enviarCorreoCertificado(idcertificado, pdfFile);

            return ResponseEntity.status(HttpStatus.OK).body("Certificado enviado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, intente más tarde.\"}");
        }
    }
//    @Async
    public void enviarCorreoCertificado(Integer idcertificado, File pdfFile) {
        try {
            CertificadoEntity certificadoEntity = servicio.findById(idcertificado);
            if (certificadoEntity == null) return;

            Map<String, Object> variables = Map.of(
                "nombre", certificadoEntity.getParticipante().getPersona().getNombres() + " " + certificadoEntity.getParticipante().getPersona().getApellidos(),
                "detalle", Optional.ofNullable(certificadoEntity.getEvento().getDetalleemail()).orElse(Constantes.DETALLE_EMAIL_CERTIFICADO),
                "evento", certificadoEntity.getCurso().getNombrecurso()+"/"+certificadoEntity.getEvento().getDetalle(),
                "numero", certificadoEntity.getCurso().getCategoria().getContacto(),
                "fecha", certificadoEntity.getEvento().getFechainicial(),
                "fecha2", certificadoEntity.getEvento().getFechafinal(),
                "pagina", Constantes.NOMBRE_PAGINA_EMAIL
            );

            mailService.sendEmailWithAttachment(
                certificadoEntity.getParticipante().getPersona().getEmail(),
                "CERTIFICADO DIGITAL",
                "email-certificado.html",
                variables,
                "no-reply@academialider.com",
                pdfFile
            );
            
            //actualizar el estado del certificado enviado
            certificadoEntity.setCertificadoenviado(1);
            servicio.save(certificadoEntity);
            
            System.out.println("Correo enviado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File generarCertificadoPDF(Integer id) {
        try {
            CertificadoEntity certificadoEntity = servicio.findById(id);
            System.out.println("CERTIFICADO:"+certificadoEntity.toString());
            if (certificadoEntity == null) throw new RuntimeException("Certificado no encontrado para el ID: " + id);

            // Iniciar las descargas en paralelo
            CompletableFuture<File> plantillaFileFuture = descargarYGuardarTemporalEnHilo(
                Constantes.nameFolderLogoCurso + "/" + certificadoEntity.getCurso().getImagencertificado(),
                "plantilla"
            );
            CompletableFuture<File> qrFileFuture = descargarYGuardarTemporalEnHilo(
                Constantes.nameFolderQrIncritoCertificado + "/" + certificadoEntity.getLinkqr(),
                "codigoQR"
            );
            CompletableFuture<File> userFileFuture = certificadoEntity.getParticipante().getImagen() != null
                ? descargarYGuardarTemporalEnHilo(
                    Constantes.nameFolderLogoParticipante + "/" + certificadoEntity.getParticipante().getImagen(),
                    "fotoUsuario"
                )
                : CompletableFuture.completedFuture(null); // No descarga si no hay imagen

            // Esperar a que todas las descargas terminen
            CompletableFuture.allOf(plantillaFileFuture, qrFileFuture, userFileFuture).join();

            // Obtener los resultados
            File plantillaFile = plantillaFileFuture.get();
            File qrFile = qrFileFuture.get();
            File userFile = userFileFuture.get();

            // Parámetros para JasperReports
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idcert_param", id);
            parametros.put("dirPhotho_param", plantillaFile.getAbsolutePath());
            parametros.put("dirPhothoQr_param", qrFile.getAbsolutePath());
            parametros.put("dirPhothoUser_param", userFile != null ? userFile.getAbsolutePath() : null);

            // Generar el PDF
            File pdfTempFile = File.createTempFile("certificado_", ".pdf");
            try (Connection connection = dataSource.getConnection()) {
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, connection);
                JasperExportManager.exportReportToPdfFile(jasperPrint, pdfTempFile.getAbsolutePath());
            }

            return pdfTempFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CompletableFuture<File> descargarYGuardarTemporalEnHilo(String key, String descripcion) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Descargando " + descripcion + " desde S3: " + key);
                try (InputStream stream = s3Service.downloadFileFromS3(key)) {
                    if (stream == null) throw new RuntimeException("Error al descargar " + descripcion);

                    File tempFile = File.createTempFile(descripcion, ".png");
                    Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    return tempFile;
                }
            } catch (Exception e) {
                throw new RuntimeException("Error descargando " + descripcion, e);
            }
        }, executor); // Usar el ExecutorService existente
    }

    @PostMapping("/enviar_certificados")
    public ResponseEntity<?> recibirCertificadosMarcados(@RequestBody Map<String, List<Integer>> requestData) {
        List<Integer> selectedIds = requestData.get("ids");

        if (selectedIds == null || selectedIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se enviaron IDs válidos.");
        }

        try {
            // Ordenar los IDs antes de procesarlos
            Collections.sort(selectedIds);

            // Lista para almacenar posibles errores en la generación/envío de certificados
            List<String> errores = new ArrayList<>();

            // Procesar cada certificado individualmente
            for (Integer id : selectedIds) {
                try {
                    CertificadoEntity certificadoEntity = servicio.findById(id);
                    if (certificadoEntity == null) {
                        errores.add("Certificado no encontrado para el ID: " + id);
                        continue;
                    }

                    // Generar el PDF del certificado
                    File pdfFile = generarCertificadoPDF(id);
                    if (pdfFile == null || !pdfFile.exists()) {
                        errores.add("Error al generar el certificado para el ID: " + id);
                        continue;
                    }

                    // Enviar el certificado por correo de manera asíncrona
                    enviarCorreoCertificados(certificadoEntity, pdfFile);
                   
                } catch (Exception e) {
                    errores.add("Error procesando el certificado para ID: " + id + " - " + e.getMessage());
                    e.printStackTrace();
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body("Certificado enviado correctamente.");
      

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error general al procesar los certificados.");
        }
    }
    
//    @Async
    public void enviarCorreoCertificados(CertificadoEntity certificadoEntity, File pdfFile) {
        try {
            // Construir variables para la plantilla de email
            Map<String, Object> variables = Map.of(
                "nombre", certificadoEntity.getParticipante().getPersona().getNombres() + " " + certificadoEntity.getParticipante().getPersona().getApellidos(),
                "detalle", Optional.ofNullable(certificadoEntity.getEvento().getDetalleemail()).orElse(Constantes.DETALLE_EMAIL_CERTIFICADO),
                "evento", certificadoEntity.getEvento().getDetalle() +" - "+certificadoEntity.getCurso().getNombrecurso(),
                "fecha", certificadoEntity.getEvento().getFechainicial(),
                "fecha2", certificadoEntity.getEvento().getFechafinal(),
                "pagina", Constantes.NOMBRE_PAGINA_EMAIL
            );

            // Enviar email con el certificado adjunto
            mailService.sendEmailWithAttachment(
                certificadoEntity.getParticipante().getPersona().getEmail(),
                "CERTIFICADO DIGITAL",
                "email-certificado.html",
                variables,
                "no-reply@academialider.com",
                pdfFile
            );

            System.out.println("Correo enviado a: " + certificadoEntity.getParticipante().getPersona().getEmail());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
