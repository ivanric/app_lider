package app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.AnioEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.AnioRepository;
import app.util.QRCodeGeneratorService;

@Service
public class AnioServiceImpl extends GenericServiceImplNormal<AnioEntity, Integer> implements AnioService {

	@Autowired private AnioRepository AnioRepository;
	

	
	AnioServiceImpl(GenericRepositoryNormal<AnioEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = AnioRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return 0;
      }
	}

	

	@Override
	@Transactional
	public List<AnioEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
            List<AnioEntity> entities = AnioRepository.findAll(estado, search, length, start);
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
        	AnioRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = AnioRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}

	
    @Override
    @Transactional
    public AnioEntity save(AnioEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());

        	entity.setId(AnioRepository.getIdPrimaryKey());
        	System.out.println("EntityPost:"+entity.toString());
            entity = AnioRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    
    
	@Override
	@Transactional
	public AnioEntity update(Integer id, AnioEntity entity) throws Exception {
		try {
			System.out.println("Modificar Entity Service:"+entity.toString());
			//observado
			AnioEntity catalogoEntity2=AnioRepository.findById(id).get();
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
