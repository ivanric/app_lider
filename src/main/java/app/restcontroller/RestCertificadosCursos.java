package app.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.Connection;
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

import app.dto.ParticipanteDTO;
import app.entity.CertificadoEntity;
import app.entity.PersonaEntity;
import app.service.DepartamentoService;
import app.service.GradoAcademicoService;
//import app.service.ArchivoService;
import app.service.CertificadoCursoServiceImpl;
import app.service.ProfesionService;
import app.service.S3Service;
import app.util.Constantes;
import app.util.GeneradorReportes;
import app.util.URIS;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

@RestController
@RequestMapping("/RestCertificadosCursos") 
public class RestCertificadosCursos extends RestControllerGenericNormalImpl<CertificadoEntity,CertificadoCursoServiceImpl> {

	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
	@Autowired DepartamentoService departamentoService;
//	@Autowired private ArchivoService archivoService;
    @Autowired private S3Service s3Service;
    private final DataSource dataSource;

    @Autowired
    public RestCertificadosCursos(DataSource dataSource) {
        this.dataSource = dataSource;
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
	/*
	@GetMapping("/getCategoryById/{id}")
	public ResponseEntity<?> getCateogoryById(@PathVariable Integer id)throws IOException{
		System.out.println("*************************************************GET CATEGORIA***********************");
		List<?> lista=new ArrayList<>();
		try {
			System.out.println("LISTAR categorias:"+id);
			lista= servicio.getCategoryById(-1, -1,5);
			System.out.println("listar cat:"+lista.toString()); 

			
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lista);
		}
	}
	*/
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
    
    
    /*
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

        // Obtener la entidad del certificado
        CertificadoEntity certificadoEntity = servicio.findById(id);

        // Obtener las claves (nombres de archivos) de las imágenes en S3
        String keyPhotoPlantilla = Constantes.nameFolderLogoCurso + "/"+certificadoEntity.getCurso().getImagencertificado();
        String keyPhotoUser = Constantes.nameFolderLogoParticipante + "/"+certificadoEntity.getParticipante().getImagen();
        String keyPhotoQr = Constantes.nameFolderQrIncritoCertificado + "/"+certificadoEntity.getLinkqr();

        // Obtener las imágenes desde S3
        InputStream plantillaStream = s3Service.downloadFileFromS3(keyPhotoPlantilla);
        InputStream userStream = s3Service.downloadFileFromS3(keyPhotoUser);
        InputStream qrStream = s3Service.downloadFileFromS3(keyPhotoQr);

        // Manejo de InputStreams con try-with-resources
        try (
            plantillaStream;
            userStream;
            qrStream) {

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

            System.out.println("Reporte generado exitosamente con imágenes de S3.");
        } catch (Exception e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
            System.setProperty("net.sf.jasperreports.debug", "true"); // Activa el debug para JasperReports si es necesario
        }
    }*/
    
    
    @RequestMapping(value = "Imprimir_d1/{id}", method = RequestMethod.GET)
    public void imprimirCertificadoConImagenes(
            HttpServletResponse response,
            @PathVariable("id") Integer id) {
        try {
            // Parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            // Obtener el certificado
            CertificadoEntity certificadoEntity = servicio.findById(id);
            if (certificadoEntity == null) {
                throw new RuntimeException("Certificado no encontrado para el ID: " + id);
            }

            // Descargar imágenes
            InputStream plantillaStream = descargarArchivoS3(
                Constantes.nameFolderLogoCurso + "/" + certificadoEntity.getCurso().getImagencertificado(), 
                "la plantilla");
            InputStream userStream=null;
            if (certificadoEntity.getParticipante().getImagen()!=null) {
                 userStream = descargarArchivoS3(
                        Constantes.nameFolderLogoParticipante + "/" + certificadoEntity.getParticipante().getImagen(), 
                        "la foto del usuario");
                 
                 parametros.put("dirPhothoUser_param", userStream.readAllBytes());
			}else {
				parametros.put("dirPhothoUser_param", null);
			}

            InputStream qrStream = descargarArchivoS3(
                Constantes.nameFolderQrIncritoCertificado + "/" + certificadoEntity.getLinkqr(), 
                "el código QR");

        
            parametros.put("idcert_param", id);
            parametros.put("dirPhotho_param", plantillaStream.readAllBytes());
//            parametros.put("dirPhothoUser_param", userStream);
            parametros.put("dirPhothoQr_param", qrStream.readAllBytes());
            
            String formato = "pdf";
            String nombreReporte = "CERTIFICADO";
            String descarga = "inline"; // o inline attachment

            // Lista de fuentes personalizadas
            List<String> fuentes = List.of(
                "fonts/Montserrat/static/Montserrat-Bold.ttf",
                "fonts/Montserrat/static/Montserrat-Regular.ttf",
                "fonts/eras-itc-bold.ttf"
            );
            // Verificar carga de fuentes
            System.out.println("****************Cargando fuentes: " + fuentes);
            
            URIS ubicacion = new URIS();
    
            String urlReporte = ubicacion.jasperReport + "certificadobyid.jrxml";
            InputStream jrxmlStream = getClass().getClassLoader().getResourceAsStream(urlReporte);
            if (jrxmlStream == null) {
                throw new RuntimeException("No se pudo encontrar el archivo JRXML.");
            }
         // Ruta del archivo .jasper
//            String urlReporte = ubicacion.jasperReport + "certificadobyid.jasper";
//            InputStream jasperStream = getClass().getClassLoader().getResourceAsStream(urlReporte);
//            if (jasperStream == null) {
//                throw new RuntimeException("No se pudo encontrar el archivo .jasper.");
//            }

            // Compilar y generar el reporte
            JasperReport reporte = JasperCompileManager.compileReport(jrxmlStream);
            try (Connection connection = dataSource.getConnection()) {
                GeneradorReportes generador = new GeneradorReportes();
                generador.generarReporte(response, reporte,formato, parametros, connection, nombreReporte, descarga);
            }
        } catch (Exception e) {
            System.out.println("Error al generar el reporte: "+ e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private InputStream descargarArchivoS3(String key, String descripcion) throws RuntimeException, IOException {
        InputStream stream = s3Service.downloadFileFromS3(key);
        if (stream == null) {
            throw new RuntimeException("No se pudo descargar " + descripcion + " desde S3.");
        }
        return stream;
    }

    
//    @PostMapping("/Imprimir_certificados")
//    public ResponseEntity<Map<String, String>> recibirCertificadosMarcados(@RequestBody Map<String, List<Integer>> requestData, HttpServletResponse response) {
//        List<Integer> selectedIds = requestData.get("ids");  // Obtener los IDs de los certificados
//
//        Map<String, String> responseMap = new HashMap<>();
//        if (selectedIds == null || selectedIds.isEmpty()) {
//            // Respuesta de error si no se reciben IDs
//            responseMap.put("message", "No se enviaron IDs válidos.");
//            return ResponseEntity.badRequest().body(responseMap);
//        }
//
//        try {
//            // Convertir List<Integer> a Integer[]
//            Integer[] certificadosIdsArray = selectedIds.toArray(new Integer[0]);
//            System.out.println("Contenido de certificadosIdsArray:");
//            for (Integer id : certificadosIdsArray) {
//                System.out.println(id);
//            }
//            // Crear una lista de parámetros
//            Map<String, Object> parametros = new HashMap<>();
//            List<byte[]> fotoPlantillas = new ArrayList<>();
//            List<byte[]> fotoUsuarios = new ArrayList<>();
//            List<byte[]> fotosQr = new ArrayList<>();
//
//            // Recopilar las imágenes y agregar los parámetros
//            for (Integer id : certificadosIdsArray) {
//                CertificadoEntity certificadoEntity = servicio.findById(id);
//                if (certificadoEntity == null) {
//                    throw new RuntimeException("Certificado no encontrado para el ID: " + id);
//                }
//
//                // Descargar las imágenes de S3
//                InputStream plantillaStream = descargarArchivoS3(Constantes.nameFolderLogoCurso + "/" + certificadoEntity.getCurso().getImagencertificado(), "la plantilla");
//                
//                InputStream userStream=null;
//                if (certificadoEntity.getParticipante().getImagen()!=null) {
//                	 userStream = descargarArchivoS3(Constantes.nameFolderLogoParticipante + "/" + certificadoEntity.getParticipante().getImagen(), "la foto del usuario");
//                    
//                	 fotoUsuarios.add(userStream.readAllBytes());
//    			}else {
//    				fotoUsuarios.add(null);
//    			}
//                InputStream qrStream = descargarArchivoS3(Constantes.nameFolderQrIncritoCertificado + "/" + certificadoEntity.getLinkqr(), "el código QR");
//
//                // Guardar las imágenes en una lista de bytes
//                fotoPlantillas.add(plantillaStream.readAllBytes());
////                fotoUsuarios.add(userStream.);
//                fotosQr.add(qrStream.readAllBytes());
//            }   
//     
//            // Agregar los parámetros al mapa
//            parametros.put("certificadosIds", certificadosIdsArray);
//            parametros.put("fotoPlantillas", fotoPlantillas);
//            parametros.put("fotoUsuarios", fotoUsuarios);
//            parametros.put("fotosQr", fotosQr); 
//
//            String formato = "pdf";
//            String nombreReporte = "CERTIFICADO";
//            String descarga = "inline"; // o inline attachment
//
//            // Lista de fuentes personalizadas
//            List<String> fuentes = List.of(
//                "fonts/Montserrat/static/Montserrat-Bold.ttf",
//                "fonts/Montserrat/static/Montserrat-Regular.ttf",
//                "fonts/eras-itc-bold.ttf"
//            );
//            
//            // Verificar carga de fuentes
//            System.out.println("****************Cargando fuentes: " + fuentes);
//            // Cargar el archivo JRXML
//            URIS ubicacion = new URIS();
//            String urlReporte = ubicacion.jasperReport + "certificadosByIds.jrxml";
//            InputStream jrxmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(urlReporte);
//            if (jrxmlStream == null) {
//                throw new RuntimeException("No se pudo encontrar el archivo JRXML.");
//            }
//
//            // Compilar el reporte
//            JasperReport reporte = JasperCompileManager.compileReport(jrxmlStream);
//
//            try (Connection connection = dataSource.getConnection()) {
//                GeneradorReportes generador = new GeneradorReportes();
//                generador.generarReporte(response, reporte, formato, parametros, connection, nombreReporte, descarga);
//            }
//        } catch (Exception e) {
//            System.out.println("Error al generar los certificados: " + e.getMessage());
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            responseMap.put("message", "Error al generar los certificados.");
//            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(responseMap);
//        }
//
//        responseMap.put("message", "Certificados generados correctamente.");
//        return ResponseEntity.ok(responseMap);
//    }
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
            Integer[] certificadosIdsArray = selectedIds.toArray(new Integer[0]);
            System.out.println("Contenido de certificadosIdsArray:");
            for (Integer id : certificadosIdsArray) {
                System.out.println(id);
            }

            // Mapa de parámetros para el reporte
            Map<String, Object> parametros = new HashMap<>();
            List<byte[]> fotoPlantillas = new ArrayList<>();
            List<byte[]> fotoUsuarios = new ArrayList<>();
            List<byte[]> fotosQr = new ArrayList<>();

            // Recopilar las imágenes y agregar los parámetros
            for (Integer id : certificadosIdsArray) {
                CertificadoEntity certificadoEntity = servicio.findById(id);
                if (certificadoEntity == null) {
                    throw new RuntimeException("Certificado no encontrado para el ID: " + id);
                }

                // Descargar imágenes de S3
                InputStream plantillaStream = descargarArchivoS3(Constantes.nameFolderLogoCurso + "/" + certificadoEntity.getCurso().getImagencertificado(), "la plantilla");
                InputStream qrStream = descargarArchivoS3(Constantes.nameFolderQrIncritoCertificado + "/" + certificadoEntity.getLinkqr(), "el código QR");

                // Manejo de imagen del usuario (puede ser nula)
                InputStream userStream = null;
                if (certificadoEntity.getParticipante().getImagen() != null) {
                    userStream = descargarArchivoS3(Constantes.nameFolderLogoParticipante + "/" + certificadoEntity.getParticipante().getImagen(), "la foto del usuario");
                    fotoUsuarios.add(userStream.readAllBytes());
                } else {
                    fotoUsuarios.add(null);
                }

                // Guardar imágenes en listas de bytes
                fotoPlantillas.add(plantillaStream.readAllBytes());
                fotosQr.add(qrStream.readAllBytes());
            }

            // Agregar los parámetros al mapa
            parametros.put("certificadosIds", certificadosIdsArray);
            parametros.put("fotoPlantillas", fotoPlantillas);
            parametros.put("fotoUsuarios", fotoUsuarios);
            parametros.put("fotosQr", fotosQr);

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

            // Generar el PDF usando tu método actual
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


    /* 
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

*/
     
    /*
    @Autowired //METODO SIN USO
	DataSource dataSource;
	@RequestMapping(value="Imprimir_d1/{id}",method=RequestMethod.GET)
	public  void Imprimir_d(HttpServletResponse res,HttpServletRequest req,@PathVariable("id") Integer id) throws Exception{
		URIS ubicacion=new URIS();
	
//		Persona us=(Persona)req.getSession(true).getAttribute("xusuario");
//		String ListaTelefonos="",Tramitador=us.getAp().toUpperCase()+" "+us.getAm().toUpperCase()+" "+us.getNombres().toUpperCase();
//		String Tramitador="";
		int idparam=id;
		System.out.println("idparam: "+idparam);
		String nombreReporte="CERTIFICADO",tipo="pdf", estado="inline";
		
//		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
//		String nit_patam=(String) nitSQL.get("nitInst");
//		String nit_patam="1024171021";

	
		String dirPhothoUser_param=ubicacion.imgJasperReport+"user.png";
		String dirPhothoQr_param=ubicacion.imgJasperReport+"qrcert.png";
//		String direccionImagenActivo=ubicacion.imgJasperReportActivos+servicio.findById(id).getActivo().getImagen().toString();
		String direccionEscudoTarijaFondo=ubicacion.imgJasperReport+"escudo-tarija-agua.png";
//		String direccionFondoReporte=ubicacion.imgJasperReport+"fondoreporte.jpg";
		
		System.out.println("dirPhothoUser_param: "+dirPhothoUser_param);              
//		System.out.println("Dire2: "+direccionImagenActivo);              
		
//		System.out.println("escudo: "+this.getClass().getResourceAsStream(direccionLogo));
//		String subReportInst=ubicacion.jasperReport+"getEmpresa.jasper";
		
		Map<String, Object> parametros=new HashMap<String, Object>();               	
		String url=ubicacion.jasperReport+"certificadobyid.jasper";  	
	                                
//		parametros.put("nit_param",nit_patam);
		parametros.put("idcert_param",idparam); 
//		parametros.put("tramitador_param",Tramitador);

		parametros.put("dirPhothoUser_param",this.getClass().getResourceAsStream(dirPhothoUser_param));
		parametros.put("dirPhothoQr_param",this.getClass().getResourceAsStream(dirPhothoQr_param));
//		parametros.put("imagen_activo_param",this.getClass().getResourceAsStream(direccionImagenActivo));

		GeneradorReportes g=new GeneradorReportes(); 
		try{
			 
			g.generarReporte(res, getClass().getResource(url), tipo, parametros, dataSource.getConnection(), nombreReporte, estado);	
		  

		    
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}		
	}
	
	*/
	/*
    @PostMapping("/guardar")
	public ResponseEntity<?> save( ParticipanteDTO ParticipanteDTO,@RequestParam("logo") MultipartFile file){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadSave LLEGO:"+ParticipanteDTO.toString());

		try {
			System.out.println("**AFICHE**:"+ParticipanteDTO.getLogo().getOriginalFilename());
			
			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(ParticipanteDTO.getCi());
			personaEntity.setExp(ParticipanteDTO.getExp());
			personaEntity.setNombrecompleto(ParticipanteDTO.getNombrecompleto());
			personaEntity.setGenero(ParticipanteDTO.getGenero());
			personaEntity.setFechanacimiento(ParticipanteDTO.getFechanacimiento());
			personaEntity.setEdad(ParticipanteDTO.getEdad());
			personaEntity.setCelular(ParticipanteDTO.getCelular());
			personaEntity.setEmail(ParticipanteDTO.getEmail());
			personaEntity.setDireccion(ParticipanteDTO.getDireccion());
			personaEntity.setEstado(1);
			
			
			CertificadoEntity CertificadoEntity=new CertificadoEntity();
			CertificadoEntity.setImagen(ParticipanteDTO.getImagen());
			CertificadoEntity.setLogo(ParticipanteDTO.getLogo());
			CertificadoEntity.setGradoacademico(gradoAcademicoService.findById(ParticipanteDTO.getGradoacademico()));
			CertificadoEntity.setProfesion(profesionService.findById(ParticipanteDTO.getProfesion()));
			CertificadoEntity.setDepartamento(departamentoService.findById(ParticipanteDTO.getDepartamento()));
			CertificadoEntity.setLocalidad(ParticipanteDTO.getLocalidad());
			CertificadoEntity.setPersona(personaEntity);
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(CertificadoEntity));
//			return ResponseEntity.status(HttpStatus.OK).body(new CertificadoEntity());
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    
    @PostMapping("/modificar/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, ParticipanteDTO ParticipanteDTO, @RequestParam("logo") MultipartFile file) {
        try {

			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(ParticipanteDTO.getCi());
			personaEntity.setExp(ParticipanteDTO.getExp());
			personaEntity.setNombrecompleto(ParticipanteDTO.getNombrecompleto());
			personaEntity.setGenero(ParticipanteDTO.getGenero());
			personaEntity.setFechanacimiento(ParticipanteDTO.getFechanacimiento());
			personaEntity.setEdad(ParticipanteDTO.getEdad());
			personaEntity.setCelular(ParticipanteDTO.getCelular());
			personaEntity.setEmail(ParticipanteDTO.getEmail());
			personaEntity.setDireccion(ParticipanteDTO.getDireccion());
			personaEntity.setEstado(1);
			
			
			CertificadoEntity CertificadoEntity=new CertificadoEntity();
			CertificadoEntity.setCodigo(ParticipanteDTO.getCodigo());
			CertificadoEntity.setImagen(ParticipanteDTO.getImagen());
			CertificadoEntity.setLogo(ParticipanteDTO.getLogo());
			CertificadoEntity.setGradoacademico(gradoAcademicoService.findById(ParticipanteDTO.getGradoacademico()));
			CertificadoEntity.setProfesion(profesionService.findById(ParticipanteDTO.getProfesion()));
			CertificadoEntity.setDepartamento(departamentoService.findById(ParticipanteDTO.getDepartamento()));
			CertificadoEntity.setLocalidad(ParticipanteDTO.getLocalidad());
			CertificadoEntity.setPersona(personaEntity);
            
            CertificadoEntity CertificadoEntity2=servicio.update(id, CertificadoEntity);
            System.out.println("REST CONTROLLER CURSO:"+CertificadoEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(CertificadoEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }

    */
    
    
    
/*
    
    @GetMapping("getTotCursoPorCategoria/{id}")
    public ResponseEntity<?> getTotCursoPorCategoria(@PathVariable Integer id) {
        try {
        	int cant=servicio.getTotCursoPorCategoria(id); 	
            return ResponseEntity.status(HttpStatus.OK).body(cant);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @PutMapping("/modificar_plantilla/{id}")
    public ResponseEntity<?> modificar_plantilla(@PathVariable Integer id, ParticipanteDTO ParticipanteDTO, @RequestParam("certificado_imagen") MultipartFile file) {
        try {
            // Procesar el archivo MultipartFile
//            if (!file.isEmpty()) {
//                ParticipanteDTO.setAfiche(file);
//            }

            // Convertir DTO a Entity
            CertificadoEntity CertificadoEntity = new CertificadoEntity();
            CertificadoEntity.setId(ParticipanteDTO.getId());
            CertificadoEntity.setCodigo(ParticipanteDTO.getCodigo());

            CertificadoEntity.setCertificado_imagen(ParticipanteDTO.getCertificado_imagen());
            
            CertificadoEntity CertificadoEntity2=servicio.customPlantillaCurso(id, CertificadoEntity);
            System.out.println("REST CONTROLLER CURSO:"+CertificadoEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(CertificadoEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }
*/

}
