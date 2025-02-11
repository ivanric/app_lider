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
import app.dto.CursoDTO;
import app.entity.CursoEntity;
//import app.service.ArchivoService;
import app.service.CursoServiceImpl;
import app.service.S3Service;
import app.service.UsuarioServiceImpl;
import app.util.Constantes;

@RestController
@RequestMapping("/RestCursos") 
public class RestCurso extends RestControllerGenericNormalImpl<CursoEntity,CursoServiceImpl> {

    @Autowired
    private S3Service s3Service;
//	@Autowired private ArchivoService archivoService;
	
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
	
	@GetMapping("/listar_cursos")
	public ResponseEntity<?> getAllCursos(HttpServletRequest request, 
	                                      @Param("draw") int draw, 
	                                      @Param("length") int length, 
	                                      @Param("start") int start, 
	                                      @Param("estado") int estado) throws IOException {
	    
	    Map<String, Object> Data = new HashMap<>();
	    try {
	        String search = request.getParameter("search[value]");
	        int tot = Constantes.NUM_MAX_DATATABLE;
	        Integer idcat = -1, idanio = -1;
	        
	        // Validación de parámetros
	        if (request.getParameter("idcat") != null && !request.getParameter("idcat").isEmpty()) {
	            idcat = Integer.parseInt(request.getParameter("idcat"));
	            System.out.println("idcat:" + idcat);
	        }
	        
	        if (request.getParameter("idanio") != null && !request.getParameter("idanio").isEmpty()) {
	            idanio = Integer.parseInt(request.getParameter("idanio"));
	            System.out.println("idanio:" + idanio);
	        }
	        
	        System.out.println("tot:" + tot + " estado:" + estado + " search:" + search + " length:" + length + " start:" + start + " idcat:" + idcat+ " idanio:" + idanio);
	        
	        // Obtener lista de cursos
	        List<?> lista = servicio.findAll(estado, search, length, start,idcat,idanio);
	        System.out.println("Listar cursos: " + lista);
	        
	        String total;
	        try {
	            total = String.valueOf(servicio.getTotAll(search, estado,idcat,idanio));
	        } catch (Exception e) {
	            total = "0";
	        }
	        
	        // Armar respuesta
	        Data.put("draw", draw);
	        Data.put("recordsTotal", total);
	        Data.put("data", lista);
	        Data.put("recordsFiltered", search != null && !search.isEmpty() ? lista.size() : total);
	        
	        return ResponseEntity.status(HttpStatus.OK).body(Data);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
	    }
	}

	
    @PostMapping("/guardar")
	public ResponseEntity<?> save( CursoDTO cursoDTO){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadSave LLEGO:"+cursoDTO.toString());

		try {
			System.out.println("**PLANTILLA**:"+cursoDTO.getCertificado_imagen().getOriginalFilename());
			
			CursoEntity cursoEntity=new CursoEntity();
			cursoEntity.setNrodocumento(cursoDTO.getNrodocumento());
			cursoEntity.setCertificado_imagen(cursoDTO.getCertificado_imagen());
			cursoEntity.setCertificado_imagen_sf(cursoDTO.getCertificado_imagen_sf());
			cursoEntity.setAnio(cursoDTO.getAnio());
			cursoEntity.setCategoria(cursoDTO.getCategoria());
			cursoEntity.setNombrecurso(cursoDTO.getNombrecurso());
			cursoEntity.setNivel(cursoDTO.getNivel());
			cursoEntity.setDedicatoriacertificado(cursoDTO.getDedicatoriacertificado());
			cursoEntity.setGlosacertificado(cursoDTO.getGlosacertificado());
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(cursoEntity));
//			return ResponseEntity.status(HttpStatus.OK).body(new CursoEntity());
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    @PostMapping("/modificar/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, CursoDTO cursoDTO) {
        try {
            // Procesar el archivo MultipartFile
//            if (!file.isEmpty()) {
//                cursoDTO.setAfiche(file);
//            }
        	System.out.println("CURSODTO:"+cursoDTO.toString());
            // Convertir DTO a Entity
            CursoEntity cursoEntity = new CursoEntity();
            cursoEntity.setId(cursoDTO.getId());
            cursoEntity.setCodigo(cursoDTO.getCodigo());
            cursoEntity.setNrodocumento(cursoDTO.getNrodocumento());
			cursoEntity.setAnio(cursoDTO.getAnio());
            cursoEntity.setCategoria(cursoDTO.getCategoria());
            cursoEntity.setNombrecurso(cursoDTO.getNombrecurso());
            cursoEntity.setNivel(cursoDTO.getNivel());
            cursoEntity.setCertificado_imagen(cursoDTO.getCertificado_imagen());
            cursoEntity.setCertificado_imagen_sf(cursoDTO.getCertificado_imagen_sf());
			cursoEntity.setDedicatoriacertificado(cursoDTO.getDedicatoriacertificado());
			cursoEntity.setGlosacertificado(cursoDTO.getGlosacertificado());
            // Actualizar la entidad y manejar excepciones
            
            CursoEntity cursoEntity2=servicio.update(id, cursoEntity);
            System.out.println("REST CONTROLLER CURSO:"+cursoEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(cursoEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }
    
    @PutMapping("/modificar_plantilla/{id}")
    public ResponseEntity<?> modificar_plantilla(@PathVariable Integer id, CursoDTO cursoDTO, @RequestParam("certificado_imagen") MultipartFile file) {
        try {
            // Procesar el archivo MultipartFile
//            if (!file.isEmpty()) {
//                cursoDTO.setAfiche(file);
//            }

            // Convertir DTO a Entity
            CursoEntity cursoEntity = new CursoEntity();
            cursoEntity.setId(cursoDTO.getId());
            cursoEntity.setCodigo(cursoDTO.getCodigo());
            /*
            cursoEntity.setNrodocumento(cursoDTO.getNrodocumento());
            cursoEntity.setCategoria(cursoDTO.getCategoria());
            cursoEntity.setNombrecurso(cursoDTO.getNombrecurso());
            cursoEntity.setModalidad(cursoDTO.getModalidad());
            cursoEntity.setNivel(cursoDTO.getNivel());
            cursoEntity.setFechainicial(cursoDTO.getFechainicial());
            cursoEntity.setFechafinal(cursoDTO.getFechafinal());
            cursoEntity.setHora(cursoDTO.getHora());
            cursoEntity.setLinkgrupo(cursoDTO.getLinkgrupo());
            cursoEntity.setExpositores(cursoDTO.getExpositores());
            cursoEntity.setAfiche(cursoDTO.getAfiche());
            */
            cursoEntity.setCertificado_imagen(cursoDTO.getCertificado_imagen());
            
            CursoEntity cursoEntity2=servicio.customPlantillaCurso(id, cursoEntity);
            System.out.println("REST CONTROLLER CURSO:"+cursoEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(cursoEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody CursoEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	CursoEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
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
    @GetMapping("/logo/{filename}")
    public ResponseEntity<Resource> img_logo(@PathVariable String filename) {
        try {
        	System.out.println("filename1: " + filename);
            // Concatenar la ruta completa en S3 donde está almacenada la imagen
            String fileKey = Constantes.nameFolderLogoCurso + "/" + filename;
            System.out.println("fileKey:"+fileKey);
            // Obtener el archivo desde S3 como InputStream
            InputStream inputStream = s3Service.downloadFileFromS3(fileKey);

            if (inputStream != null) {
                // Determinar el tipo de contenido del archivo (opcional)
                String contentType = "application/octet-stream"; // Tipo por defecto
                try {
                    contentType = Files.probeContentType(Paths.get(filename));
                    if (contentType == null || !contentType.startsWith("image/")) {
                        contentType = "application/octet-stream"; // Forzar un tipo por defecto si no es una imagen
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo determinar el tipo de archivo.");
                }

                // Crear un recurso a partir del InputStream
                Resource resource = new InputStreamResource(inputStream);

                // Devolver el archivo como respuesta con los encabezados adecuados
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
    @GetMapping("/logo2/{filename}")
    public ResponseEntity<Resource> img_logo2(@PathVariable String filename) {
        try {
        	System.out.println("filename2: " + filename);
            // Concatenar la ruta completa en S3 donde está almacenada la imagen
            String fileKey = Constantes.nameFolderLogoCursoSF + "/" + filename;
            System.out.println("fileKey2:"+fileKey);
            // Obtener el archivo desde S3 como InputStream
            InputStream inputStream = s3Service.downloadFileFromS3(fileKey);

            if (inputStream != null) {
                // Determinar el tipo de contenido del archivo (opcional)
                String contentType = "application/octet-stream"; // Tipo por defecto
                try {
                    contentType = Files.probeContentType(Paths.get(filename));
                    if (contentType == null || !contentType.startsWith("image/")) {
                        contentType = "application/octet-stream"; // Forzar un tipo por defecto si no es una imagen
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo determinar el tipo de archivo.");
                }

                // Crear un recurso a partir del InputStream
                Resource resource = new InputStreamResource(inputStream);

                // Devolver el archivo como respuesta con los encabezados adecuados
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

    @GetMapping("/logourl/{filename}")
    public ResponseEntity<Resource> getFile_logo_s3(@PathVariable String filename) {
        try {
            System.out.println("logO: " + filename);
            
            // Concatenar la ruta completa en S3 donde está almacenada la imagen
            String fileKey = Constantes.nameFolderLogoCurso + "/" + filename;

            // Obtener el archivo desde S3 como InputStream
            InputStream inputStream = s3Service.downloadFileFromS3(fileKey);

            if (inputStream != null) {
                // Establecer un tipo de contenido por defecto
                String contentType = "application/octet-stream"; // Tipo por defecto
                try {
                    contentType = Files.probeContentType(Paths.get(filename));
                    if (contentType == null || !contentType.startsWith("image/")) {
                        contentType = "application/octet-stream"; // Forzar un tipo por defecto si no es una imagen
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo determinar el tipo de archivo.");
                }

                // Crear un recurso a partir del InputStream
                InputStreamResource resource = new InputStreamResource(inputStream);

                // Devolver el archivo como respuesta con los encabezados adecuados
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



    
    /*
    @PutMapping("/catalogos/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id,CursoEntity entidad){
		
		
		System.out.println("EntidadModificar LLEGO:"+entidad.toString());
    	try {
			
	
			return ResponseEntity.status(HttpStatus.OK).body(servicio.updatecatalogos(id,entidad));
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    
    @GetMapping("/findByNrodocumento"+"/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id){ 
        try { 
        	System.out.println("ID A BUSCAR");
        	CursoEntity entity=new CursoEntity();
        	entity=servicio.findByNrodocumento(id);
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
    
	@PostMapping("modificarqr/{id}")
	public ResponseEntity<?> modificarqr(@PathVariable Integer id,@RequestBody  CursoEntity entidad){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.renovarQR(id,entidad));
		} catch (Exception e) {//BAD_REQUEST= es error 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
*/

}
