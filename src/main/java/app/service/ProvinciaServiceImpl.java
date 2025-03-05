package app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.ProvinciaEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.ProvinciaRepository;

@Service
public class ProvinciaServiceImpl extends GenericServiceImplNormal<ProvinciaEntity, Integer> implements ProvinciaService {

	@Autowired private ProvinciaRepository ProvinciaRepository;
	
	
	@Value("${server.port}")
    private static String puertoservidor;
	
	ProvinciaServiceImpl(GenericRepositoryNormal<ProvinciaEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = ProvinciaRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return 0;
      }
	}

	

	@Override
	@Transactional
	public List<ProvinciaEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
            List<ProvinciaEntity> entities = ProvinciaRepository.findAll(estado, search, length, start);
            return entities;
        } catch (Exception e){
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	

	@Override 
	@Transactional
	public void updateStatus(int status, int id) throws Exception {

        try{
        	System.out.println("estado:"+status+" id:"+id);
        	ProvinciaRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = ProvinciaRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}

	
    @Override
    @Transactional
    public ProvinciaEntity save(ProvinciaEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());

        	entity.setId(ProvinciaRepository.getIdPrimaryKey());
        	System.out.println("EntityPost:"+entity.toString());
            entity = ProvinciaRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    
    
	@Override
	@Transactional
	public ProvinciaEntity update(Integer id, ProvinciaEntity entity) throws Exception {
		try {
			System.out.println("Modificar Entity Service:"+entity.toString());
			//observado
			ProvinciaEntity entity2=ProvinciaRepository.findById(id).get();
			entity2.setNombre(entity.getNombre());
			entity2.setDepartamento(entity.getDepartamento());
			System.out.println("CATALOGO BD:"+entity2.toString());
			 entity=genericRepository.save(entity2);
			return entity;
		} catch (Exception e) { 
			e.printStackTrace(); 
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<ProvinciaEntity> findAll(int iddep) throws Exception {
        try{
            List<ProvinciaEntity> entities = ProvinciaRepository.findAll(iddep);
            return entities;
        } catch (Exception e){
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}



}
