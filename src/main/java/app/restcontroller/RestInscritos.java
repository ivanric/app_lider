package app.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.dto.InscritoDTO;
import app.entity.InscritoEntity;
import app.entity.ParticipanteEntity;
import app.entity.PersonaEntity;
import app.service.DepartamentoService;
import app.service.GradoAcademicoService;
import app.service.InscritoServiceImpl;
import app.service.MailService;
import app.service.ParticipanteService;
import app.service.PersonaService;
import app.service.ProfesionService;
import app.util.Constantes;

@RestController
@RequestMapping("/RestInscritos") 
public class RestInscritos extends RestControllerGenericNormalImpl<InscritoEntity,InscritoServiceImpl> {

	@Autowired GradoAcademicoService gradoAcademicoService;
	@Autowired ProfesionService profesionService;
	@Autowired DepartamentoService departamentoService;
	@Autowired PersonaService personaService;
	@Autowired ParticipanteService participanteService;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping("/listar")
	public ResponseEntity<?> getAll(HttpServletRequest request,@Param("draw")int draw,@Param("length")int length,@Param("start")int start,@Param("estado")int estado)throws IOException{
		String total="";
		Map<String, Object> Data = new HashMap<String, Object>();
		try {

			String search = request.getParameter("search[value]");
			int tot=Constantes.NUM_MAX_DATATABLE;
			Integer idanio = -1;
	        if (request.getParameter("idanio") != null && !request.getParameter("idanio").isEmpty()) {
	            idanio = Integer.parseInt(request.getParameter("idanio"));
	            System.out.println("idanio:" + idanio);
	        }
			System.out.println("tot:"+tot+"estado:"+estado+"search:"+search+"length:"+length+"start:"+start);
			List<Map<String, Object>> lista= servicio.findAll(estado, search, length, start,idanio);
			System.out.println("listar:"+lista.toString()); 
			try {

					total=String.valueOf(servicio.getTotAll(search,estado,idanio));	
						
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
    public ResponseEntity<?> updateStatus(@RequestBody InscritoEntity entity) {
        try {
        	System.out.println("Entidad:"+entity.toString());
        	servicio.updateStatus(entity.getEstado(), entity.getId());
        	InscritoEntity entity2=servicio.findById(entity.getId());
            return ResponseEntity.status(HttpStatus.OK).body(entity2);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    
    
    @PostMapping("/guardarinscrito")
	public ResponseEntity<?> save( InscritoDTO InscritoDTO){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("Entidad inscrito LLEGO:"+InscritoDTO.toString());

		try {
			System.out.println("**IMG PARTICIPANTE**:"+InscritoDTO.getArchivoimgparticipante().getOriginalFilename());
			System.out.println("**IMG PAGO**:"+InscritoDTO.getArchivoimgpago().getOriginalFilename());
			

			
			
			/*
//			
			// Enviar correo
            String subject = "Confirmación de Registro";
            String body = "<h1>¡Gracias por registrarte!</h1>" +
                          "<p>Hola " + InscritoDTO.getNombrecompleto() + "</p>" +
                          "<p>Tu inscripción se realizó correctamente.</p>" +
                          "<p><b>Detalles:</b></p>" +
                          "<ul>" +
                          "<li>Evento: " + InscritoDTO.getEvento().getDetalle() + "</li>" +
                          "<li>Grupo de Whatsapp: " + InscritoDTO.getEvento().getLinkgrupo() + "</li>" +
                          "</ul>" +
                          "<p>Atentamente,<br>Equipo de " + "EQUIPO DE ACADEMIA LIDER" + "</p>";

            String to = InscritoDTO.getEmail();
            String from = "no-reply@" + "ACADEMIALIDER" + ".com";

            mailService.sendEmail(to, subject, body, from);
            */
          
            
       //aqui enviamos el mensaje
            
//            String send_detalle_email="";
//            if (InscritoDTO.getEvento().getDetalleemail()!=null) {
//            	send_detalle_email=InscritoDTO.getEvento().getDetalleemail();
//			}else {
//				send_detalle_email=Constantes.DETALLE_EMAIL;
//			}
//            Map<String, Object> variables = Map.of(
//                    "nombre", InscritoDTO.getNombrecompleto(),
//                    "detalle", send_detalle_email,
//                    "evento", InscritoDTO.getEvento().getDetalle(),
//                    "fecha", InscritoDTO.getEvento().getFechainicial(),
//                    "grupo", InscritoDTO.getEvento().getLinkgrupo(),
//                    "pagina", Constantes.NOMBRE_PAGINA_EMAIL
//                );
//             // Enviar correo
//                mailService.sendEmailWithTemplate(
//                    InscritoDTO.getEmail(),
//                    "Registro de Evento",
//                    "email-inscrito.html",
//                    variables,
//                    "no-reply@" + "academialider".toLowerCase() + ".com"
//                );
            return ResponseEntity.status(HttpStatus.OK).body(servicio.saveinscrito(InscritoDTO));
//			return ResponseEntity.status(HttpStatus.OK).body(new InscritoEntity());
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    

    
    
	@RequestMapping({"findEstudiante"})
	public ResponseEntity<?> existeCi(HttpServletRequest req) throws Exception{
		Map<String, Object> mapa=new HashMap<String, Object>();
		String ci=req.getParameter("ci");
		Integer idevento=Integer.parseInt(req.getParameter("idevento"));
		boolean existe;
		System.out.println("tam_"+ci.length());
		PersonaEntity persona=null;
		ParticipanteEntity participante=null;
		InscritoEntity inscrito=null;
		if (this.servicio.getInscritoByCi(ci, idevento)!=null) {
			mapa.put("inscrito", true);
		} else {
			mapa.put("inscrito", false);
		}
		
		
		if(this.personaService.getPersonaByCi(ci)!=null){
			persona=this.personaService.getPersonaByCi(ci);
			if (this.participanteService.getParticipanteByCi(ci)!=null) {
				participante=participanteService.getParticipanteByCi(ci);
			}else {
				participante=null;	
			}
		}else{
//			existe=false;
			persona=null;
			participante=null;	
		}
//		System.out.println("existe: "+existe);
		
		System.out.println("persona:"+persona);
		System.out.println("participante:"+participante);
		mapa.put("persona", persona);
		mapa.put("participante", participante);
		return new ResponseEntity<Map<String,Object>>(mapa,HttpStatus.OK);
	}
	/*
    @PostMapping("/guardar")
	public ResponseEntity<?> save( InscritoDTO InscritoDTO,@RequestParam("logo") MultipartFile file){
//	public ResponseEntity<?> save(CatalogoEntity entidad){
		System.out.println("EntidadSave LLEGO:"+InscritoDTO.toString());

		try {
			System.out.println("**AFICHE**:"+InscritoDTO.getLogo().getOriginalFilename());
			
			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(InscritoDTO.getCi());
			personaEntity.setExp(InscritoDTO.getExp());
			personaEntity.setNombrecompleto(InscritoDTO.getNombrecompleto());
			personaEntity.setGenero(InscritoDTO.getGenero());
			personaEntity.setFechanacimiento(InscritoDTO.getFechanacimiento());
			personaEntity.setEdad(InscritoDTO.getEdad());
			personaEntity.setCelular(InscritoDTO.getCelular());
			personaEntity.setEmail(InscritoDTO.getEmail());
			personaEntity.setDireccion(InscritoDTO.getDireccion());
			personaEntity.setEstado(1);
			
			
			InscritoEntity InscritoEntity=new InscritoEntity();
			InscritoEntity.setImagen(InscritoDTO.getImagen());
			InscritoEntity.setLogo(InscritoDTO.getLogo());
			InscritoEntity.setGradoacademico(gradoAcademicoService.findById(InscritoDTO.getGradoacademico()));
			InscritoEntity.setProfesion(profesionService.findById(InscritoDTO.getProfesion()));
			InscritoEntity.setDepartamento(departamentoService.findById(InscritoDTO.getDepartamento()));
			InscritoEntity.setLocalidad(InscritoDTO.getLocalidad());
			InscritoEntity.setPersona(personaEntity);
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(InscritoEntity));
//			return ResponseEntity.status(HttpStatus.OK).body(new InscritoEntity());
		} catch (Exception e) {//BAD_REQUEST= es error 400
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
		}
	}
    
    @PostMapping("/modificar/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, InscritoDTO InscritoDTO, @RequestParam("logo") MultipartFile file) {
        try {

			PersonaEntity personaEntity=new PersonaEntity();
			personaEntity.setCi(InscritoDTO.getCi());
			personaEntity.setExp(InscritoDTO.getExp());
			personaEntity.setNombrecompleto(InscritoDTO.getNombrecompleto());
			personaEntity.setGenero(InscritoDTO.getGenero());
			personaEntity.setFechanacimiento(InscritoDTO.getFechanacimiento());
			personaEntity.setEdad(InscritoDTO.getEdad());
			personaEntity.setCelular(InscritoDTO.getCelular());
			personaEntity.setEmail(InscritoDTO.getEmail());
			personaEntity.setDireccion(InscritoDTO.getDireccion());
			personaEntity.setEstado(1);
			
			
			InscritoEntity InscritoEntity=new InscritoEntity();
			InscritoEntity.setCodigo(InscritoDTO.getCodigo());
			InscritoEntity.setImagen(InscritoDTO.getImagen());
			InscritoEntity.setLogo(InscritoDTO.getLogo());
			InscritoEntity.setGradoacademico(gradoAcademicoService.findById(InscritoDTO.getGradoacademico()));
			InscritoEntity.setProfesion(profesionService.findById(InscritoDTO.getProfesion()));
			InscritoEntity.setDepartamento(departamentoService.findById(InscritoDTO.getDepartamento()));
			InscritoEntity.setLocalidad(InscritoDTO.getLocalidad());
			InscritoEntity.setPersona(personaEntity);
            
            InscritoEntity InscritoEntity2=servicio.update(id, InscritoEntity);
            System.out.println("REST CONTROLLER CURSO:"+InscritoEntity2);
            return ResponseEntity.status(HttpStatus.OK).body(InscritoEntity2);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver respuesta de error
            e.printStackTrace();

			System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente más tarde. \"}");
        }
    }
*/

}
