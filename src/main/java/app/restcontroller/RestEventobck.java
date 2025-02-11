package app.restcontroller;
/*
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.Drive;

import app.entity.PersonaEntity;
import app.config.GoogleDriveConfig;
import app.dto.CursoDTO;
import app.dto.EventoDTO;
import app.entity.EventoEntity;
import app.service.ArchivoService;
import app.service.EventoServiceImpl;
import app.service.UsuarioService;
import app.service.UsuarioServiceImpl;
import app.util.Constantes;

@RestController
@RequestMapping("/RestEventosbck") 
public class RestEventobck extends RestControllerGenericNormalImpl<EventoEntity,EventoServiceImpl> {
	@Autowired private ArchivoService archivoService;
	
	@Autowired
    private GoogleDriveConfig googleDriveConfig;
//	@Autowired private 
	@Autowired private UsuarioService userService;
	
	
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
	public ResponseEntity<?> save( EventoDTO eventoDTO ,@RequestParam("afiche") MultipartFile file){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadDTOSave LLEGO:"+eventoDTO.toString());
//		System.out.println("Cursos:"+eventoDTO.getCurso().size());
//		System.out.println("EXPOSITORS:"+eventoDTO.getExpositor().size());
		try {
			System.out.println("**AFICHE**:"+eventoDTO.getAfiche().getOriginalFilename());
			 
			EventoEntity eventoEntity=new EventoEntity();
//			eventoEntity.setId(null)
//			eventoEntity.setCodigo(null)
//			eventoEntity.setCodigoevento(null)
			eventoEntity.setConcertificado(eventoDTO.getConcertificado());
			eventoEntity.setConevaluacion(eventoDTO.getConevaluacion());
			eventoEntity.setDetalle(eventoDTO.getDetalle());
			eventoEntity.setEstado(1);
			eventoEntity.setFechafinal(eventoDTO.getFechafinal());
			eventoEntity.setFechainicial(eventoDTO.getFechainicial());
			eventoEntity.setFecharegistrofin(eventoDTO.getFecharegistrofin());
//			eventoEntity.setFecharegistroinicio(null)
			eventoEntity.setHora(eventoDTO.getHora());
//			eventoEntity.setImagen(null)
//			eventoEntity.setImagen_qr_what(null)
//			eventoEntity.setImagencredencial(null)
//			eventoEntity.setImagencredencialDriveId(null)
//			eventoEntity.setImageneventoDriveId(null)
			eventoEntity.setLinkgrupo(eventoDTO.getLinkgrupo());
//			eventoEntity.setLinkqr(null)
			eventoEntity.setLugarevento(eventoDTO.getLugarevento());
			eventoEntity.setMetodopagosino(eventoDTO.getMetodopagosino());//add2
			eventoEntity.setMetodopagos(eventoDTO.getMetodopagos());//add2
			eventoEntity.setModalidad(eventoDTO.getModalidad());
			eventoEntity.setNroestudiantescupo(eventoDTO.getNroestudiantescupo());
			eventoEntity.setNroestudiantesinscritos(eventoDTO.getNroestudiantesinscritos());
//			eventoEntity.setNroevento(null)
			eventoEntity.setPrecio(eventoDTO.getPrecio());
			eventoEntity.setTipo(eventoDTO.getTipo());		
			eventoEntity.setAnio(eventoDTO.getAnio());	
			
			eventoEntity.setAfiche(eventoDTO.getAfiche());
						
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(eventoEntity,eventoDTO.getCurso(),eventoDTO.getExpositor(),eventoDTO.getFechacurso(),eventoDTO.getLiteralfechacurso(),eventoDTO.getHorainicio(),eventoDTO.getHorafin(),eventoDTO.getLugarcurso()));
//			return ResponseEntity.status(HttpStatus.OK).body(new EventoEntity());
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
	
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody EventoEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	EventoEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    
    
    @GetMapping("/findByCodeEncrypt"+"/{codigoeventoencript}")
    public ResponseEntity<?> buscar(@PathVariable String codigoeventoencript){ 
        try { 
        	System.out.println("ID A BUSCAR");
        	EventoEntity entity=servicio.findByCodeEncrypt(codigoeventoencript);
        	if (entity!=null) {
        		System.out.println("Socio encontrado:"+entity.toString());	
			}
        	
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    
    
    
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
	
	@GetMapping("/afiche/{filename}")
	public ResponseEntity<Resource> img_afiche(@PathVariable String filename) {
	    try {
	    	System.out.println("filenameAFICHE:"+filename);
	        // Obtener el ID de la carpeta en Google Drive
	        String folderId = archivoService.getOrCreateFolder(Constantes.nameFolderLogoEvento);

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
    

}
*/
