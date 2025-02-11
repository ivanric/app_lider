package app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.ProfesionEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.ProfesionRepository;
import app.util.QRCodeGeneratorService;

@Service
public class ProfesionServiceImpl extends GenericServiceImplNormal<ProfesionEntity, Integer> implements ProfesionService {

	@Autowired private ProfesionRepository ProfesionRepository;
	
	
	@Value("${server.port}")
    private static String puertoservidor;
	
	ProfesionServiceImpl(GenericRepositoryNormal<ProfesionEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = ProfesionRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return 0;
      }
	}

	

	@Override
	@Transactional
	public List<ProfesionEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
            List<ProfesionEntity> entities = ProfesionRepository.findAll(estado, search, length, start);
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
        	ProfesionRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = ProfesionRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}

	
    @Override
    @Transactional
    public ProfesionEntity save(ProfesionEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());

        	entity.setId(ProfesionRepository.getIdPrimaryKey());
        	System.out.println("EntityPost:"+entity.toString());
            entity = ProfesionRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    
    
	@Override
	@Transactional
	public ProfesionEntity update(Integer id, ProfesionEntity entity) throws Exception {
		try {
			System.out.println("Modificar Entity Service:"+entity.toString());
			//observado
			ProfesionEntity catalogoEntity2=ProfesionRepository.findById(id).get();
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
