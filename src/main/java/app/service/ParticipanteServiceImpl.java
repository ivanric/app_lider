package app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.ParticipanteEntity;
import app.entity.PersonaEntity;
import app.repository.ParticipanteRepository;
import app.repository.PersonaRepository;
import app.repository.GenericRepositoryNormal;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class ParticipanteServiceImpl extends GenericServiceImplNormal<ParticipanteEntity, Integer> implements ParticipanteService {

	@Autowired private PersonaRepository PersonaRepository;
	@Autowired private ParticipanteRepository ParticipanteRepository;

//	@Autowired private ArchivoService archivoService;
	@Autowired QRCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    private S3Service s3Service;
	ParticipanteServiceImpl(GenericRepositoryNormal<ParticipanteEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = ParticipanteRepository.getIdPrimaryKey();
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
        	int total = ParticipanteRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	@Transactional
	public List<ParticipanteEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<ParticipanteEntity> entities = ParticipanteRepository.findAll(estado, search, length, start);
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
        	ParticipanteEntity partm=this.ParticipanteRepository.getById(id);
        	PersonaRepository.updateStatus(1,partm.getPersona().getId());
//        	this.PersonaRepository.updateStatus(1,partm.getPersona().getId());)
        	ParticipanteRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = ParticipanteRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


    @Override
    @Transactional
    public ParticipanteEntity save(ParticipanteEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());
        	
        	System.out.println("IMAGEN:"+entity.getLogo().getOriginalFilename());
        	
        	//ADD PERSONA
        	entity.getPersona().setId(PersonaRepository.getIdPrimaryKey());
        	entity.getPersona().setEstado(1);
        	PersonaEntity persona2=PersonaRepository.save(entity.getPersona());
        	
        	entity.setId(ParticipanteRepository.getIdPrimaryKey());
        	entity.setCodigo(ParticipanteRepository.getCodigo());
        	entity.setEstado(1);
        	entity.setPersona(persona2);
        	
          	if (!entity.getLogo().isEmpty()) {
        	
        	       String nombre = "PTE-"+entity.getPersona().getCi() + entity.getLogo().getOriginalFilename()
       	                .substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));
        		String fileKey = s3Service.uploadFileToS3(
				        Constantes.nameFolderLogoParticipante, 
				        entity.getLogo(), 
				        nombre
				);
				entity.setImagen(nombre);
			    entity.setImagenDriveId(fileKey);
			}
    
        	
        	System.out.println("EntityPost:"+entity.toString());
            entity = ParticipanteRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    

	@Override
	@Transactional
	public ParticipanteEntity update(Integer id, ParticipanteEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			ParticipanteEntity entitymod=ParticipanteRepository.findById(id).get();
//			entitymod.setCodigo(entidad.getCodigo());
			entitymod.getPersona().setCi(entidad.getPersona().getCi());
			entitymod.getPersona().setExp(entidad.getPersona().getExp());
			entitymod.getPersona().setNombres(entidad.getPersona().getNombres());
			entitymod.getPersona().setApellidos(entidad.getPersona().getApellidos());
			entitymod.getPersona().setGenero(entidad.getPersona().getGenero());
			entitymod.getPersona().setFechanacimiento(entidad.getPersona().getFechanacimiento());
			entitymod.getPersona().setEdad(entidad.getPersona().getEdad());
			entitymod.setGradoacademico(entidad.getGradoacademico());
			entitymod.setProfesion(entidad.getProfesion());
			entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
			entitymod.getPersona().setEmail(entidad.getPersona().getEmail());
			entitymod.setDepartamento(entidad.getDepartamento());
			entitymod.setProvincia(entidad.getProvincia());
			entitymod.setLocalidad(entidad.getLocalidad());
			entitymod.getPersona().setDireccion(entidad.getPersona().getDireccion());
			

			System.out.println("VALOR AFICHE:"+entidad.getLogo()!=null);
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getLogo().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getLogo().isEmpty()) {
//						          // Eliminar el logo existente de S3 si aplica
	            if (entitymod.getImagen() != null) {
	                System.out.println("Eliminando logo anterior de S3: " + entitymod.getImagen());
	                s3Service.deleteFile(Constantes.nameFolderLogoParticipante + "/" + entitymod.getImagen());
	            }
                
	            // Generar el nombre para el nuevo logo
	            String nombreLogo = "PTE-"+entitymod.getPersona().getCi() + entidad.getLogo().getOriginalFilename()
	                .substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
	            System.out.println("Nombre del logo a guardar: " + nombreLogo);
	            
	         // Subir el archivo de logo a S3
	            String fileKey = s3Service.uploadFileToS3(Constantes.nameFolderLogoParticipante, entidad.getLogo(), nombreLogo);

	            // Actualizar la entidad con los datos del logo
	            entitymod.setImagen(nombreLogo);
	            entitymod.setImagenDriveId(fileKey); // Guardamos la clave del archivo en S3
			}else {
			    if (entitymod.getImagen() != null) {
	                System.out.println("*******************Eliminando222222 logo anterior de S3: " + entitymod.getImagen());
	                s3Service.deleteFile(Constantes.nameFolderLogoParticipante + "/" + entitymod.getImagen());
	            }
			    entitymod.setImagen(null);
			    entitymod.setImagenDriveId(null);
			}

			System.out.println("SE COMPLETO LA MODIFICACION");
//			entitymod=genericRepository.save(entidad);
			ParticipanteEntity cursoEntity3=genericRepository.save(entitymod); 
			System.out.println("ENTIDAD MODIFICADA:"+cursoEntity3);
			return cursoEntity3;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	@Override
	public ParticipanteEntity getParticipanteByCi(String  search) throws Exception {
		ParticipanteEntity entity= new ParticipanteEntity();
        try{
        	entity= ParticipanteRepository.getParticipanteByCi(search);
          return entity;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return null;  
      }
	}


}
