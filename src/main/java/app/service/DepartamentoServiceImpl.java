package app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.DepartamentoEntity;
import app.entity.ProvinciaEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.DepartamentoRepository;

@Service
public class DepartamentoServiceImpl extends GenericServiceImplNormal<DepartamentoEntity, Integer> implements DepartamentoService {

	@Autowired private DepartamentoRepository DepartamentoRepository;
	
	
	@Value("${server.port}")
    private static String puertoservidor;
	
	DepartamentoServiceImpl(GenericRepositoryNormal<DepartamentoEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = DepartamentoRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return 0;
      }
	}

	

	@Override
	@Transactional
	public List<DepartamentoEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
            List<DepartamentoEntity> entities = DepartamentoRepository.findAll(estado, search, length, start);
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
        	DepartamentoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = DepartamentoRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}

	
    @Override
    @Transactional
    public DepartamentoEntity save(DepartamentoEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());

        	entity.setId(DepartamentoRepository.getIdPrimaryKey());
        	System.out.println("EntityPost:"+entity.toString());
            entity = DepartamentoRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    
    
	@Override
	@Transactional
	public DepartamentoEntity update(Integer id, DepartamentoEntity entity) throws Exception {
		try {
			System.out.println("Modificar Entity Service:"+entity.toString());
			//observado
			DepartamentoEntity catalogoEntity2=DepartamentoRepository.findById(id).get();
			System.out.println("CATALOGO BD:"+catalogoEntity2.toString());
        	
			 entity=genericRepository.save(entity);
			return entity;
		} catch (Exception e) { 
			e.printStackTrace(); 
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}




}
