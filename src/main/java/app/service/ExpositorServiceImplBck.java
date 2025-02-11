package app.service;
/*

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.ExpositorEntity;
import app.entity.InstitucionEntity;
import app.entity.PersonaEntity;
import app.repository.ExpositorRepository;
import app.repository.GenericRepositoryNormal;
import app.repository.GradoAcademicoRepository;
import app.repository.InstitucionRepository;
import app.repository.ProfesionRepository;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class ExpositorServiceImplBck extends GenericServiceImplNormal<ExpositorEntity, Integer> implements ExpositorService {

	@Autowired private ExpositorRepository ExpositorRepository;
	@Autowired private InstitucionRepository institucionRepository;
	
	@Autowired private ProfesionRepository profesionRepository;
	@Autowired private GradoAcademicoRepository gradoAcademicoRepository;
	@Autowired private PersonaService personaService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private ArchivoService archivoService;
	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;
//	private final String imagePath = "./src/main/resources/static/qrcodes/QRCode.png";
//	private  String imagePath = "./src/main/resources/static/qrcodes/";
	
	@Value("${server.port}")
    private static String puertoservidor;
	

	

    private String IPPUBLICA = ""; // Configura tu IP pública manualmente
    private String PORT= ""; // ejemplo de puerto
    
    
	ExpositorServiceImplBck(GenericRepositoryNormal<ExpositorEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = ExpositorRepository.getIdPrimaryKey();
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
        	int total = ExpositorRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	@Transactional
	public List<ExpositorEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<ExpositorEntity> entities = ExpositorRepository.findAll(estado, search, length, start);
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
        	ExpositorRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = ExpositorRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


    @Override
    @Transactional
    public ExpositorEntity save(ExpositorEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());
        	
        	System.out.println("IMAGEN:"+entity.getLogo().getOriginalFilename());
        	
        	//ADD PERSONA
        	
        	PersonaEntity persona=new PersonaEntity();
        	persona.setId(personaService.getIdPrimaryKey());
        	persona.setCi(entity.getPersona().getCi());
        	persona.setExp(entity.getPersona().getExp());
        	persona.setNombres(entity.getPersona().getNombres());
        	persona.setApellidos(entity.getPersona().getApellidos());
        	persona.setEmail(entity.getPersona().getEmail());
        	persona.setCelular(entity.getPersona().getCelular());
        	persona.setDireccion(entity.getPersona().getDireccion());
        	persona.setEstado(1);
        	PersonaEntity persona2=personaService.save(persona);
        	

        	entity.setId(ExpositorRepository.getIdPrimaryKey());
        	entity.setCodigo(ExpositorRepository.getCodigo());
        	

        	
//        	entity.setGradoacademico(gradoAcademicoRepository.findById(1).get());
//        	entity.setProfesion(profesionRepository.findById(1).get());
        	entity.setPersona(persona2);
        	
        	
        	if (!entity.getLogo().isEmpty()) {
        		String nombre=entity.getPersona().getCi()+entity.getLogo().getOriginalFilename().substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));
//    			
//				String nombreLogo=archivoService.guargar(entity.getLogo(),nombre);
//				entity.setImagen(nombreLogo);
				entity.setImagen(nombre);
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoExpositor, entity.getLogo(), nombre);
                entity.setImagenDriveId(idArchivoLogoDrive);
			}
        	
        	System.out.println("EntityPost:"+entity.toString());
            entity = ExpositorRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

	@Override
	@Transactional
	public ExpositorEntity update(Integer id, ExpositorEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			ExpositorEntity entitymod=ExpositorRepository.findById(id).get();
			
			entitymod.getPersona().setCi(entidad.getPersona().getCi());
			entitymod.getPersona().setExp(entidad.getPersona().getExp());
			entitymod.getPersona().setNombres(entidad.getPersona().getNombres());
			entitymod.getPersona().setApellidos(entidad.getPersona().getApellidos());
			entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
			entitymod.getPersona().setEmail(entidad.getPersona().getEmail());
			entitymod.getPersona().setDireccion(entidad.getPersona().getDireccion());
			entitymod.setGradoacademico(entidad.getGradoacademico());
			entitymod.setProfesion(entidad.getProfesion());
			entitymod.setRubro(entidad.getRubro());
			entitymod.setDedicacion(entidad.getDedicacion());
		
			if (!entidad.getLogo().isEmpty()) {
//        		this.archivoService.eliminarArchivoExpositorLogo(entitymod.getImagen());
	            // Eliminar el logo existente en Google Drive
	            this.archivoService.eliminarArchivoDrive(Constantes.nameFolderLogoExpositor, entitymod.getImagen());
        		String nombre=entitymod.getPersona().getCi()+entidad.getLogo().getOriginalFilename().substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
    			
//				String ruta_logo=archivoService.guargarExpositorLogo(entidad.getLogo(),nombre);
//				entitymod.setImagen(ruta_logo);
				entitymod.setImagen(nombre);
	            String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoExpositor, entidad.getLogo(), nombre);
	            entitymod.setImagenDriveId(idArchivoLogoDrive); // Asumimos que hay un campo `logoDriveId` para almacenar el ID de Drive
			}

			
//			entitymod=genericRepository.save(entidad);
			entitymod=genericRepository.save(entitymod);
			return entitymod;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}



}*/
