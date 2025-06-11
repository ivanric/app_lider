package app.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dto.EventoDTO;
import app.entity.CursoEntity;
import app.entity.EventoDetalleEntity;
import app.entity.EventoEntity;
import app.entity.ExpositorEntity;
import app.entity.InstitucionEntity;
import app.entity.UsuarioEntity;
import app.repository.EventoDetalleRepository;
import app.repository.EventoRepository;
import app.repository.GenericRepositoryNormal;
import app.repository.InstitucionRepository;
import app.repository.ProfesionRepository;
import app.repository.UsuarioRepository;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class EventoServiceImpl extends GenericServiceImplNormal<EventoEntity, Integer> implements EventoService {

    @Autowired private S3Service s3Service;  // Inyectamos el servicio S3
    @Autowired private EventoRepository EventoRepository;
    @Autowired private EventoDetalleRepository eventoDetalleRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private InstitucionRepository institucionRepository;
    @Autowired private ProfesionRepository profesionRepository;
    @Autowired private PersonaService personaService;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
//    @Autowired private ArchivoService archivoService;
    @Autowired QRCodeGeneratorService qrCodeGeneratorService;
    
    
	EventoServiceImpl(GenericRepositoryNormal<EventoEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = EventoRepository.getIdPrimaryKey();
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
        	int total = EventoRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	

	@Override
	@Transactional
	public List<EventoEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<EventoEntity> entities = EventoRepository.findAll(estado, search, length, start);
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
        	EventoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = EventoRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


    @Override
    @Transactional
    public EventoEntity save(EventoEntity entity,List <CursoEntity> curso,List <ExpositorEntity> expositor,
    		List<LocalDate> fechacurso,List<String> literalfechacurso,
    		List <LocalTime> horainicio,List <LocalTime> horafin,List<String>lugarcurso,List<Integer>horasacademicas) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());
        	
        	//agregando a la bd el detalle del evento, los cursos
        	List<EventoDetalleEntity> array_eventodetalle=new ArrayList<>();
        	
        	for (int i = 0; i < curso.size(); i++) {
        		EventoDetalleEntity eventoDetalleEntity=new EventoDetalleEntity();
        		eventoDetalleEntity.setId(this.eventoDetalleRepository.getIdPrimaryKey());
        		eventoDetalleEntity.setFechacurso(fechacurso.get(i));
//        		eventoDetalleEntity.setLiteralfechacurso(literalfechacurso.get(i));
        		eventoDetalleEntity.setHorainicio(horainicio.get(i));
        		eventoDetalleEntity.setHorafin(horafin.get(i));
        		eventoDetalleEntity.setLugarcurso(lugarcurso.get(i));
        		eventoDetalleEntity.setHorasacademicas(horasacademicas.get(i));
        		eventoDetalleEntity.setCurso(curso.get(i));
        		eventoDetalleEntity.setExpositor(expositor.get(i));
        		eventoDetalleEntity.setEstado(1);
        		EventoDetalleEntity savedEventoDetalle = this.eventoDetalleRepository.save(eventoDetalleEntity);
                array_eventodetalle.add(savedEventoDetalle);
			}
        	entity.setEventodetalle(array_eventodetalle);//agregando el detalle al evento
        	
        	
        	System.out.println("IMAGEN:"+entity.getAfiche().getOriginalFilename());
        	int id_x=this.EventoRepository.getIdPrimaryKey();
        	entity.setId(id_x);
        	entity.setCodigo(EventoRepository.getCodigo());
        	
            // Generar código evento
            String codigoevento_encript = passwordEncoder.encode(EventoRepository.getCodigo() + "");
            codigoevento_encript = codigoevento_encript.replace("/", "a").replace(".", "l").replace("$", "j").replace("_", "a");
            entity.setCodigoevento(codigoevento_encript);
            

        	String nroevento_x="AL-EVT-"+this.EventoRepository.getCodigo();
        	entity.setNroevento(nroevento_x);
        	
            //agregando el nombre del qr,para obtener la imagen qr
            entity.setLinkqr("QR - "+entity.getNroevento()+ ".png");
            
            // GENERAR QR localmete y en drive guarda
            InstitucionEntity institucionEntity = institucionRepository.findById(1).get();
            String bodyQR = institucionEntity.getHost() + "/evento/id/" + codigoevento_encript;
            qrCodeGeneratorService.generateQRCode(Constantes.nameFolderQrEvento,bodyQR,"QR-"+entity.getNroevento());
        	
        	if (!entity.getAfiche().isEmpty()) {
        		
        		String nombre="EVENTO-"+nroevento_x+entity.getAfiche().getOriginalFilename()
        				.substring(entity.getAfiche().getOriginalFilename().lastIndexOf('.'));
				
				
				  // Subir archivo de imagen a Amazon S3
			    String fileKey = s3Service.uploadFileToS3(
			        Constantes.nameFolderLogoEvento,
			        entity.getAfiche(),
			        nombre
			    );
			    entity.setImagen(nombre);
			    entity.setImageneventoDriveId(fileKey); // Guardar la clave de S3
			}

        	
//        	agregar usuario
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    		String login = authentication.getName();
    		System.out.println("LOGUEADOOOOOOOOOOOOOO:"+login); 
    		UsuarioEntity user = usuarioRepository.getUserByLogin(login);
        	entity.setUsuario(user);
    		
        	System.out.println("EntityPost:"+entity.toString());
            entity = EventoRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
	@Override
	@Transactional
	public EventoEntity update(Integer id, EventoDTO eventoDTO) throws Exception {
		try {
			System.out.println("Modificar Entity:"+eventoDTO.toString());
			EventoEntity entitymod=this.EventoRepository.findById(id).get();
			
			//eliminando el detalle del evento
			
			for (int i = 0; i < entitymod.getEventodetalle().size(); i++) {
				this.eventoDetalleRepository.delete(entitymod.getEventodetalle().get(i));
				//se elimino los detalles del evento
			}
			
        	//agregando a la bd el detalle del evento, los cursos
        	List<EventoDetalleEntity> array_eventodetalle=new ArrayList<>();
        	System.out.println("tam DE curso: "+eventoDTO.getCurso().size());
        	
        	for (int i = 0; i < eventoDTO.getCurso().size(); i++) {
        		System.out.println("CURSOooooo:"+eventoDTO.getCurso().get(i));
        		EventoDetalleEntity eventoDetalleEntity=new EventoDetalleEntity();
        		eventoDetalleEntity.setId(this.eventoDetalleRepository.getIdPrimaryKey());
        		eventoDetalleEntity.setFechacurso(eventoDTO.getFechacurso().get(i));
//        		eventoDetalleEntity.setLiteralfechacurso(literalfechacurso.get(i));
        		eventoDetalleEntity.setHorainicio(eventoDTO.getHorainicio().get(i));
        		eventoDetalleEntity.setHorafin(eventoDTO.getHorafin().get(i));
        		eventoDetalleEntity.setLugarcurso(eventoDTO.getLugarcurso().get(i));
        		eventoDetalleEntity.setHorasacademicas(eventoDTO.getHorasacademicas().get(i));
        		eventoDetalleEntity.setCurso(eventoDTO.getCurso().get(i));
        		eventoDetalleEntity.setExpositor(eventoDTO.getExpositor().get(i));
        		eventoDetalleEntity.setEstado(1);
        		EventoDetalleEntity savedEventoDetalle = this.eventoDetalleRepository.save(eventoDetalleEntity);
                array_eventodetalle.add(savedEventoDetalle);
			}
        	entitymod.setEventodetalle(array_eventodetalle);//agregando el detalle al evento
			
			
			entitymod.setAnio(eventoDTO.getAnio());
			entitymod.setModalidad(eventoDTO.getModalidad());
			entitymod.setDetalle(eventoDTO.getDetalle());
			entitymod.setTipo(eventoDTO.getTipo());
			entitymod.setPrecio(eventoDTO.getPrecio());
			entitymod.setMetodopagosino(eventoDTO.getMetodopagosino());
			entitymod.setMetodopagos(eventoDTO.getMetodopagos());
			entitymod.setFechainicial(eventoDTO.getFechainicial());
			entitymod.setFechafinal(eventoDTO.getFechafinal());
			entitymod.setFecharegistrofin(eventoDTO.getFecharegistrofin());
			entitymod.setHora(eventoDTO.getHora());
			entitymod.setNroestudiantescupo(eventoDTO.getNroestudiantescupo());
			entitymod.setConcertificado(eventoDTO.getConcertificado());
			entitymod.setConevaluacion(eventoDTO.getConevaluacion());
			entitymod.setLugarevento(eventoDTO.getLugarevento());
			entitymod.setLinkgrupo(eventoDTO.getLinkgrupo());
			
            if (!eventoDTO.getAfiche().isEmpty()) {
                if (entitymod.getImagen() != null) {
                    // Si ya existe una imagen, eliminarla de S3
                    System.out.println("Eliminando archivo anterior: " + entitymod.getImagen());
                    s3Service.deleteFile(Constantes.nameFolderLogoCurso + "/" + entitymod.getImagen());
                }


        		String nombre="EVENTO-"+entitymod.getNroevento()+eventoDTO.getAfiche().getOriginalFilename()
        				.substring(eventoDTO.getAfiche().getOriginalFilename().lastIndexOf('.'));
                
                // Establecer el nombre del archivo en la entidad
                entitymod.setImagen(nombre);

                // Subir el archivo a Amazon S3
                String fileKey = s3Service.uploadFileToS3(
                    Constantes.nameFolderLogoEvento,  // Carpeta en S3 donde se almacenará el archivo
                    eventoDTO.getAfiche(),  // El archivo de imagen a subir
                    nombre  // El nombre del archivo en S3
                );
                // Guardar la clave del archivo en S3 en el campo 'imagencertificadoDriveId'
                entitymod.setImageneventoDriveId(fileKey);  // Aquí guardamos la clave de S3
            }
            entitymod=genericRepository.save(entitymod);
			return entitymod;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	/*
	@Override
	@Transactional
	public EventoEntity update(Integer id, EventoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			EventoEntity entitymod=EventoRepository.findById(id).get();
			entitymod.setCodigo(entidad.getCodigo());
			entitymod.setNrodocumento(entidad.getNrodocumento());
//			entitymod.setImagen(entidad.getImagen());
			entitymod.setAfiche(entidad.getAfiche());
			entitymod.setCategoria(entidad.getCategoria());
			entitymod.setNombrecurso(entidad.getNombrecurso());
			entitymod.setModalidad(entidad.getModalidad());
			entitymod.setNivel(entidad.getNivel());
			entitymod.setFechainicial(entidad.getFechainicial());
			entitymod.setFechafinal(entidad.getFechafinal());
			entitymod.setHora(entidad.getHora());
			entitymod.setPrecio(entidad.getPrecio());
			entitymod.setExpositores(entidad.getExpositores());
			System.out.println("VALOR AFICHE:"+entidad.getAfiche()!=null);
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getAfiche().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getAfiche().isEmpty()) {
				if (entitymod.getImagen()!=null) {
					this.archivoService.eliminarArchivoCursoAfiche(entitymod.getImagen());
					System.out.println("entitymod.getImagen():"+entitymod.getImagen());
				}		
				String nombre="mod-"+entitymod.getCodigo()+entidad.getAfiche().getOriginalFilename().substring(entidad.getAfiche().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombreafichemodificar:"+nombre);
				String nombreLogo=archivoService.guargarCursoAfiche(entidad.getAfiche(),nombre);
				entitymod.setImagen(nombreLogo);
			}

			if (!entidad.getCertificado_imagen().isEmpty()) {
				if (entitymod.getImagencertificado()!=null) {
					this.archivoService.eliminarArchivoCursoPlantilla(entitymod.getImagencertificado());
					System.out.println("entitymod.getImagen():"+entitymod.getImagencertificado());
				}
				String nombre="ptl-"+entitymod.getNrodocumento()+entidad.getCertificado_imagen().getOriginalFilename().substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);
    			     		

				String nombrePlantilla=archivoService.guargarCursoPlantilla(entidad.getCertificado_imagen(),nombre);
				entitymod.setImagencertificado(nombrePlantilla);
			}
			
			
			System.out.println("SE COMPLETO LA MODIFICACION");
//			entitymod=genericRepository.save(entidad);
			EventoEntity cursoEntity3=genericRepository.save(entitymod); 
			System.out.println("ENTIDAD MODIFICADA:"+cursoEntity3);
			return cursoEntity3;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	@Override
	@Transactional
	public EventoEntity customPlantillaCurso(Integer id, EventoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar plantilla Entity :"+entidad.toString());
			//observado

			EventoEntity entitymod=EventoRepository.findById(id).get();
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getCertificado_imagen().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getCertificado_imagen().isEmpty()) {
				if (entitymod.getImagencertificado()!=null) {
					this.archivoService.eliminarArchivoCursoPlantilla(entitymod.getImagencertificado());
					System.out.println("entitymod.getImagen():"+entitymod.getImagencertificado());
				}
				String nombre="ptl-"+entitymod.getNrodocumento()+entidad.getCertificado_imagen().getOriginalFilename().substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);
    			     		

				String nombrePlantilla=archivoService.guargarCursoPlantilla(entidad.getCertificado_imagen(),nombre);
				entitymod.setImagencertificado(nombrePlantilla);
			}

			
//			entitymod=genericRepository.save(entidad);
			entitymod=genericRepository.save(entitymod);
			return entitymod;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}<
	  */
	@Override
	public Integer getTotCursoPorCategoria(int idubicacion) throws Exception {
		
        try{
        	int total = EventoRepository.getTotCursoPorCategoria(idubicacion);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


	@Override
	public EventoEntity customPlantillaCurso(Integer id, EventoEntity entidad) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public EventoEntity findByCodeEncrypt(String  codigo) throws Exception {
		EventoEntity entity= new EventoEntity();
        try{
        	entity= EventoRepository.findByCodigoevento(codigo);
          return entity;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return null;  
      }
	}
	
  
}
