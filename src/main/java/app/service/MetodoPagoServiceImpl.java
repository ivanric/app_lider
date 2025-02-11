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

import app.entity.MetodoPagoEntity;
import app.entity.InstitucionEntity;
import app.entity.PersonaEntity;
import app.repository.MetodoPagoRepository;
import app.repository.GenericRepositoryNormal;
import app.repository.GradoAcademicoRepository;
import app.repository.InstitucionRepository;
import app.repository.ProfesionRepository;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class MetodoPagoServiceImpl extends GenericServiceImplNormal<MetodoPagoEntity, Integer> implements MetodoPagoService {

	@Autowired private MetodoPagoRepository MetodoPagoRepository;
	@Autowired private InstitucionRepository institucionRepository;
	
	@Autowired private ProfesionRepository profesionRepository;
	@Autowired private GradoAcademicoRepository gradoAcademicoRepository;
	@Autowired private PersonaService personaService;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
//	@Autowired private ArchivoService archivoService;
	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;
    @Autowired private S3Service s3Service;
    
	MetodoPagoServiceImpl(GenericRepositoryNormal<MetodoPagoEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	
	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = MetodoPagoRepository.getIdPrimaryKey();
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
        	int total = MetodoPagoRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	@Transactional
	public List<MetodoPagoEntity> findAll( int estado,String search,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<MetodoPagoEntity> entities = MetodoPagoRepository.findAll(estado, search, length, start);
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
        	MetodoPagoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(String search,int estado) throws Exception {
		
        try{
        	int total = MetodoPagoRepository.getTotAll(search, estado);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}


    @Override
    @Transactional
    public MetodoPagoEntity save(MetodoPagoEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());
        	
        	System.out.println("IMAGEN:"+entity.getLogo().getOriginalFilename());
        	entity.setId(MetodoPagoRepository.getIdPrimaryKey());
        	entity.setCodigo(MetodoPagoRepository.getCodigo());
        	
        	
            if (entity.getLogo() != null && !entity.getLogo().isEmpty()) {
                // Generar un nombre único para el archivo
                String nombre = entity.getCodigo() + entity.getLogo().getOriginalFilename()
                        .substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));

                // Subir el archivo de logo a S3
                String fileKey = s3Service.uploadFileToS3(
                        Constantes.nameFolderLogoMetodoPago, 
                        entity.getLogo(), 
                        nombre
                );

                // Guardar la clave del archivo en S3
                entity.setImagenDriveId(fileKey);
                entity.setImagen(nombre);
            }

        	
        	System.out.println("EntityPost:"+entity.toString());
            entity = MetodoPagoRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

	@Override
	@Transactional
	public MetodoPagoEntity update(Integer id, MetodoPagoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			// Buscar la entidad existente en la base de datos
	        MetodoPagoEntity entitymod = MetodoPagoRepository.findById(id)
	                .orElseThrow(() -> new Exception("Método de Pago no encontrado con ID: " + id));

	        // Manejo del logo si se envió uno nuevo
	        if (entidad.getLogo() != null && !entidad.getLogo().isEmpty()) {
	            // Eliminar el logo existente en S3 si aplica
	            if (entitymod.getImagen() != null) {
	                System.out.println("Eliminando logo anterior de S3: " + entitymod.getImagen());
	                s3Service.deleteFile(Constantes.nameFolderLogoMetodoPago + "/" + entitymod.getImagen());
	            }

	            // Generar el nuevo nombre para el logo
	            String nombreLogo = entitymod.getCodigo() 
	                    + entidad.getLogo().getOriginalFilename()
	                    .substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
	            System.out.println("Nombre del logo a guardar: " + nombreLogo);

	            // Subir el nuevo logo a S3
	            String fileKey = s3Service.uploadFileToS3(Constantes.nameFolderLogoMetodoPago, entidad.getLogo(), nombreLogo);

	            // Actualizar la entidad con los datos del nuevo logo
	            entitymod.setImagen(nombreLogo);
	            entitymod.setImagenDriveId(fileKey); // Guardar la clave del archivo en S3
	        }
	     // Actualizar los demás campos del método de pago
			entitymod.setTitular(entidad.getTitular());
			entitymod.setBanco(entidad.getBanco());
			entitymod.setNrocuenta(entidad.getNrocuenta());

			
		    // Guardar la entidad actualizada
			entitymod=genericRepository.save(entitymod);
			return entitymod;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}



}
