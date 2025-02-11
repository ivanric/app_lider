//package app.service;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import app.dto.EventoDTO;
//import app.dto.InscritoDTO;
//import app.entity.CertificadoEntity;
//import app.entity.InscritoDetalleEntity;
//import app.entity.InscritoEntity;
//import app.entity.InstitucionEntity;
//import app.entity.ParticipanteEntity;
//import app.entity.PersonaEntity;
//import app.entity.UsuarioEntity;
//import app.repository.InscritoRepository;
//import app.repository.InstitucionRepository;
//import app.repository.PersonaRepository;
//import app.repository.UsuarioRepository;
//import app.repository.ParticipanteRepository;
//import app.repository.CertificadoCursoRepository;
//import app.repository.GenericRepositoryNormal;
//import app.repository.InscritoDetalleRepository;
//import app.util.Constantes;
//import app.util.QRCodeGeneratorService;
//
//@Service
//public class InscritoServiceImplbck extends GenericServiceImplNormal<InscritoEntity, Integer> implements InscritoService {
//
//	@Autowired private PersonaRepository PersonaRepository;
//	@Autowired private ParticipanteRepository ParticipanteRepository;
//	@Autowired private InscritoRepository InscritoRepository;
//	@Autowired private InscritoDetalleRepository InscritoDetalleRepository;
//	@Autowired private UsuarioRepository usuarioRepository;
//
//	@Autowired private CertificadoCursoRepository CertificadoCursoRepository;
//
//	@Autowired private InstitucionRepository InstitucionRepository;
//	
//	@Autowired private ArchivoService archivoService;
//	@Autowired QRCodeGeneratorService qrCodeGeneratorService;
//	@Autowired private BCryptPasswordEncoder passwordEncoder;
//    
//	InscritoServiceImplbck(GenericRepositoryNormal<InscritoEntity, Integer> genericRepository) {
//		super(genericRepository);
//		// TODO Auto-generated constructor stub
//	}
//
//	//sin el @Override porque hereda de
//	public int getIdPrimaryKey() throws Exception {
//		
//        try{
//        	int id = InscritoRepository.getIdPrimaryKey();
//          return id;
//      } catch (Exception e){
//      		System.out.println(e.getMessage());
////          throw new Exception(e.getMessage());
//          return 0;
//      }
//	}
//	@Override
//	public Integer getCodigo() throws Exception {
//		
//        try{
//        	int total = InscritoRepository.getCodigo();
//          return total;
//      } catch (Exception e){
//      		System.out.println(e.getMessage());
////          throw new Exception(e.getMessage());
//          return 0;
//      }
//	}
//	@Override
//	@Transactional
//	public List<InscritoEntity> findAll( int estado,String search,int length,int start ) throws Exception {
//        try{
////            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
//            List<InscritoEntity> entities = InscritoRepository.findAll(estado, search, length, start);
//            return entities;
//        } catch (Exception e){
//        	System.out.println(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//	}
//	
//
//	@Override 
//	@Transactional
//	public void updateStatus(int status, int id) throws Exception {
//		// TODO Auto-generated method stub
//        try{
//        	System.out.println("estado:"+status+" id:"+id);
//        	InscritoRepository.updateStatus(status,id);
//
//        } catch (Exception e){
//        	System.out.println(e.getMessage());
//        	e.printStackTrace();
//            throw new Exception(e.getMessage());
//        }
//	}
//	
//	@Override
//	public Integer getTotAll(String search,int estado) throws Exception {
//		
//        try{
//        	int total = InscritoRepository.getTotAll(search, estado);
//          return total;
//      } catch (Exception e){
//      		System.out.println(e.getMessage());
////          throw new Exception(e.getMessage());
//          return 0;
//      }
//	}
//
//
//    @Transactional
//    public InscritoEntity saveinscrito(InscritoDTO InscritoDTO) throws Exception {
//        try{
//        	System.out.println("EntitySAVE_Servicio:"+InscritoDTO.toString());
//        	
////        	System.out.println("IMAGEN:"+InscritoDTO.getLogo().getOriginalFilename());
//        	
//        	
//        	
//			PersonaEntity personaEntity=new PersonaEntity();
//			personaEntity.setId(PersonaRepository.getIdPrimaryKey());
//			personaEntity.setCi(InscritoDTO.getCi());
//			personaEntity.setExp(InscritoDTO.getExp());
//			personaEntity.setNombrecompleto(InscritoDTO.getNombrecompleto());
//			personaEntity.setGenero(InscritoDTO.getGenero());
//			personaEntity.setFechanacimiento(InscritoDTO.getFechanacimiento());
//			personaEntity.setEdad(InscritoDTO.getEdad());
//			personaEntity.setCelular(InscritoDTO.getCelular());
//			personaEntity.setEmail(InscritoDTO.getEmail());
//			personaEntity.setDireccion(InscritoDTO.getDireccion());
//			personaEntity.setEstado(1);
//			
//			PersonaEntity persona2=PersonaRepository.save(personaEntity);
//			
//			ParticipanteEntity ParticipanteEntity=new ParticipanteEntity();
////			ParticipanteEntity.setImagen(null)
//			ParticipanteEntity.setId(ParticipanteRepository.getIdPrimaryKey());
//			ParticipanteEntity.setCodigo(ParticipanteRepository.getCodigo());
//			ParticipanteEntity.setLogo(InscritoDTO.getArchivoimgparticipante());
//			ParticipanteEntity.setGradoacademico(InscritoDTO.getGradoacademico());
//			ParticipanteEntity.setProfesion(InscritoDTO.getProfesion());
//			ParticipanteEntity.setDepartamento(InscritoDTO.getDepartamento());
//			ParticipanteEntity.setLocalidad(InscritoDTO.getLocalidad());
//			ParticipanteEntity.setPersona(persona2);
//			ParticipanteEntity.setEstado(1);
//			
//        	if (!InscritoDTO.getArchivoimgparticipante().isEmpty()) {
//	    		String nombre="PTE-"+InscritoDTO.getCi()+InscritoDTO.getArchivoimgparticipante().getOriginalFilename().substring(InscritoDTO.getArchivoimgparticipante().getOriginalFilename().lastIndexOf('.'));		
//                // Guardar en Google Drive
//	    		ParticipanteEntity.setImagen(nombre);
//	    		String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoParticipante, InscritoDTO.getArchivoimgparticipante(), nombre);
//                ParticipanteEntity.setImagenDriveId(idArchivoLogoDrive);
//        	}
//			
//			
//			ParticipanteEntity participanteEntity2=this.ParticipanteRepository.save(ParticipanteEntity);
//			
//			List<InscritoDetalleEntity> array_detalleIns=new ArrayList<>();
//			
//			InscritoDetalleEntity InscritoDetalleEntity=new InscritoDetalleEntity();
//			InscritoDetalleEntity.setId(this.InscritoDetalleRepository.getIdPrimaryKey());
//			InscritoDetalleEntity.setCodigo(this.InscritoDetalleRepository.getCodigo());
//			InscritoDetalleEntity.setCantidad(InscritoDTO.getCantidad());
//			InscritoDetalleEntity.setPrecio(InscritoDTO.getPrecio());
//			InscritoDetalleEntity.setDescuento(InscritoDTO.getDescuento());
//			InscritoDetalleEntity.setSubtotal(InscritoDTO.getSubtotal());
//			InscritoDetalleEntity.setEstado(1);
//			InscritoDetalleEntity.setEvento(InscritoDTO.getEvento());
//			
//			InscritoDetalleEntity InscritoDetalleEntity2=this.InscritoDetalleRepository.save(InscritoDetalleEntity);
//			array_detalleIns.add(InscritoDetalleEntity2);
//			
//			InscritoEntity InscritoEntity= new InscritoEntity();
//			InscritoEntity.setId(this.InscritoRepository.getIdPrimaryKey());
//			InscritoEntity.setCodigo(this.InscritoRepository.getCodigo());
//			
//        	//deposito
////			InscritoEntity.setImagen(null);
//			InscritoEntity.setNrocuenta(InscritoDTO.getNrocuenta());
//			InscritoEntity.setBanco(InscritoDTO.getBanco());
//			InscritoEntity.setImporte(InscritoDTO.getImporte());
//			
//			//totales
//			double xtotcantidad=0,xtotdescuento=0,xtotsubtotal=0;
//			
//			if (InscritoDTO.getCantidad()!=0) {
//				xtotcantidad=InscritoDTO.getCantidad();
//			}
//			if (InscritoDTO.getDescuento()!=0) {
//				xtotdescuento=InscritoDTO.getDescuento();
//			}
//			if (InscritoDTO.getSubtotal()!=0) {
//				xtotsubtotal=InscritoDTO.getSubtotal();
//			}
//			InscritoEntity.setCantidad(xtotcantidad);
//			InscritoEntity.setDescuento(xtotdescuento);
//			InscritoEntity.setSubtotal(xtotsubtotal);
//			InscritoEntity.setTotal(xtotsubtotal-xtotdescuento);
//        	InscritoEntity.setEstado(1);
//        	
//        	InscritoEntity.setAnio(InscritoDTO.getAnio());
//        	InscritoEntity.setParticipante(participanteEntity2);
//        	InscritoEntity.setDetalleInscrito(array_detalleIns);
//         	if (!InscritoDTO.getArchivoimgpago().isEmpty()) {
//	    		String nombre="PAGO-"+InscritoDTO.getCi()+InscritoDTO.getArchivoimgpago().getOriginalFilename().substring(InscritoDTO.getArchivoimgpago().getOriginalFilename().lastIndexOf('.'));		
//	    		InscritoEntity.setImagen(nombre);
//	    		// Guardar en Google Drive
//                String idArchivoLogoPagoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoInscrito,InscritoDTO.getArchivoimgpago(), nombre);
//                InscritoEntity.setImagenDriveId(idArchivoLogoPagoDrive);
//        	}
//        	
//         	
//         	
//         	//agregando certificados
//         	int tam= InscritoDTO.getEvento().getEventodetalle().size();
//         	for (int i = 0; i < tam; i++) {
//         		CertificadoEntity CertificadoEntity=new CertificadoEntity();
//         		CertificadoEntity.setId(this.CertificadoCursoRepository.getIdPrimaryKey());
//         		CertificadoEntity.setCodigo(this.CertificadoCursoRepository.getCodigo());
//         		
//         		String nrofolio_x=InscritoDTO.getEvento().getEventodetalle().get(i).getCurso().getNrodocumento()+"-"+participanteEntity2.getCodigo();
//         		System.out.println("nrofolio_x "+ i + ":"+nrofolio_x);
//         		
//         		
//         		//GENERANDO CODE QR
//         		String codigoDocumento = passwordEncoder.encode(nrofolio_x+ "");
//	            codigoDocumento = codigoDocumento.replace("/", "c"); // Eliminar las barras '/' del resultado
//	            codigoDocumento = codigoDocumento.replace(".", "a"); // Eliminar los puntos '.' del resultado
//	            codigoDocumento = codigoDocumento.replace("$", "d"); // Eliminar los signos '$' del resultado
//         		
//	            CertificadoEntity.setNrodocumento(codigoDocumento);
//	            CertificadoEntity.setLinkqr("QRCERT - "+nrofolio_x+ ".png");
//	            InstitucionEntity institucionEntity = InstitucionRepository.findById(1).get();
//	            String bodyQR = institucionEntity.getHost() + "/certificado/" + codigoDocumento;
//	            qrCodeGeneratorService.generateQRCode(Constantes.nameFolderQrIncritoCertificado,bodyQR,"QRCERT - "+nrofolio_x);
//			
//	            CertificadoEntity.setNrofolio(nrofolio_x);
//	            CertificadoEntity.setParticipante(participanteEntity2);
//	            CertificadoEntity.setCurso(InscritoDTO.getEvento().getEventodetalle().get(i).getCurso());
//	            CertificadoEntity.setAnio(InscritoDTO.getAnio());
//	            CertificadoEntity.setEstado(1);
//	            CertificadoEntity CertificadoEntity2=this.CertificadoCursoRepository.save(CertificadoEntity);            
//         	}
//	
//        	System.out.println("EntityInscritoPost:"+InscritoEntity.toString());
//            InscritoEntity InscritoEntity2 = InscritoRepository.save(InscritoEntity);
//            return InscritoEntity2;
//        } catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    } 
//    
///*
//	@Override
//	@Transactional
//	public InscritoEntity update(Integer id, InscritoEntity entidad) throws Exception {
//		try {
//			System.out.println("Modificar Entity:"+entidad.toString());
//			//observado
//
//			InscritoEntity entitymod=InscritoRepository.findById(id).get();
////			entitymod.setCodigo(entidad.getCodigo());
//			entitymod.getPersona().setCi(entidad.getPersona().getCi());
//			entitymod.getPersona().setExp(entidad.getPersona().getExp());
//			entitymod.getPersona().setNombrecompleto(entidad.getPersona().getNombrecompleto());
//			entitymod.getPersona().setGenero(entidad.getPersona().getGenero());
//			entitymod.getPersona().setFechanacimiento(entidad.getPersona().getFechanacimiento());
//			entitymod.getPersona().setEdad(entidad.getPersona().getEdad());
//			entitymod.setGradoacademico(entidad.getGradoacademico());
//			entitymod.setProfesion(entidad.getProfesion());
//			entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
//			entitymod.getPersona().setEmail(entidad.getPersona().getEmail());
//			entitymod.setDepartamento(entidad.getDepartamento());
//			entitymod.setLocalidad(entidad.getLocalidad());
//			entitymod.getPersona().setDireccion(entidad.getPersona().getDireccion());
//			
//
//			System.out.println("VALOR AFICHE:"+entidad.getLogo()!=null);
//			
//			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getLogo().getOriginalFilename());
////			if (entidad.getAfiche()!=null) {
//			if (!entidad.getLogo().isEmpty()) {
//				this.archivoService.eliminarArchivoParticipanteLogo(entitymod.getImagen());
//				String nombre="mod-"+entitymod.getCodigo()+entidad.getLogo().getOriginalFilename().substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
//    			System.out.println("Nombre logo modificar:"+nombre);
//    			System.out.println("entitymod.getImagen():"+entitymod.getImagen());
//        
//				String nombreLogo=archivoService.guargarLogoParticipante(entidad.getLogo(),nombre);
//				entitymod.setImagen(nombreLogo);
//			}
//
//			System.out.println("SE COMPLETO LA MODIFICACION");
////			entitymod=genericRepository.save(entidad);
//			InscritoEntity cursoEntity3=genericRepository.save(entitymod); 
//			System.out.println("ENTIDAD MODIFICADA:"+cursoEntity3);
//			return cursoEntity3;
//		} catch (Exception e) { 
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			throw new Exception(e.getMessage());
//		}
//	}
//	
//*/
//}
