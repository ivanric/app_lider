package app.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.Drive;

import app.entity.PersonaEntity;
//import app.config.GoogleDriveConfig;
import app.dto.MetodoPagoDTO;
import app.entity.MetodoPagoEntity;
//import app.service.ArchivoService;
import app.service.MetodoPagoServiceImpl;
import app.service.GradoAcademicoService;
import app.service.ProfesionService;
import app.service.S3Service;
import app.util.Constantes;

@RestController
@RequestMapping("/RestMetodosPagos") 
public class RestMetodosPagos extends RestControllerGenericNormalImpl<MetodoPagoEntity,MetodoPagoServiceImpl> {

	
//	@Autowired private ArchivoService archivoService;
	
//	@Autowired
//    private GoogleDriveConfig googleDriveConfig;
	
	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
    @Autowired private S3Service s3Service;
	
	//drive
//    private Drive getDriveService() throws IOException, GeneralSecurityException {
//        return googleDriveConfig.getDriveService();
//    }
	
	@GetMapping("/listar")
	public ResponseEntity<?> getAll(HttpServletRequest request,@Param("draw")int draw,@Param("length")int length,@Param("start")int start,@Param("estado")int estado)throws IOException{
		String total="";
		Map<String, Object> Data = new HashMap<String, Object>();
		try {

			String search = request.getParameter("search[value]");
			int tot=Constantes.NUM_MAX_DATATABLE;
			System.out.println("tot:"+tot+"estado:"+estado+"search:"+search+"length:"+length+"start:"+start);
			List<?> lista= servicio.findAll(estado, search, length, start);
			System.out.println("listar:"+lista.toString()); 
			try {

					total=String.valueOf(servicio.getTotAll(search,estado));	
						
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
    @PostMapping("/guardar")
	public ResponseEntity<?> save( MetodoPagoDTO MetodoPagoDTO,@RequestParam("logo") MultipartFile file){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadSave LLEGO:"+MetodoPagoDTO.toString());

		try {		
			MetodoPagoEntity MetodoPagoEntity=new MetodoPagoEntity();
			MetodoPagoEntity.setEstado(1);
			MetodoPagoEntity.setTitular(MetodoPagoDTO.getTitular());
			MetodoPagoEntity.setBanco(MetodoPagoDTO.getBanco());
			MetodoPagoEntity.setNrocuenta(MetodoPagoDTO.getNrocuenta());
			MetodoPagoEntity.setLogo(MetodoPagoDTO.getLogo());
	
			System.out.println("***************************LOGOO:"+MetodoPagoEntity.getLogo().getOriginalFilename());
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(MetodoPagoEntity));
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}

    @PostMapping("/modificar/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id,MetodoPagoDTO MetodoPagoDTO ,@RequestParam("logo") MultipartFile file){
		
		
		System.out.println("EntidadModificar LLEGO:"+MetodoPagoDTO.toString());
    	try {
			
			
			MetodoPagoEntity MetodoPagoEntity=new MetodoPagoEntity();
			MetodoPagoEntity.setId(MetodoPagoDTO.getId());
			MetodoPagoEntity.setCodigo(MetodoPagoDTO.getCodigo());
			MetodoPagoEntity.setEstado(1);
			MetodoPagoEntity.setTitular(MetodoPagoDTO.getTitular());
			MetodoPagoEntity.setBanco(MetodoPagoDTO.getBanco());
			MetodoPagoEntity.setNrocuenta(MetodoPagoDTO.getNrocuenta());
			MetodoPagoEntity.setLogo(MetodoPagoDTO.getLogo());
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id,MetodoPagoEntity));
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody MetodoPagoEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	MetodoPagoEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/logo/{filename}")
    public ResponseEntity<Resource> getFileLogoExpo(@PathVariable String filename) {
        try {
            System.out.println("filename: " + filename);

            // Construir el key completo del archivo en S3
            String fileKey = Constantes.nameFolderLogoMetodoPago + "/" + filename;

            // Verificar si el archivo existe en S3
            if (!s3Service.fileExists(fileKey)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Descargar el archivo desde S3
            InputStream inputStream = s3Service.downloadFileFromS3(fileKey);
            if (inputStream == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Crear un recurso a partir del InputStream
            InputStreamResource resource = new InputStreamResource(inputStream);

            // Determinar el tipo de contenido del archivo
            String contentType = "application/octet-stream"; // Valor por defecto
            try {
                contentType = Files.probeContentType(Paths.get(filename));
                if (contentType == null || !contentType.startsWith("image/")) {
                    contentType = "application/octet-stream"; // Fallback si no es imagen
                }
            } catch (IOException e) {
                System.out.println("No se pudo determinar el tipo de archivo.");
            }

            // Devolver el archivo como respuesta
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	
    @GetMapping("/logo_expositor/{filename}")
    public ResponseEntity<Resource> logoExpositor(@PathVariable String filename) {
        try {
            System.out.println("filenameAFICHE: " + filename);

            // Construir el key completo del archivo en S3
            String fileKey = Constantes.nameFolderLogoMetodoPago + "/" + filename;

            // Verificar si el archivo existe en S3
            if (!s3Service.fileExists(fileKey)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Descargar el archivo desde S3
            InputStream inputStream = s3Service.downloadFileFromS3(fileKey);
            if (inputStream == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Crear un recurso a partir del InputStream
            InputStreamResource resource = new InputStreamResource(inputStream);

            // Determinar el tipo de contenido del archivo
            String contentType = "application/octet-stream"; // Tipo por defecto
            try {
                contentType = Files.probeContentType(Paths.get(filename));
                if (contentType == null || !contentType.startsWith("image/")) {
                    contentType = "application/octet-stream"; // Fallback si no es una imagen
                }
            } catch (IOException e) {
                System.out.println("No se pudo determinar el tipo de archivo.");
            }

            // Devolver el archivo como respuesta
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	
    @GetMapping("/logo_mp/{filename}")
    public ResponseEntity<Resource> logoMp(@PathVariable String filename) {
        try { 
            System.out.println("filenameAFICHE: " + filename);

            // Construir el key completo del archivo en S3
            String fileKey = Constantes.nameFolderLogoMetodoPago + "/" + filename;

            // Verificar si el archivo existe en S3
            if (!s3Service.fileExists(fileKey)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Descargar el archivo desde S3
            InputStream inputStream = s3Service.downloadFileFromS3(fileKey);
            if (inputStream == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Crear un recurso a partir del InputStream
            InputStreamResource resource = new InputStreamResource(inputStream);

            // Determinar el tipo de contenido del archivo
            String contentType = "application/octet-stream"; // Tipo por defecto
            try {
                contentType = Files.probeContentType(Paths.get(filename));
                if (contentType == null || !contentType.startsWith("image/")) {
                    contentType = "application/octet-stream"; // Fallback si no es una imagen
                }
            } catch (IOException e) {
                System.out.println("No se pudo determinar el tipo de archivo.");
            }

            // Devolver el archivo como respuesta
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
