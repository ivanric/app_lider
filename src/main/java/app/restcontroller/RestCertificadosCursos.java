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
@RequestMapping("/RestCertificadosCursos") 
public class RestCertificadosCursos extends RestControllerGenericNormalImpl<CertificadoEntity,CertificadoCursoServiceImpl> {

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
    public RestCertificadosCursos(DataSource dataSource) {
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
	 
	@GetMapping("/listar")
	public ResponseEntity<?> getAll_listar(HttpServletRequest request,@Param("draw")int draw,@Param("length")int length,@Param("start")int start,@Param("estado")int estado,@Param("idevento")int idevento,@Param("idcategoria")int idcategoria,@Param("idanio")int idanio)throws IOException{
		String total="";
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			System.out.println("LISTAR CURSOS DE LOS INSCRITOS");
			String search = request.getParameter("search[value]");
			int tot=Constantes.NUM_MAX_DATATABLE;
			System.out.println("tot:"+tot+"estado:"+estado+"search:"+search+"length:"+length+"start:"+start+"idevento:"+idevento+"idcategoria:"+idcategoria+"idanio:"+idanio);
			List<?> lista= servicio.findAll_listar_cursos(estado, search,idevento,idcategoria,idanio, length, start);
			System.out.println("listar CURSOS DE INSCRITOS:"+lista.toString()); 
			try {

					total=String.valueOf(servicio.getTotAll_cursos(estado,search,idevento,idcategoria,idanio));	
						
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
	
	
	@GetMapping("/getEventByIdAnio/{idAnio}")
	public ResponseEntity<?> getEventByIdAnio( @PathVariable Integer idAnio) {
	    System.out.println(", ID Año: " + idAnio);
	    List<?> lista = new ArrayList<>();
	    try {
	        lista = servicio.getEventByIdAnio(idAnio);
	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lista);
	    }
	}

	
	
	@GetMapping("/listarbyParticipante")
	public ResponseEntity<?> getAll(HttpServletRequest request,@Param("draw")int draw,@Param("length")int length,@Param("start")int start,@Param("estado")int estado,@Param("idevento")int idevento,@Param("idcategoria")int idcategoria,@Param("idanio")int idanio,@Param("idparticipante")int idparticipante)throws IOException{
		String total="";
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			System.out.println("LISTAR CURSOSS");
			String search = request.getParameter("search[value]");
			int tot=Constantes.NUM_MAX_DATATABLE;
			System.out.println("tot:"+tot+"estado:"+estado+"search:"+search+"length:"+length+"start:"+start+"idevento:"+idevento+"idcategoria:"+idcategoria+"idanio:"+idanio+"idparticipante:"+idparticipante);
			List<?> lista= servicio.findAll_m(estado, search,idevento,idcategoria,idanio,idparticipante, length, start);
			List<?> listaidcertificados= servicio.getIdCertiByPart(estado, search,idevento,idcategoria,idanio,idparticipante);
//			List<?> lista= servicio.findAll(estado, search,idevento,idcategoria,idanio,idparticipante, length, start);
			System.out.println("listar:"+lista.toString()); 
			try {

					total=String.valueOf(servicio.getTotAll(estado,search,idevento,idcategoria,idanio,idparticipante));	
						
			} catch (Exception e) {
				total="0";
			}
			Data.put("draw", draw);
			Data.put("recordsTotal", total);
			Data.put("data", lista);
			Data.put("listaidcertificados", listaidcertificados);
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
	
	
	@GetMapping("/listarbyCurso")
	public ResponseEntity<?> listarbyCurso(HttpServletRequest request,@Param("draw")int draw,@Param("length")int length,@Param("start")int start,@Param("estado")int estado,@Param("idevento")int idevento,@Param("idcategoria")int idcategoria,@Param("idanio")int idanio,@Param("idcurso")int idcurso)throws IOException{
		String total="";
		Map<String, Object> Data = new HashMap<String, Object>();
		try {
			System.out.println("LISTAR CURSOSSsssssss");
			String search = request.getParameter("search[value]");
			int tot=Constantes.NUM_MAX_DATATABLE;
			System.out.println("tot:"+tot+"estado:"+estado+"search:"+search+"length:"+length+"start:"+start+"idevento:"+idevento+"idcategoria:"+idcategoria+"idanio:"+idanio+"idcurso:"+idcurso);
			List<?> lista= servicio.findAll_m_curso(estado, search,idevento,idcategoria,idanio,idcurso, length, start);
			List<?> listaidcertificados= servicio.getIdCertiByCurso(estado, search,idevento,idcategoria,idanio,idcurso);
//			List<?> lista= servicio.findAll(estado, search,idevento,idcategoria,idanio,idparticipante, length, start);
			System.out.println("listar:"+lista.toString()); 
			try {
				total=String.valueOf(servicio.getTotAll_curso(estado,search,idevento,idcategoria,idanio,idcurso));					
			} catch (Exception e) {
				total="0";
			}
			Data.put("draw", draw);
			Data.put("recordsTotal", total);
			Data.put("data", lista);
			Data.put("listaidcertificados", listaidcertificados);
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
	

	@GetMapping("/getCategoryByIdAnio/{idEvent}/{idAnio}")
	public ResponseEntity<?> getCategoryByIdAnio(@PathVariable Integer idEvent, @PathVariable Integer idAnio){
	    System.out.println("ID Evento: " + idEvent + ", ID Año: " + idAnio);
	    List<?> lista = new ArrayList<>();
	    try {
	        lista = servicio.getCategoryByIdAnio(idEvent,idAnio);
	        return ResponseEntity.ok(lista);
	    } catch (Exception e){
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lista);
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
    
    @PostMapping("/renovarqr")
    public ResponseEntity<?> renovarqr(@RequestBody CertificadoEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
//        	servicio.updateStatus(entity.getEstado(), entity.getId());
//        	CertificadoEntity entity2=servicio.findById(entity.getId());
//            return ResponseEntity.status(HttpStatus.OK).body(entity2);
//            return ResponseEntity.status(HttpStatus.OK).body(entity2);
            return ResponseEntity.status(HttpStatus.OK).body(servicio.renovarQR(entity));
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @GetMapping("/findByNrodocumento"+"/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id){ 
        try { 
        	System.out.println("ID A BUSCAR");
        	Map<String, Object> entity=new HashMap<>();
        	entity=servicio.findByNrodocumento(id);
        	if (entity!=null) {
        		System.out.println("certificado encontrado:"+entity.toString());	
			}
        	
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @RequestMapping(value = "Imprimir_d1/{id}", method = RequestMethod.GET)
    public void imprimirCertificadoConImagenes(HttpServletResponse response,@PathVariable("id") Integer id) {
        try {
            // Parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            
            // Obtener el certificado
            CertificadoEntity certificadoEntity = servicio.findById(id);
            if (certificadoEntity == null) {
                throw new RuntimeException("Certificado no encontrado para el ID: " + id);
            }

            // Descargar y guardar imágenes en archivos temporales
            File plantillaFile = descargarYGuardarTemporal(
                Constantes.nameFolderLogoCurso + "/" + certificadoEntity.getCurso().getImagencertificado(), 
                "plantilla"
            );

            File qrFile = descargarYGuardarTemporal(
                Constantes.nameFolderQrIncritoCertificado + "/" + certificadoEntity.getLinkqr(), 
                "codigoQR"
            );

            parametros.put("idcert_param", id);
            parametros.put("dirPhotho_param", plantillaFile.getAbsolutePath());
            parametros.put("dirPhothoQr_param", qrFile.getAbsolutePath());

            // Manejo de la imagen del usuario si está disponible
            if (certificadoEntity.getParticipante().getImagen() != null) {
                File userFile = descargarYGuardarTemporal(
                    Constantes.nameFolderLogoParticipante + "/" + certificadoEntity.getParticipante().getImagen(),
                    "fotoUsuario"
                );
                parametros.put("dirPhothoUser_param", userFile.getAbsolutePath());
            } else {
                parametros.put("dirPhothoUser_param", null);
            }

            // Cargar y compilar el reporte JRXML
            URIS ubicacion = new URIS();
            String urlReporte = ubicacion.jasperReport + "certificadobyid.jrxml";
            InputStream jrxmlStream = getClass().getClassLoader().getResourceAsStream(urlReporte);
            if (jrxmlStream == null) {
                throw new RuntimeException("No se pudo encontrar el archivo JRXML.");
            }

            JasperReport reporte = JasperCompileManager.compileReport(jrxmlStream);

            // Conexión a la base de datos
            try (Connection connection = dataSource.getConnection()) {
                GeneradorReportes generador = new GeneradorReportes();
                String formato = "pdf";
                String nombreReporte = "CERTIFICADO";
                String descarga = "inline";
                generador.generarReporte(response, reporte, formato, parametros, connection, nombreReporte, descarga);
            }

        } catch (Exception e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // Método para descargar archivos desde S3 y guardarlos como temporales
    private File descargarYGuardarTemporal(String key, String descripcion) throws IOException {
        System.out.println("Descargando " + descripcion + " desde S3: " + key);
        
        try (InputStream stream = s3Service.downloadFileFromS3(key)) {
            if (stream == null) {
                throw new RuntimeException("No se pudo descargar " + descripcion + " desde S3.");
            }

            File tempFile = File.createTempFile(descripcion, ".png");
            Files.copy(stream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        }
    }
    
    
    
    

    
    @PostMapping("/Imprimir_certificados")
    public void recibirCertificadosMarcados(@RequestBody Map<String, List<Integer>> requestData, HttpServletResponse response) {
        List<Integer> selectedIds = requestData.get("ids");

        if (selectedIds == null || selectedIds.isEmpty()) {
            try {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("No se enviaron IDs válidos.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            
        	// **Ordenar selectedIds antes de procesarlo**
            Collections.sort(selectedIds);
        	// Parámetros del reporte
            Map<String, Object> parametros = new HashMap<>();
            List<File> fotoPlantillas = new ArrayList<>();
            List<File> fotoUsuarios = new ArrayList<>(); 
            List<File> fotosQr = new ArrayList<>();

            // Recopilar imágenes desde S3
            for (Integer id : selectedIds) {
                CertificadoEntity certificadoEntity = servicio.findById(id);
//                CertificadoEntity certificadoEntity = servicio.findById(id);
                if (certificadoEntity == null) {
                    throw new RuntimeException("Certificado no encontrado para el ID: " + id);
                }

                // Descargar imágenes y guardar en archivos temporales
                fotoPlantillas.add(
                    descargarYGuardarTemporal(Constantes.nameFolderLogoCurso + "/" + certificadoEntity.getCurso().getImagencertificado(), "plantilla")
                );

                fotosQr.add(
                    descargarYGuardarTemporal(Constantes.nameFolderQrIncritoCertificado + "/" + certificadoEntity.getLinkqr(), "codigoQR")
                );

                if (certificadoEntity.getParticipante().getImagen() != null) {
                    fotoUsuarios.add(
                        descargarYGuardarTemporal(Constantes.nameFolderLogoParticipante + "/" + certificadoEntity.getParticipante().getImagen(), "fotoUsuario")
                    );
                } else {
                    fotoUsuarios.add(null); // Si no hay imagen de usuario
                }
            }
//            Integer[] certificadosIdsArray = selectedIds.toArray(selectedIds);
            // Agregar parámetros al reporte
            parametros.put("certificadosIds", selectedIds.toArray(new Integer[0]));//dato para
            parametros.put("fotoPlantillas", fotoPlantillas.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
            parametros.put("fotoUsuarios", fotoUsuarios.stream().map(file -> file != null ? file.getAbsolutePath() : null).collect(Collectors.toList()));
            parametros.put("fotosQr", fotosQr.stream().map(File::getAbsolutePath).collect(Collectors.toList()));
            String formato = "pdf";
            String nombreReporte = "CERTIFICADO";
            String descarga = "inline"; // Puede ser "attachment" para descarga directa

            // Cargar y compilar el archivo JRXML
            URIS ubicacion = new URIS();
            String urlReporte = ubicacion.jasperReport + "certificadosByIds.jrxml";
            InputStream jrxmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(urlReporte);

            if (jrxmlStream == null) {
                throw new RuntimeException("No se pudo encontrar el archivo JRXML.");
            }

            JasperReport reporte = JasperCompileManager.compileReport(jrxmlStream);

            // Generar el PDF usando JasperReports
            try (Connection connection = dataSource.getConnection()) {
                GeneradorReportes generador = new GeneradorReportes();
                generador.generarReporte(response, reporte, formato, parametros, connection, nombreReporte, descarga);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("Error al generar los certificados: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }





    
}
