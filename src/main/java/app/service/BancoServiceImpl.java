package app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.BancoEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.BancoRepository;
import app.util.QRCodeGeneratorService;

@Service
public class BancoServiceImpl extends GenericServiceImplNormal<BancoEntity, Integer> implements BancoService {

	@Autowired private BancoRepository BancoRepository;
	
	
	@Value("${server.port}")
    private static String puertoservidor;
	
	BancoServiceImpl(GenericRepositoryNormal<BancoEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = BancoRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return 0;
      }
	}

	

	@Override
	@Transactional
	public List<BancoEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
            List<BancoEntity> entities = BancoRepository.findAll(estado, search, length, start);
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
        	BancoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = BancoRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}

	
    @Override
    @Transactional
    public BancoEntity save(BancoEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());

        	entity.setId(BancoRepository.getIdPrimaryKey());
        	System.out.println("EntityPost:"+entity.toString());
            entity = BancoRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    
    
	@Override
	@Transactional
	public BancoEntity update(Integer id, BancoEntity entity) throws Exception {
		try {
			System.out.println("Modificar Entity Service:"+entity.toString());
			//observado
			BancoEntity catalogoEntity2=BancoRepository.findById(id).get();
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
