package app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.CategoriaEntity;
import app.repository.GenericRepositoryNormal;
import app.repository.CategoriaRepository;
import app.util.QRCodeGeneratorService;

@Service
public class CategoriaServiceImpl extends GenericServiceImplNormal<CategoriaEntity, Integer> implements CategoriaService {

	@Autowired private CategoriaRepository CategoriaRepository;
	
//	@Autowired private ArchivoService archivoService;
	

	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;

	
	@Value("${server.port}")
    private static String puertoservidor;
	
	CategoriaServiceImpl(GenericRepositoryNormal<CategoriaEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = CategoriaRepository.getIdPrimaryKey();
          return id;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return 0;
      }
	}

	

	@Override
	@Transactional
	public List<CategoriaEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
            List<CategoriaEntity> entities = CategoriaRepository.findAll(estado, search, length, start);
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
        	CategoriaRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = CategoriaRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}

	
    @Override
    @Transactional
    public CategoriaEntity save(CategoriaEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());

        	entity.setId(CategoriaRepository.getIdPrimaryKey());
        	System.out.println("EntityPost:"+entity.toString());
            entity = CategoriaRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    
    
	@Override
	@Transactional
	public CategoriaEntity update(Integer id, CategoriaEntity entity) throws Exception {
		try {
			System.out.println("Modificar Entity Service:"+entity.toString());
			//observado
			CategoriaEntity catalogoEntity2=CategoriaRepository.findById(id).get();
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
