package app.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dto.EventoDTO;
import app.dto.InscritoDTO;
import app.entity.AnioEntity;
import app.entity.CertificadoEntity;
import app.entity.CursoEntity;
import app.entity.EventoEntity;
import app.entity.InscritoDetalleEntity;
import app.entity.InscritoEntity;
import app.entity.InstitucionEntity;
import app.entity.ParticipanteEntity;
import app.entity.InscritoEntity;
import app.entity.PersonaEntity;
import app.entity.UsuarioEntity;
import app.repository.InscritoRepository;
import app.repository.InstitucionRepository;
import app.repository.PersonaRepository;
import app.repository.UsuarioRepository;
import app.repository.ParticipanteRepository;
import app.repository.AnioRepository;
import app.repository.CertificadoCursoRepository;
import app.repository.GenericRepositoryNormal;
import app.repository.InscritoDetalleRepository;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class InscritoServiceImpl extends GenericServiceImplNormal<InscritoEntity, Integer> implements InscritoService {

	@Autowired private PersonaRepository PersonaRepository;
	@Autowired private ParticipanteRepository ParticipanteRepository;
	@Autowired private InscritoRepository InscritoRepository;
	@Autowired private InscritoDetalleRepository InscritoDetalleRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private AnioRepository anioRepository;

	@Autowired private CertificadoCursoRepository CertificadoCursoRepository;

	@Autowired private InstitucionRepository InstitucionRepository;
	
	@Autowired private S3Service s3Service;  // Cambio: Ahora utilizamos S3Service
	@Autowired QRCodeGeneratorService qrCodeGeneratorService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MailService mailService;
	InscritoServiceImpl(GenericRepositoryNormal<InscritoEntity, Integer> genericRepository) {
		super(genericRepository);
	}


	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = InscritoRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	public Integer getCodigo() throws Exception {
		
        try{
        	int total = InscritoRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> findAll( int estado,String search,int length,int start ,int idanio) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = InscritoRepository.findAll(estado, search, length, start,idanio);
            return entities;
        } catch (Exception e){
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	

	@Override 
	@Transactional
	public void updateStatus(int status, int id) throws Exception {
		// TODO Auto-generated method stub
        try{
        	System.out.println("estado:"+status+" id:"+id);
        	InscritoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado,int idanio) throws Exception {
		
        try{
        	int total = InscritoRepository.getTotAll(search, estado,idanio);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


    @Transactional
    public InscritoEntity saveinscrito(InscritoDTO InscritoDTO) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+InscritoDTO.toString());
        	
//        	System.out.println("IMAGEN:"+InscritoDTO.getLogo().getOriginalFilename());
        	
    		LocalDate ahora = LocalDate.now();
//    		int dia = ahora.getDayOfMonth();
//    		int mes = ahora.getMonthValue();
    		long año = ahora.getYear();
    		System.out.println("ANIOOOOOO ANT:"+año);
    		String nombre1=String.valueOf(año);
    		AnioEntity anio=anioRepository.findByNombre(nombre1);
        	
        	
        	PersonaEntity persona2=null;
        	ParticipanteEntity participanteEntity2=null;
        	InscritoEntity InscritoEntity2=null;
        	int codigox_inscrito=0;
        	
        	if (InscritoDTO.getIdper()==null) {
        		PersonaEntity personaExistente  = null;
        		if (PersonaRepository.getPersonaByCi(InscritoDTO.getCi()).size()!=0) {
        			personaExistente  = PersonaRepository.getPersonaByCi(InscritoDTO.getCi()).get(0);
				}
        		
        		if (personaExistente!=null) {
					persona2=personaExistente;
				}else {
					PersonaEntity personaEntity=new PersonaEntity();
	    			personaEntity.setId(PersonaRepository.getIdPrimaryKey());
	    			personaEntity.setCi(InscritoDTO.getCi());
	    			personaEntity.setExp(InscritoDTO.getExp());
	    			personaEntity.setNombres(InscritoDTO.getNombres());
	    			personaEntity.setApellidos(InscritoDTO.getApellidos());
	    			personaEntity.setGenero(InscritoDTO.getGenero());
	    			personaEntity.setFechanacimiento(InscritoDTO.getFechanacimiento());
	    			personaEntity.setEdad(InscritoDTO.getEdad());
	    			personaEntity.setCelular(InscritoDTO.getCelular());
	    			personaEntity.setEmail(InscritoDTO.getEmail());
	    			personaEntity.setDireccion(InscritoDTO.getDireccion());
	    			personaEntity.setEstado(1);
	    			persona2=PersonaRepository.save(personaEntity);
				}
    			
			} else {
				PersonaEntity personabd=PersonaRepository.findById(InscritoDTO.getIdper()).get();
				personabd.setCi(InscritoDTO.getCi());
				personabd.setExp(InscritoDTO.getExp());
				personabd.setNombres(InscritoDTO.getNombres());
				personabd.setApellidos(InscritoDTO.getApellidos());
				personabd.setGenero(InscritoDTO.getGenero());
				personabd.setFechanacimiento(InscritoDTO.getFechanacimiento());
				personabd.setEdad(InscritoDTO.getEdad());
				personabd.setCelular(InscritoDTO.getCelular());
				personabd.setEmail(InscritoDTO.getEmail());
				personabd.setDireccion(InscritoDTO.getDireccion());
				persona2=PersonaRepository.save(personabd);
			}
        	
        	if (InscritoDTO.getIdpart()==null) {
        		ParticipanteEntity participanteExistente = null;
        		if (ParticipanteRepository.getParticipanteByIdPer(persona2.getId()).size()!=0) {
        			participanteExistente = ParticipanteRepository.getParticipanteByIdPer(persona2.getId()).get(0);
				}
        		if (participanteExistente != null) {
        		    participanteEntity2 = participanteExistente;
        		} else {
        		    // Crear nuevo participante

            		
            		// Crear la entidad de Participante
        			ParticipanteEntity ParticipanteEntity=new ParticipanteEntity();
//        			InscritoEntity.setImagen(null)
        			ParticipanteEntity.setId(ParticipanteRepository.getIdPrimaryKey());
        			ParticipanteEntity.setCodigo(ParticipanteRepository.getCodigo());
        			ParticipanteEntity.setLogo(InscritoDTO.getArchivoimgparticipante());
        			ParticipanteEntity.setGradoacademico(InscritoDTO.getGradoacademico());
        			ParticipanteEntity.setProfesion(InscritoDTO.getProfesion());
        			ParticipanteEntity.setDepartamento(InscritoDTO.getDepartamento());
        			if (InscritoDTO.getProvincia()!=null) {
        				ParticipanteEntity.setProvincia(InscritoDTO.getProvincia());
        			}
        			ParticipanteEntity.setProvincia(InscritoDTO.getProvincia());
        			ParticipanteEntity.setLocalidad(InscritoDTO.getLocalidad());
        			ParticipanteEntity.setPersona(persona2);
        			ParticipanteEntity.setEstado(1);
        			
        			// Subir imagen del participante a Amazon S3
        			if (!InscritoDTO.getArchivoimgparticipante().isEmpty()) {
        	    		String nombre = "PTE-" + InscritoDTO.getCi() + InscritoDTO.getArchivoimgparticipante().getOriginalFilename()
        	    				.substring(InscritoDTO.getArchivoimgparticipante().getOriginalFilename().lastIndexOf('.'));
        	    		ParticipanteEntity.setImagen(nombre);

        	    		// Subir archivo a Amazon S3
        	    		String fileKey = s3Service.uploadFileToS3(
        	    				Constantes.nameFolderLogoParticipante,
        	    				InscritoDTO.getArchivoimgparticipante(), 
        	    				nombre
        	    		);
        	    		ParticipanteEntity.setImagenDriveId(fileKey);  // Guardar la clave S3 en lugar del ID de Google Drive
                	}
        			
        			
        			participanteEntity2=this.ParticipanteRepository.save(ParticipanteEntity);
        			
        		}
        		
			} else {
				ParticipanteEntity participantemod=this.ParticipanteRepository.findById(InscritoDTO.getIdpart()).get();
				participantemod.setGradoacademico(InscritoDTO.getGradoacademico());
				participantemod.setProfesion(InscritoDTO.getProfesion());
				participantemod.setDepartamento(InscritoDTO.getDepartamento());
				participantemod.setProvincia(InscritoDTO.getProvincia());
				participantemod.setLocalidad(InscritoDTO.getLocalidad());
				participantemod.setPersona(persona2);
				
				//actualizar la foto del participante
				System.out.println("Serv Modificar afiche LLEGO:"+InscritoDTO.getArchivoimgparticipante().getOriginalFilename());
//				if (entidad.getAfiche()!=null) {
				if (!InscritoDTO.getArchivoimgparticipante().isEmpty()) {
//							          // Eliminar el logo existente de S3 si aplica
		            if (participantemod.getImagen() != null) {
		                System.out.println("Eliminando logo anterior de S3: " + participantemod.getImagen());
		                s3Service.deleteFile(Constantes.nameFolderLogoParticipante + "/" + participantemod.getImagen());
		            }
	                
		            // Generar el nombre para el nuevo logo
		            String nombreLogo = "PTE-"+participantemod.getPersona().getCi() + InscritoDTO.getArchivoimgparticipante().getOriginalFilename()
		                .substring(InscritoDTO.getArchivoimgparticipante().getOriginalFilename().lastIndexOf('.'));
		            System.out.println("Nombre del logo a guardar: " + nombreLogo);
		            
		         // Subir el archivo de logo a S3
		            String fileKey = s3Service.uploadFileToS3(Constantes.nameFolderLogoParticipante, InscritoDTO.getArchivoimgparticipante(), nombreLogo);

		            // Actualizar la entidad con los datos del logo
		            participantemod.setImagen(nombreLogo);
		            participantemod.setImagenDriveId(fileKey); // Guardamos la clave del archivo en S3
				}else {
				    if (participantemod.getImagen() != null) {
		                System.out.println("*******************Eliminando222222 logo anterior de S3: " + participantemod.getImagen());
		                s3Service.deleteFile(Constantes.nameFolderLogoParticipante + "/" + participantemod.getImagen());
		            }
				    participantemod.setImagen(null);
		            participantemod.setImagenDriveId(null);
				}
				participanteEntity2=this.ParticipanteRepository.save(participantemod);
			}
        	System.out.println("registro++");
			System.out.println("participanteEntity2:"+participanteEntity2.toString());
			System.out.println("InscritoDTO:"+InscritoDTO.getEvento().toString());
			
        	boolean existeInscrito = InscritoRepository.existsByParticipanteIdAndEventoId(
        		    participanteEntity2.getId(),
        		    InscritoDTO.getEvento().getId()
        		);
			System.out.println("**********INSCRITO EXISTE:"+existeInscrito);
        	if (!existeInscrito) {

    			List<InscritoDetalleEntity> array_detalleIns=new ArrayList<>();
    			
    			InscritoDetalleEntity InscritoDetalleEntity=new InscritoDetalleEntity();
    			InscritoDetalleEntity.setId(this.InscritoDetalleRepository.getIdPrimaryKey());
    			InscritoDetalleEntity.setCodigo(this.InscritoDetalleRepository.getCodigo());
    			InscritoDetalleEntity.setCantidad(InscritoDTO.getCantidad());
    			InscritoDetalleEntity.setPrecio(InscritoDTO.getPrecio());
    			InscritoDetalleEntity.setDescuento(InscritoDTO.getDescuento());
    			InscritoDetalleEntity.setSubtotal(InscritoDTO.getSubtotal());
    			InscritoDetalleEntity.setEstado(1);
    			InscritoDetalleEntity.setEvento(InscritoDTO.getEvento());
    			
    			InscritoDetalleEntity InscritoDetalleEntity2=this.InscritoDetalleRepository.save(InscritoDetalleEntity);
    			array_detalleIns.add(InscritoDetalleEntity2);
    			
    			InscritoEntity InscritoEntity= new InscritoEntity();
    			InscritoEntity.setId(this.InscritoRepository.getIdPrimaryKey());
    			codigox_inscrito=this.InscritoRepository.getCodigo();
    			InscritoEntity.setCodigo(codigox_inscrito);
            	//deposito
    			InscritoEntity.setNrocuenta(InscritoDTO.getNrocuenta());
    			InscritoEntity.setBanco(InscritoDTO.getBanco());
    			InscritoEntity.setImporte(InscritoDTO.getImporte());
    			
    			//totales
    			double xtotcantidad=0,xtotdescuento=0,xtotsubtotal=0;
    			
    			if (InscritoDTO.getCantidad()!=0) {
    				xtotcantidad=InscritoDTO.getCantidad();
    			}
    			if (InscritoDTO.getDescuento()!=0) {
    				xtotdescuento=InscritoDTO.getDescuento();
    			}
    			if (InscritoDTO.getSubtotal()!=0) {
    				xtotsubtotal=InscritoDTO.getSubtotal();
    			}
    			InscritoEntity.setCantidad(xtotcantidad);
    			InscritoEntity.setDescuento(xtotdescuento);
    			InscritoEntity.setSubtotal(xtotsubtotal);
    			InscritoEntity.setTotal(xtotsubtotal-xtotdescuento);
            	InscritoEntity.setEstado(1);
            	InscritoEntity.setAnio(anio);
            	InscritoEntity.setParticipante(participanteEntity2);
            	InscritoEntity.setDetalleInscrito(array_detalleIns);
            	
            	// Subir imagen del pago a Amazon S3
                if (!InscritoDTO.getArchivoimgpago().isEmpty()) {
                    String nombrePago = "PAGO-"+codigox_inscrito+"-" + InscritoDTO.getCi()
                        + InscritoDTO.getArchivoimgpago().getOriginalFilename()
                          .substring(InscritoDTO.getArchivoimgpago().getOriginalFilename().lastIndexOf('.'));
                    
                    InscritoEntity.setImagen(nombrePago);

                    // Subir archivo de pago a Amazon S3
                    String fileKeyPago = s3Service.uploadFileToS3(
                        Constantes.nameFolderLogoPagoInscrito,
                        InscritoDTO.getArchivoimgpago(),
                        nombrePago
                    );
                    InscritoEntity.setImagenDriveId(fileKeyPago); // Guardar la clave S3
                }
            	System.out.println("EntityInscritoPost:"+InscritoEntity.toString());
                InscritoEntity2 = InscritoRepository.save(InscritoEntity);
			}
        	
         	//agregando certificados
         	int tam= InscritoDTO.getEvento().getEventodetalle().size();
         	for (int i = 0; i < tam; i++) {
         		// Datos clave para validar duplicidad
         		ParticipanteEntity participante_b = participanteEntity2;
         		CursoEntity curso_b = InscritoDTO.getEvento().getEventodetalle().get(i).getCurso();
         		EventoEntity evento_b = InscritoDTO.getEvento();
//         		String tipoCertificado = InscritoDTO.getTipocertificado();
             	boolean existecertificado=CertificadoCursoRepository.existsCertificado(participanteEntity2.getId(), curso_b.getId(), evento_b.getId());
             	
             	
             	if (existecertificado) {
            		System.out.println("⚠️ Certificado duplicado: ya existe un certificado para este participante, curso y evento.");
            	}else {
            		CertificadoEntity CertificadoEntity=new CertificadoEntity();
             		CertificadoEntity.setId(this.CertificadoCursoRepository.getIdPrimaryKey());
             		CertificadoEntity.setCodigo(this.CertificadoCursoRepository.getCodigo());
             		 // Generar y guardar QR
//             		String nrofolio_x=InscritoDTO.getEvento().getEventodetalle().get(i).getCurso().getNrodocumento()+"-"+codigox_inscrito+"-"+participanteEntity2.getCodigo();
             		String nrofolio_x = curso_b.getNrodocumento() + "-" + codigox_inscrito + "-" + participante_b.getCodigo();
             		System.out.println("nrofolio_x "+ i + ":"+nrofolio_x);
             		
             		
             	// Generar código QR
                    String codigoDocumento = passwordEncoder.encode(nrofolio_x + "");
                    codigoDocumento = codigoDocumento.replace("/", "c").replace(".", "a").replace("$", "d");
                    
    	            CertificadoEntity.setNrodocumento(codigoDocumento);
    	            CertificadoEntity.setLinkqr("QRCERT - "+nrofolio_x+ ".png");
    	            
    	            InstitucionEntity institucionEntity = InstitucionRepository.findById(1).get();
    	            String bodyQR = institucionEntity.getHost() + "/certificado/" + codigoDocumento;
    	            qrCodeGeneratorService.generateQRCode(
    	            		Constantes.nameFolderQrIncritoCertificado,
    	            		bodyQR,
    	            		"QRCERT - "+nrofolio_x
    	            );
    			
    	            CertificadoEntity.setNrofolio(nrofolio_x);
    	            CertificadoEntity.setParticipante(participanteEntity2);
    	            CertificadoEntity.setCurso(curso_b);
    	            CertificadoEntity.setAnio(anio);
    	            CertificadoEntity.setEstado(1);
    	            CertificadoEntity.setEvento(InscritoDTO.getEvento());
    	            CertificadoEntity.setLugarcurso(InscritoDTO.getEvento().getEventodetalle().get(i).getLugarcurso());
    	            CertificadoEntity.setHorasacademicas(InscritoDTO.getEvento().getEventodetalle().get(i).getHorasacademicas());
    	            CertificadoEntity.setExpositor(InscritoDTO.getEvento().getEventodetalle().get(i).getExpositor());
    	            CertificadoEntity.setTipocertificado(InscritoDTO.getTipocertificado());
    	            CertificadoEntity CertificadoEntity2=this.CertificadoCursoRepository.save(CertificadoEntity);
    	         // Guardar InscritoEntity final
            	}
         		
         	            
         	}
	

        	 
            
            
            //aqui enviamos el mensaje
            
            String send_detalle_email="";
            if (InscritoDTO.getEvento().getDetalleemail()!=null) {
            	send_detalle_email=InscritoDTO.getEvento().getDetalleemail();
			}else {
				send_detalle_email=Constantes.DETALLE_EMAIL;
			}
            Map<String, Object> variables = Map.of(
                    "nombre", InscritoDTO.getNombres()+" "+InscritoDTO.getApellidos(),
                    "detalle", send_detalle_email,
                    "evento", InscritoDTO.getEvento().getDetalle(),
                    "fecha", InscritoDTO.getEvento().getFechainicial(),
                    "grupo", InscritoDTO.getEvento().getLinkgrupo(),
                    "pagina", Constantes.NOMBRE_PAGINA_EMAIL
                );
             // Enviar correo
                mailService.sendEmailWithTemplate(
                    InscritoDTO.getEmail(),
                    "Registro de Evento",
                    "email-inscrito.html",
                    variables,
                    "no-reply@" + "academialider".toLowerCase() + ".com"
                );
            return InscritoEntity2;
        } catch (Exception e){
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    } 

	@Override
	public InscritoEntity getInscritoByCi(String  search,int idevento) throws Exception {
		InscritoEntity entity= new InscritoEntity();
        try{
        	entity= InscritoRepository.getInscritoByCi(search, idevento);
          return entity;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return null;  
      }
	}
	@Override
	public InscritoEntity getInscritoByIdEventoByIdPart(Integer idevent, Integer idpart) throws Exception {
		InscritoEntity entity= new InscritoEntity();
        try{
        	entity= InscritoRepository.getInscritoByIdEventoByIdPart(idevent, idpart);
          return entity;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return null;  
      }
	}
/*
	@Override
	@Transactional
	public InscritoEntity update(Integer id, InscritoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			InscritoEntity entitymod=InscritoRepository.findById(id).get();
//			entitymod.setCodigo(entidad.getCodigo());
			entitymod.getPersona().setCi(entidad.getPersona().getCi());
			entitymod.getPersona().setExp(entidad.getPersona().getExp());
			entitymod.getPersona().setNombrecompleto(entidad.getPersona().getNombrecompleto());
			entitymod.getPersona().setGenero(entidad.getPersona().getGenero());
			entitymod.getPersona().setFechanacimiento(entidad.getPersona().getFechanacimiento());
			entitymod.getPersona().setEdad(entidad.getPersona().getEdad());
			entitymod.setGradoacademico(entidad.getGradoacademico());
			entitymod.setProfesion(entidad.getProfesion());
			entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
			entitymod.getPersona().setEmail(entidad.getPersona().getEmail());
			entitymod.setDepartamento(entidad.getDepartamento());
			entitymod.setLocalidad(entidad.getLocalidad());
			entitymod.getPersona().setDireccion(entidad.getPersona().getDireccion());
			

			System.out.println("VALOR AFICHE:"+entidad.getLogo()!=null);
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getLogo().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getLogo().isEmpty()) {
				this.archivoService.eliminarArchivoParticipanteLogo(entitymod.getImagen());
				String nombre="mod-"+entitymod.getCodigo()+entidad.getLogo().getOriginalFilename().substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombre logo modificar:"+nombre);
    			System.out.println("entitymod.getImagen():"+entitymod.getImagen());
        
				String nombreLogo=archivoService.guargarLogoParticipante(entidad.getLogo(),nombre);
				entitymod.setImagen(nombreLogo);
			}

			System.out.println("SE COMPLETO LA MODIFICACION");
//			entitymod=genericRepository.save(entidad);
			InscritoEntity cursoEntity3=genericRepository.save(entitymod); 
			System.out.println("ENTIDAD MODIFICADA:"+cursoEntity3);
			return cursoEntity3;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
*/



}
