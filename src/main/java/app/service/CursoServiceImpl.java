package app.service;


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
public class CursoServiceImpl extends GenericServiceImplNormal<CursoEntity, Integer> implements CursoService {
	@Autowired private S3Service s3Service;
	@Autowired private CursoRepository CursoRepository;
//	@Autowired private ArchivoService archivoService;
	@Autowired
	QRCodeGeneratorService qrCodeGeneratorService;

	CursoServiceImpl(GenericRepositoryNormal<CursoEntity, Integer> genericRepository) {
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
				String nombre="CURSO-"+entity.getNrodocumento()+entity.getCertificado_imagen().getOriginalFilename()
						.substring(entity.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);

				entity.setImagencertificado(nombre);
				
                
				  // Subir archivo de imagen a Amazon S3
			    String fileKey = s3Service.uploadFileToS3(
			        Constantes.nameFolderLogoCurso,
			        entity.getCertificado_imagen(),
			        nombre
			    );
			    entity.setImagencertificadoDriveId(fileKey); // Guardar la clave de S3
			}
			if (!entity.getCertificado_imagen_sf().isEmpty()) {
				String nombre="CURSOSF-"+entity.getNrodocumento()+entity.getCertificado_imagen_sf().getOriginalFilename()
						.substring(entity.getCertificado_imagen_sf().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);

				entity.setImagencertificadosf(nombre);
				
                
				  // Subir archivo de imagen a Amazon S3
			    String fileKey = s3Service.uploadFileToS3(
			        Constantes.nameFolderLogoCursoSF,
			        entity.getCertificado_imagen_sf(),
			        nombre
			    );
			    entity.setImagencertificadoDriveIdsf(fileKey); // Guardar la clave de S3
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
            System.out.println("Modificar Entity:" + entidad.toString());

            // Obtener la entidad a modificar
            CursoEntity entitymod = CursoRepository.findById(id).get();
            
            // Modificar los campos de la entidad
            entitymod.setCodigo(entidad.getCodigo());
            entitymod.setNrodocumento(entidad.getNrodocumento());
            entitymod.setCategoria(entidad.getCategoria());
            entitymod.setNombrecurso(entidad.getNombrecurso());
            entitymod.setNivel(entidad.getNivel());
            entitymod.setDedicatoriacertificado(entidad.getDedicatoriacertificado());
            entitymod.setGlosacertificado(entidad.getGlosacertificado());
            // Verificar si el archivo de certificado no está vacío
            if (!entidad.getCertificado_imagen().isEmpty()) {
                if (entitymod.getImagencertificado() != null) {
                    // Si ya existe una imagen, eliminarla de S3
                    System.out.println("Eliminando archivo anterior: " + entitymod.getImagencertificado());
                    s3Service.deleteFile(Constantes.nameFolderLogoCurso + "/" + entitymod.getImagencertificado());
                }

                // Generar el nombre del archivo
                String nombre = "CURSO-" + entitymod.getNrodocumento() + entidad.getCertificado_imagen().getOriginalFilename()
                        .substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
                System.out.println("Nombre a guardar del certificado: " + nombre);

                // Establecer el nombre del archivo en la entidad
                entitymod.setImagencertificado(nombre);

                // Subir el archivo a Amazon S3
                String fileKey = s3Service.uploadFileToS3(
                    Constantes.nameFolderLogoCurso,  // Carpeta en S3 donde se almacenará el archivo
                    entidad.getCertificado_imagen(),  // El archivo de imagen a subir
                    nombre  // El nombre del archivo en S3
                );

                // Guardar la clave del archivo en S3 en el campo 'imagencertificadoDriveId'
                entitymod.setImagencertificadoDriveId(fileKey);  // Aquí guardamos la clave de S3
            }
            if (!entidad.getCertificado_imagen_sf().isEmpty()) {
                if (entitymod.getImagencertificadosf() != null) {
                    // Si ya existe una imagen, eliminarla de S3
                    System.out.println("Eliminando archivo anterior: " + entitymod.getImagencertificadosf());
                    s3Service.deleteFile(Constantes.nameFolderLogoCursoSF + "/" + entitymod.getImagencertificadosf());
                }

                // Generar el nombre del archivo
                String nombre = "CURSOSF-" + entitymod.getNrodocumento() + entidad.getCertificado_imagen_sf().getOriginalFilename()
                        .substring(entidad.getCertificado_imagen_sf().getOriginalFilename().lastIndexOf('.'));
                System.out.println("Nombre a guardar del certificado: " + nombre);

                // Establecer el nombre del archivo en la entidad
                entitymod.setImagencertificadosf(nombre);

                // Subir el archivo a Amazon S3
                String fileKey = s3Service.uploadFileToS3(
                    Constantes.nameFolderLogoCursoSF,  // Carpeta en S3 donde se almacenará el archivo
                    entidad.getCertificado_imagen_sf(),  // El archivo de imagen a subir
                    nombre  // El nombre del archivo en S3
                );

                // Guardar la clave del archivo en S3 en el campo 'imagencertificadoDriveId'
                entitymod.setImagencertificadoDriveIdsf(fileKey);  // Aquí guardamos la clave de S3
            }
            // Guardar la entidad modificada
            System.out.println("SE COMPLETÓ LA MODIFICACIÓN");
            CursoEntity cursoEntity3 = genericRepository.save(entitymod);
            System.out.println("ENTIDAD MODIFICADA:" + cursoEntity3);
            
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
	        System.out.println("Modificar plantilla Entity :" + entidad.toString());
	        
	        // Buscar la entidad del curso por ID
	        CursoEntity entitymod = CursoRepository.findById(id).get();
	        
	        System.out.println("Serv Modificar afiche LLEGO:" + entidad.getCertificado_imagen().getOriginalFilename());

	        // Verificar si la imagen no está vacía
	        if (!entidad.getCertificado_imagen().isEmpty()) {
	            // Si ya existe una imagen asociada en el curso, eliminarla de S3
	            if (entitymod.getImagencertificado() != null) {
	                System.out.println("entitymod.getImagen():" + entitymod.getImagencertificado());
	                // Eliminar el archivo anterior de S3
	                s3Service.deleteFile(Constantes.nameFolderLogoCurso + "/" + entitymod.getImagencertificado());
	            }
	            
	            // Crear el nombre para la nueva imagen
	            String nombre = "CURSO-" + entitymod.getNrodocumento() + entidad.getCertificado_imagen().getOriginalFilename()
	                    .substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
	            System.out.println("Nombre afiche a guardar:" + nombre);
	            
	            // Establecer el nombre de la imagen en la entidad
	            entitymod.setImagencertificado(nombre);
	            
	            // Subir el archivo a S3
	            String fileKey = s3Service.uploadFileToS3(Constantes.nameFolderLogoCurso, entidad.getCertificado_imagen(), nombre);
	            
	            // Guardar la clave de S3 en la entidad
	            entitymod.setImagencertificadoDriveId(fileKey); // Guardamos la clave de S3, si quieres seguir usando este campo.
	        }

	        // Guardar la entidad modificada
	        entitymod = genericRepository.save(entitymod);
	        return entitymod;
	    } catch (Exception e) { 
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        throw new Exception(e.getMessage());
	    }
	}

    

}
