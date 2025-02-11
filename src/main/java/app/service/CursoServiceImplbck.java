package app.service;

/*
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.CursoEntity;
import app.repository.CursoRepository;
import app.repository.GenericRepositoryNormal;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class CursoServiceImplbck extends GenericServiceImplNormal<CursoEntity, Integer> implements CursoService {

	@Autowired private CursoRepository CursoRepository;

	@Autowired private ArchivoService archivoService;
	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;
//	private final String imagePath = "./src/main/resources/static/qrcodes/QRCode.png";
//	private  String imagePath = "./src/main/resources/static/qrcodes/";
	
	@Value("${server.port}")
    private static String puertoservidor;
	

	

    private String IPPUBLICA = ""; // Configura tu IP pública manualmente
    private String PORT= ""; // ejemplo de puerto
    
    
	CursoServiceImplbck(GenericRepositoryNormal<CursoEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = CursoRepository.getIdPrimaryKey();
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
        	int total = CursoRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	

	@Override
	@Transactional
	public List<CursoEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<CursoEntity> entities = CursoRepository.findAll(estado, search, length, start);
            return entities;
        } catch (Exception e){
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	@Transactional
	public List<CursoEntity> findAll( int estado,String search,int length,int start ,int idcat,int idanio) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<CursoEntity> entities = CursoRepository.findAll(estado, search, length, start,idcat,idanio);
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
        	CursoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = CursoRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	
	
	@Override
	public Integer getTotAll(String search,int estado,int idcat,int idanio) throws Exception {
		
        try{
        	int total = CursoRepository.getTotAll(search, estado,idcat,idanio);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


    @Override
    @Transactional
    public CursoEntity save(CursoEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());
        	//ADD PERSONA
        	entity.setId(CursoRepository.getIdPrimaryKey());
        	entity.setCodigo(CursoRepository.getCodigo());

			if (!entity.getCertificado_imagen().isEmpty()) {
				String nombre="CURSO-"+entity.getNrodocumento()+entity.getCertificado_imagen().getOriginalFilename().substring(entity.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);
//				String nombrePlantilla=archivoService.guargarCursoPlantilla(entity.getCertificado_imagen(),nombre);
//				entity.setImagencertificado(nombrePlantilla);
				entity.setImagencertificado(nombre);
				
                // Guardar en Google Drive
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoCurso, entity.getCertificado_imagen(), nombre);
                entity.setImagencertificadoDriveId(idArchivoLogoDrive);
			}
        	
        	entity.setEstado(1);
        	System.out.println("EntityPost:"+entity.toString());
            entity = CursoRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    

	@Override
	@Transactional
	public CursoEntity update(Integer id, CursoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			CursoEntity entitymod=CursoRepository.findById(id).get();
			entitymod.setCodigo(entidad.getCodigo());
			entitymod.setNrodocumento(entidad.getNrodocumento());
			entitymod.setCategoria(entidad.getCategoria());
			entitymod.setNombrecurso(entidad.getNombrecurso());
			entitymod.setNivel(entidad.getNivel());

			if (!entidad.getCertificado_imagen().isEmpty()) {
				if (entitymod.getImagencertificado()!=null) {
//					this.archivoService.eliminarArchivoCursoPlantilla(entitymod.getImagencertificado());
					System.out.println("entitymod.getImagen():"+entitymod.getImagencertificado());
					 this.archivoService.eliminarArchivoDrive(Constantes.nameFolderLogoCurso, entitymod.getImagencertificado());
				}
				
				String nombre="CURSO-"+entitymod.getNrodocumento()+entidad.getCertificado_imagen().getOriginalFilename().substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);
//				String nombrePlantilla=archivoService.guargarCursoPlantilla(entidad.getCertificado_imagen(),nombre);
//				entitymod.setImagencertificado(nombrePlantilla);
				entitymod.setImagencertificado(nombre);
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoCurso, entidad.getCertificado_imagen(), nombre);
                entitymod.setImagencertificadoDriveId(idArchivoLogoDrive);
			}
			
			
			System.out.println("SE COMPLETO LA MODIFICACION");
			CursoEntity cursoEntity3=genericRepository.save(entitymod); 
			System.out.println("ENTIDAD MODIFICADA:"+cursoEntity3);
			return cursoEntity3;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public Integer getTotCursoPorCategoria(int idubicacion) throws Exception {
		
        try{
        	int total = CursoRepository.getTotCursoPorCategoria(idubicacion);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	
	@Override
	@Transactional
	public CursoEntity customPlantillaCurso(Integer id, CursoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar plantilla Entity :"+entidad.toString());
			//observado

			CursoEntity entitymod=CursoRepository.findById(id).get();
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getCertificado_imagen().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getCertificado_imagen().isEmpty()) {
				if (entitymod.getImagencertificado()!=null) {
//					this.archivoService.eliminarArchivoCursoPlantilla(entitymod.getImagencertificado());
					System.out.println("entitymod.getImagen():"+entitymod.getImagencertificado());
					this.archivoService.eliminarArchivoDrive(Constantes.nameFolderLogoCurso, entitymod.getImagencertificado());
				}
				String nombre="CURSO-"+entitymod.getNrodocumento()+entidad.getCertificado_imagen().getOriginalFilename().substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);
    			     		

//				String nombrePlantilla=archivoService.guargarCursoPlantilla(entidad.getCertificado_imagen(),nombre);
//				entitymod.setImagencertificado(nombrePlantilla);
    			
				entitymod.setImagencertificado(nombre);
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoCurso, entidad.getCertificado_imagen(), nombre);
                entitymod.setImagencertificadoDriveId(idArchivoLogoDrive);
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
