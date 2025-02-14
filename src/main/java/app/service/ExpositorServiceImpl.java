package app.service;


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
public class ExpositorServiceImpl extends GenericServiceImplNormal<ExpositorEntity, Integer> implements ExpositorService {

	@Autowired private ExpositorRepository ExpositorRepository;
	@Autowired private InstitucionRepository institucionRepository;
	
	@Autowired private ProfesionRepository profesionRepository;
	@Autowired private GradoAcademicoRepository gradoAcademicoRepository;
	@Autowired private PersonaService personaService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
//	@Autowired private ArchivoService archivoService;
	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;

	
    @Autowired
    private S3Service s3Service;
    
	ExpositorServiceImpl(GenericRepositoryNormal<ExpositorEntity, Integer> genericRepository) {
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
        	PersonaEntity savedPersona=personaService.save(persona);
        	entity.setId(ExpositorRepository.getIdPrimaryKey());
        	entity.setCodigo(ExpositorRepository.getCodigo());
        	entity.setPersona(savedPersona);
        	
        	
        	if (!entity.getLogo().isEmpty()) {
        		String nombre=entity.getCodigo()+"-"+entity.getPersona().getCi()+entity.getLogo().getOriginalFilename()
        				.substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));
				String fileKey = s3Service.uploadFileToS3(
				        Constantes.nameFolderLogoExpositor, 
				        entity.getLogo(), 
				        nombre
				);
				entity.setImagen(nombre);
			    entity.setImagenDriveId(fileKey);

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
		          // Eliminar el logo existente de S3 si aplica
	            if (entitymod.getImagen() != null) {
	                System.out.println("Eliminando logo anterior de S3: " + entitymod.getImagen());
	                s3Service.deleteFile(Constantes.nameFolderLogoExpositor + "/" + entitymod.getImagen());
	            }
	            // Generar el nombre para el nuevo logo
	            String nombreLogo = entitymod.getCodigo()+"-"+entitymod.getPersona().getCi() + entidad.getLogo().getOriginalFilename()
	                .substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
	            System.out.println("Nombre del logo a guardar: " + nombreLogo);
	            
	         // Subir el archivo de logo a S3
	            String fileKey = s3Service.uploadFileToS3(Constantes.nameFolderLogoExpositor, entidad.getLogo(), nombreLogo);

	            // Actualizar la entidad con los datos del logo
	            entitymod.setImagen(nombreLogo);
	            entitymod.setImagenDriveId(fileKey); // Guardamos la clave del archivo en S3
	            
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



}
