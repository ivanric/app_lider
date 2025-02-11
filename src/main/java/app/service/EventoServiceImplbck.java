package app.service;

/*
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
public class EventoServiceImplbck extends GenericServiceImplNormal<EventoEntity, Integer> implements EventoService {

	@Autowired private EventoRepository EventoRepository;
	@Autowired private EventoDetalleRepository eventoDetalleRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private InstitucionRepository institucionRepository;
	@Autowired private ProfesionRepository profesionRepository;
	@Autowired private PersonaService personaService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private ArchivoService archivoService;
	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;
	
	@Value("${server.port}")
    private static String puertoservidor;
	

	

    private String IPPUBLICA = ""; // Configura tu IP pública manualmente
    private String PORT= ""; // ejemplo de puerto
    
    
	EventoServiceImplbck(GenericRepositoryNormal<EventoEntity, Integer> genericRepository) {
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
    public EventoEntity save(EventoEntity entity,List <CursoEntity> curso,List <ExpositorEntity> expositor,List<LocalDate> fechacurso,List<String> literalfechacurso,List <LocalTime> horainicio,List <LocalTime> horafin,List<String>lugarcurso,List<Integer>horasacademicas) throws Exception {
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
        		
        		EventoDetalleEntity eventoDetalleEntity2=this.eventoDetalleRepository.save(eventoDetalleEntity);
        		array_eventodetalle.add(eventoDetalleEntity2);
			}
        	entity.setEventodetalle(array_eventodetalle);//agregando el detalle al evento
        	
        	
        	System.out.println("IMAGEN:"+entity.getAfiche().getOriginalFilename());
        	int id_x=this.EventoRepository.getIdPrimaryKey();
        	entity.setId(id_x);
        	entity.setCodigo(EventoRepository.getCodigo());
        	//agregando codigo evento para entrar desde link
            String codigoevento_encript = passwordEncoder.encode(EventoRepository.getCodigo() + "");
            codigoevento_encript = codigoevento_encript.replace("/", "a"); // Eliminar las barras '/' del resultado
            codigoevento_encript = codigoevento_encript.replace(".", "l"); // Eliminar los puntos '.' del resultado
            codigoevento_encript = codigoevento_encript.replace("$", "j"); // Eliminar los signos '$' del resultado
            codigoevento_encript = codigoevento_encript.replace("_", "a"); // Eliminar los signos '$' del resultado
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
        		
        		String nombre="EVENTO-"+nroevento_x+entity.getAfiche().getOriginalFilename().substring(entity.getAfiche().getOriginalFilename().lastIndexOf('.'));
				entity.setImagen(nombre);
				
                // Guardar en Google Drive
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoEvento, entity.getAfiche(), nombre);
                entity.setImageneventoDriveId(idArchivoLogoDrive);
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


	@Override
	public EventoEntity update(Integer id, EventoDTO eventoDTO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
  
}
*/