package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.entity.InstitucionEntity;
import app.entity.UsuarioEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.InstitucionRepository;
import app.repository.ProvinciaRepository;

@Service
public class InstitucionServiceImpl extends GenericServiceImplNormal<InstitucionEntity, Integer> implements InstitucionService {

	@Autowired
	private InstitucionRepository institucionRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	
	InstitucionServiceImpl(GenericRepositoryNormal<InstitucionEntity, Integer> genericRepository) {
		super(genericRepository);
	}

	@Override
	@Transactional
	public List<InstitucionEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<InstitucionEntity> entities = institucionRepository.findAll(estado, search, length, start);
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
        	institucionRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}

	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = institucionRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
    @Override
    @Transactional
    public InstitucionEntity save(InstitucionEntity entity) throws Exception {
        try{
        	System.out.println("EntitySave:"+entity.toString());
        	int id=institucionRepository.getIdPrimaryKey();
        	System.out.println("id:"+id);
        	entity.setId(id);
        	entity.setProvincia(provinciaRepository.findById(1).get());
        	System.out.println("EntityPost:"+entity.toString());
            entity = institucionRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = institucionRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	@Transactional
	public InstitucionEntity update(Integer id, InstitucionEntity entidad) throws Exception {
		try {
			System.out.println("EntidadModificar:"+entidad.toString());
			//observado
//			Optional<InstitucionEntity> entitiOptional=genericRepository.findById(id);//entitiOptional retorna un objeto si encuentra
			
//			InstitucionEntity entidaddb=entitiOptional.get();

//			entidaddb=genericRepository.save(entidaddb);
			entidad.setProvincia(provinciaRepository.findById(1).get());
			entidad=genericRepository.save(entidad);
//			return entidaddb;
			return entidad;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	

	
}
