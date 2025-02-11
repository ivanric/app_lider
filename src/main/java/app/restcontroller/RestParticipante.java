package app.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import app.dto.ParticipanteDTO;
import app.entity.ParticipanteEntity;
import app.entity.PersonaEntity;
import app.service.DepartamentoService;
import app.service.GradoAcademicoService;
import app.service.ParticipanteServiceImpl;
import app.service.ProfesionService;
import app.service.S3Service;
import app.util.Constantes;

@RestController
@RequestMapping("/RestParticipantes") 
public class RestParticipante extends RestControllerGenericNormalImpl<ParticipanteEntity,ParticipanteServiceImpl> {

	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
	@Autowired DepartamentoService departamentoService;
    @Autowired
    private S3Service s3Service;
	
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
	
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody ParticipanteEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	ParticipanteEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    
    
	
    @PostMapping("/guardar")
	public ResponseEntity<?> save( ParticipanteDTO ParticipanteDTO,@RequestParam("logo") MultipartFile file){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadSave LLEGO:"+ParticipanteDTO.toString());

		try {
			System.out.println("**AFICHE**:"+ParticipanteDTO.getLogo().getOriginalFilename());
			
			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(ParticipanteDTO.getCi());
			personaEntity.setExp(ParticipanteDTO.getExp());
			personaEntity.setNombres(ParticipanteDTO.getNombres());
			personaEntity.setApellidos(ParticipanteDTO.getApellidos());
			personaEntity.setGenero(ParticipanteDTO.getGenero());
			personaEntity.setFechanacimiento(ParticipanteDTO.getFechanacimiento());
			personaEntity.setEdad(ParticipanteDTO.getEdad());
			personaEntity.setCelular(ParticipanteDTO.getCelular());
			personaEntity.setEmail(ParticipanteDTO.getEmail());
			personaEntity.setDireccion(ParticipanteDTO.getDireccion());
			personaEntity.setEstado(1);
			
			
			ParticipanteEntity ParticipanteEntity=new ParticipanteEntity();
			ParticipanteEntity.setImagen(ParticipanteDTO.getImagen());
			ParticipanteEntity.setLogo(ParticipanteDTO.getLogo());
			ParticipanteEntity.setGradoacademico(gradoAcademicoService.findById(ParticipanteDTO.getGradoacademico()));
			ParticipanteEntity.setProfesion(profesionService.findById(ParticipanteDTO.getProfesion()));
			ParticipanteEntity.setDepartamento(departamentoService.findById(ParticipanteDTO.getDepartamento()));
			ParticipanteEntity.setLocalidad(ParticipanteDTO.getLocalidad());
			ParticipanteEntity.setPersona(personaEntity);
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(ParticipanteEntity));
//			return ResponseEntity.status(HttpStatus.OK).body(new ParticipanteEntity());
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
			personaEntity.setNombres(ParticipanteDTO.getNombres());
			personaEntity.setApellidos(ParticipanteDTO.getApellidos());
			personaEntity.setGenero(ParticipanteDTO.getGenero());
			personaEntity.setFechanacimiento(ParticipanteDTO.getFechanacimiento());
			personaEntity.setEdad(ParticipanteDTO.getEdad());
			personaEntity.setCelular(ParticipanteDTO.getCelular());
			personaEntity.setEmail(ParticipanteDTO.getEmail());
			personaEntity.setDireccion(ParticipanteDTO.getDireccion());
			personaEntity.setEstado(1);
			
			
			ParticipanteEntity ParticipanteEntity=new ParticipanteEntity();
			ParticipanteEntity.setCodigo(ParticipanteDTO.getCodigo());
			ParticipanteEntity.setImagen(ParticipanteDTO.getImagen());
			ParticipanteEntity.setLogo(ParticipanteDTO.getLogo());
			ParticipanteEntity.setGradoacademico(gradoAcademicoService.findById(ParticipanteDTO.getGradoacademico()));
			ParticipanteEntity.setProfesion(profesionService.findById(ParticipanteDTO.getProfesion()));
			ParticipanteEntity.setDepartamento(departamentoService.findById(ParticipanteDTO.getDepartamento()));
			ParticipanteEntity.setLocalidad(ParticipanteDTO.getLocalidad());
			ParticipanteEntity.setPersona(personaEntity);
            
            ParticipanteEntity ParticipanteEntity2=servicio.update(id, ParticipanteEntity);
            System.out.println("REST CONTROLLER CURSO:"+ParticipanteEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(ParticipanteEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }
    @GetMapping("/logourl/{filename}")
    public ResponseEntity<Resource> getFile_logo_s3(@PathVariable String filename) {
        try {
            System.out.println("logO: " + filename);
            
            // Concatenar la ruta completa en S3 donde está almacenada la imagen
            String fileKey = Constantes.nameFolderLogoParticipante + "/" + filename;

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
            ParticipanteEntity ParticipanteEntity = new ParticipanteEntity();
            ParticipanteEntity.setId(ParticipanteDTO.getId());
            ParticipanteEntity.setCodigo(ParticipanteDTO.getCodigo());

            ParticipanteEntity.setCertificado_imagen(ParticipanteDTO.getCertificado_imagen());
            
            ParticipanteEntity ParticipanteEntity2=servicio.customPlantillaCurso(id, ParticipanteEntity);
            System.out.println("REST CONTROLLER CURSO:"+ParticipanteEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(ParticipanteEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }
*/

}
