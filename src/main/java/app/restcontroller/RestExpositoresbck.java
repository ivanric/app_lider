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
import app.dto.ExpositorDTO;
import app.entity.ExpositorEntity;
//import app.service.ArchivoService;
import app.service.ExpositorServiceImpl;
import app.service.GradoAcademicoService;
import app.service.ProfesionService;
import app.util.Constantes;
/*
@RestController
@RequestMapping("/RestExpositoresbck") 
public class RestExpositoresbck extends RestControllerGenericNormalImpl<ExpositorEntity,ExpositorServiceImpl> {

	
	@Autowired private ArchivoService archivoService;
	
	@Autowired
    private GoogleDriveConfig googleDriveConfig;
	
	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
	
	
	//drive
    private Drive getDriveService() throws IOException, GeneralSecurityException {
        return googleDriveConfig.getDriveService();
    }
	
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
	public ResponseEntity<?> save( ExpositorDTO expositorDTO,@RequestParam("logo") MultipartFile file){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadSave LLEGO:"+expositorDTO.toString());

		try {
			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(expositorDTO.getCi());
			personaEntity.setExp(expositorDTO.getExp());
			personaEntity.setNombres(expositorDTO.getNombres());
			personaEntity.setApellidos(expositorDTO.getApellidos());
			personaEntity.setEmail(expositorDTO.getEmail());
			personaEntity.setCelular(expositorDTO.getCelular());
			personaEntity.setDireccion(expositorDTO.getDireccion());
			personaEntity.setEstado(1);
			
			ExpositorEntity expositorEntity=new ExpositorEntity();
			expositorEntity.setEstado(1);
			expositorEntity.setRubro(expositorDTO.getRubro());
			expositorEntity.setDedicacion(expositorDTO.getDedicacion());
			expositorEntity.setPersona(personaEntity);
			expositorEntity.setGradoacademico(gradoAcademicoService.findById(expositorDTO.getGradoacademico()));
			expositorEntity.setProfesion(profesionService.findById(expositorDTO.getProfesion()));
			expositorEntity.setLogo(expositorDTO.getLogo());
			
			
			System.out.println("***************************LOGOO:"+expositorEntity.getLogo().getOriginalFilename());
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(expositorEntity));
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}

    @PostMapping("/modificar/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id,ExpositorDTO expositorDTO ,@RequestParam("logo") MultipartFile file){
		
		
		System.out.println("EntidadModificar LLEGO:"+expositorDTO.toString());
    	try {
			
			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(expositorDTO.getCi());
			personaEntity.setExp(expositorDTO.getExp());
			personaEntity.setNombres(expositorDTO.getNombres());
			personaEntity.setApellidos(expositorDTO.getApellidos());
			personaEntity.setEmail(expositorDTO.getEmail());
			personaEntity.setCelular(expositorDTO.getCelular());
			personaEntity.setDireccion(expositorDTO.getDireccion());
			personaEntity.setEstado(1);
			
			ExpositorEntity expositorEntity=new ExpositorEntity();
			expositorEntity.setId(expositorDTO.getId());
			expositorEntity.setCodigo(expositorDTO.getCodigo());
			expositorEntity.setEstado(1);
			expositorEntity.setRubro(expositorDTO.getRubro());
			expositorEntity.setDedicacion(expositorDTO.getDedicacion());
			expositorEntity.setPersona(personaEntity);
			expositorEntity.setGradoacademico(gradoAcademicoService.findById(expositorDTO.getGradoacademico()));
			expositorEntity.setProfesion(profesionService.findById(expositorDTO.getProfesion()));
			expositorEntity.setLogo(expositorDTO.getLogo());
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id,expositorEntity));
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody ExpositorEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	ExpositorEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

	@GetMapping("/logo/{filename}")
    public ResponseEntity<Resource> getFile_logo_expo_drive(@PathVariable String filename) {
        try {
            // Obtener el ID de la carpeta de logos en Google Drive
            String folderId = archivoService.getOrCreateFolder(Constantes.nameFolderLogoExpositor);
            
            // Obtener el ID del archivo en Google Drive utilizando el método existente
            String fileId = archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
            
            if (fileId != null) {
                // Obtener el servicio de Google Drive
                Drive driveService = getDriveService();

                // Descargar el archivo desde Google Drive como InputStream
                InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
                InputStreamResource resource = new InputStreamResource(inputStream);

                // Determinar el tipo de contenido del archivo
                String contentType = "application/octet-stream"; // Tipo por defecto
                try {
                    contentType = Files.probeContentType(Paths.get(filename));
                } catch (IOException e) {
                    System.out.println("No se pudo determinar el tipo de archivo.");
                }

                // Devolver el archivo como respuesta
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@GetMapping("/logo_expositor/{filename}")
	public ResponseEntity<Resource> logo_expositor(@PathVariable String filename) {
	    try {
	    	System.out.println("filenameAFICHE:"+filename);
	        // Obtener el ID de la carpeta en Google Drive
	        String folderId = archivoService.getOrCreateFolder(Constantes.nameFolderLogoExpositor);

	        // Obtener el ID del archivo en Google Drive utilizando el método existente
	        String fileId = archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);

	        if (fileId != null) {
	            // Obtener el servicio de Google Drive
	            Drive driveService = getDriveService();

	            // Descargar el archivo desde Google Drive como InputStream
	            InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
	            Resource resource = new InputStreamResource(inputStream);

	            // Determinar el tipo de contenido del archivo
	            String contentType = "application/octet-stream"; // Tipo por defecto
	            try {
	                contentType = Files.probeContentType(Paths.get(filename));
	            } catch (IOException e) {
	                System.out.println("No se pudo determinar el tipo de archivo.");
	            }

	            // Devolver el archivo como respuesta
	            return ResponseEntity.ok()
	                    .contentType(MediaType.parseMediaType(contentType))
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
	                    .body(resource);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

}*/
