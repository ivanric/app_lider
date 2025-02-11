package app.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//import app.config.GoogleDriveConfig;
import app.dto.ParticipanteDTO;
import app.entity.CertificadoEntity;
import app.entity.PersonaEntity;
import app.service.DepartamentoService;
import app.service.GradoAcademicoService;
//import app.service.ArchivoService;
import app.service.CertificadoCursoServiceImpl;
import app.service.ProfesionService;
import app.util.Constantes;
import app.util.GeneradorReportes;
import app.util.URIS;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
/*
@RestController
@RequestMapping("/RestCertificadosCursosbck") 
public class RestCertificadosCursosbck extends RestControllerGenericNormalImpl<CertificadoEntity,CertificadoCursoServiceImpl> {

	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
	@Autowired DepartamentoService departamentoService;
	
	
	@Autowired private ArchivoService archivoService;
	
	@Autowired
    private GoogleDriveConfig googleDriveConfig;

	//drive
    private Drive getDriveService() throws IOException, GeneralSecurityException {
        return googleDriveConfig.getDriveService();
    }
	
	@GetMapping("/listarbyParticipante")
	public ResponseEntity<?> getAll(HttpServletRequest request,@Param("draw")int draw,@Param("length")int length,@Param("start")int start,@Param("estado")int estado,@Param("idevento")int idevento,@Param("idcategoria")int idcategoria,@Param("idanio")int idanio,@Param("idparticipante")int idparticipante)throws IOException{
		String total="";
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			System.out.println("LISTAR CURSOSS");
			String search = request.getParameter("search[value]");
			int tot=Constantes.NUM_MAX_DATATABLE;
			System.out.println("tot:"+tot+"estado:"+estado+"search:"+search+"length:"+length+"start:"+start);
			List<?> lista= servicio.findAll_m(estado, search,idevento,idcategoria,idanio,idparticipante, length, start);
			System.out.println("listar:"+lista.toString()); 
			try {

					total=String.valueOf(servicio.getTotAll(estado,search,idevento,idcategoria,idanio,idparticipante));	
						
			} catch (Exception e) {
				total="0";
			}
			Data.put("draw", draw);
			Data.put("recordsTotal", total);
			Data.put("data", lista);
			if(!search.equals(""))
				Data.put("recordsFiltered", lista.size());
			else
				Data.put("recordsFiltered", total);
			
			return ResponseEntity.status(HttpStatus.OK).body(Data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
		}
	}

	@GetMapping("/getCategoryById/{idPart}/{idEvent}/{idAnio}")
	public ResponseEntity<?> getCatogoryById(@PathVariable Integer idPart,@PathVariable Integer idEvent, @PathVariable Integer idAnio) {
	    System.out.println("ID Participante: " + idPart+"ID Evento: " + idEvent + ", ID Año: " + idAnio);
	    List<?> lista = new ArrayList<>();
	    try {
	        lista = servicio.getCategoryById(idEvent,idAnio,idPart);
	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lista);
	    }
	}
	@GetMapping("/getEventById/{idPart}/{idAnio}")
	public ResponseEntity<?> getEventById(@PathVariable Integer idPart, @PathVariable Integer idAnio) {
	    System.out.println("ID Participante: " + idPart + ", ID Año: " + idAnio);
	    List<?> lista = new ArrayList<>();
	    try {
	        lista = servicio.getEventoById(idAnio,idPart);
	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lista);
	    }
	}

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody CertificadoEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	CertificadoEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @Autowired
    DataSource dataSource;

    @RequestMapping(value = "Imprimir_d1/{id}", method = RequestMethod.GET)
    public void imprimirCertificadoConImagenes(
            HttpServletResponse response,
            @PathVariable("id") Integer id) throws Exception {

        URIS ubicacion = new URIS();
        final String nombreReporte = "CERTIFICADO";
        final String formato = "pdf"; // Cambiar según el formato deseado
        final String estado = "inline"; // Cambiar a "attachment" si quieres forzar la descarga del archivo

        // Obtener IDs de las carpetas en Google Drive
        String folderIdPlantilla = archivoService.getOrCreateFolder(Constantes.nameFolderLogoCurso);
        String folderIdParticipante = archivoService.getOrCreateFolder(Constantes.nameFolderLogoParticipante);
        String folderIdQr = archivoService.getOrCreateFolder(Constantes.nameFolderQrIncritoCertificado);

        // Obtener la entidad del certificado
        CertificadoEntity certificadoEntity = servicio.findById(id);

        // Obtener los IDs de las imágenes desde Google Drive
        String fileIdPhotoPlantilla = archivoService.obtenerIdArchivoDrivePorNombre(
                certificadoEntity.getCurso().getImagencertificado(), folderIdPlantilla);
        String fileIdPhotoUser = archivoService.obtenerIdArchivoDrivePorNombre(
                certificadoEntity.getParticipante().getImagen(), folderIdParticipante);
        String fileIdPhotoQr = archivoService.obtenerIdArchivoDrivePorNombre(
                certificadoEntity.getLinkqr(), folderIdQr);

        // Servicio de Google Drive
        Drive driveService = getDriveService();

        // Manejo de InputStreams con try-with-resources
        try (
            InputStream plantillaStream = driveService.files().get(fileIdPhotoPlantilla).executeMediaAsInputStream();
            InputStream userStream = driveService.files().get(fileIdPhotoUser).executeMediaAsInputStream();
            InputStream qrStream = driveService.files().get(fileIdPhotoQr).executeMediaAsInputStream()) {

            // Parámetros del reporte
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idcert_param", id);
            parametros.put("dirPhotho_param", plantillaStream); // Plantilla de certificado
            parametros.put("dirPhothoUser_param", userStream); // Foto del usuario
            parametros.put("dirPhothoQr_param", qrStream); // Código QR

            // Ruta del archivo JRXML
            String urlReporte = ubicacion.jasperReport + "certificadobyid.jrxml"; // Reemplaza por la ruta correcta

            // Compilar el archivo .jrxml a .jasper en tiempo real
            JasperReport reporte = JasperCompileManager.compileReport(getClass().getResourceAsStream(urlReporte));

            // Generar y exportar el reporte
            GeneradorReportes generador = new GeneradorReportes();
            generador.generarReporte(response, reporte, formato, parametros, dataSource.getConnection(), nombreReporte, estado);

            System.out.println("Reporte generado exitosamente con imágenes de Google Drive.");
        } catch (Exception e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
            System.setProperty("net.sf.jasperreports.debug", "true"); // Activa el debug para JasperReports si es necesario
        }
    }


   

}*/
