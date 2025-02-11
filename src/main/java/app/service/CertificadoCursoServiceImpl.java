package app.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.CertificadoEntity;
import app.entity.PersonaEntity;
import app.repository.CertificadoCursoRepository;
import app.repository.PersonaRepository;
import app.repository.GenericRepositoryNormal;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class CertificadoCursoServiceImpl extends GenericServiceImplNormal<CertificadoEntity, Integer> implements CertificadoCursoService {

	@Autowired private PersonaRepository PersonaRepository;
	@Autowired private CertificadoCursoRepository CertificadoCursoRepository;

//	@Autowired private ArchivoService archivoService;
	@Autowired QRCodeGeneratorService qrCodeGeneratorService;

    
	CertificadoCursoServiceImpl(GenericRepositoryNormal<CertificadoEntity, Integer> genericRepository) {
		super(genericRepository);
		// TODO Auto-generated constructor stub
	}

	//sin el @Override porque hereda de
	public int getIdPrimaryKey() throws Exception {
		
        try{
        	int id = CertificadoCursoRepository.getIdPrimaryKey();
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
        	int total = CertificadoCursoRepository.getCodigo();
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	@Override
	@Transactional
	public List<CertificadoEntity> findAll( int estado,String search,int idevento,int idanio,int idparticipante,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<CertificadoEntity> entities = CertificadoCursoRepository.findAll(estado, search,idevento,idanio,idparticipante, length, start);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	@Transactional
	public List<CertificadoEntity> findAll( int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<CertificadoEntity> entities = CertificadoCursoRepository.findAll(estado, search,idevento,idcategoria,idanio,idparticipante, length, start);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> findAll_m( int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.findAll_m(estado, search,idevento,idcategoria,idanio,idparticipante, length, start);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> getIdCertiByPart( int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getIdCertiByPart(estado, search,idevento,idcategoria,idanio,idparticipante);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
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
        	CertificadoCursoRepository.updateStatus(status,id);

        } catch (Exception e){
        	System.out.println(e.getMessage());
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
	}
	
	@Override
	public Integer getTotAll(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante) throws Exception {
		
        try{
        	int total = CertificadoCursoRepository.getTotAll(estado,search, idevento,idcategoria,idanio,idparticipante );
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0; 
      }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> getCategoryById(int idevento,int idanio, int idpart) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getCategoryById(idevento,idanio,idpart);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> getEventoById(int idanio, int idpart) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getEventoById(idanio,idpart);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	public Map<String, Object>  findByNrodocumento(String  codigo) throws Exception {
		Map<String, Object>  entity= new HashMap<>();
        try{
        	entity= CertificadoCursoRepository.findPorNrodocumento( codigo);
          return entity;
      } catch (Exception e){
      		System.out.println(e.getMessage());
          return null;  
      }
	}
/*

    @Override
    @Transactional
    public CertificadoEntity save(CertificadoEntity entity) throws Exception {
        try{
        	System.out.println("EntitySAVE_Servicio:"+entity.toString());
        	
        	System.out.println("IMAGEN:"+entity.getLogo().getOriginalFilename());
        	
        	//ADD PERSONA
        	entity.getPersona().setId(PersonaRepository.getIdPrimaryKey());
        	entity.getPersona().setEstado(1);
        	PersonaEntity persona2=PersonaRepository.save(entity.getPersona());
        	
        	entity.setId(CertificadoCursoRepository.getIdPrimaryKey());
        	entity.setCodigo(CertificadoCursoRepository.getCodigo());
        	entity.setEstado(1);
        	entity.setPersona(persona2);
        	
        	
        	if (!entity.getLogo().isEmpty()) {
        		String nombre="logo-"+this.CertificadoCursoRepository.getCodigo()+entity.getLogo().getOriginalFilename().substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));
    			
//				String nombreLogo=archivoService.guargarLogoParticipante(entity.getLogo(),nombre);
//				entity.setImagen(nombreLogo);
				entity.setImagen(nombre);

                // Guardar en Google Drive
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoParticipante, entity.getLogo(), nombre);
                entity.setImagenDriveId(idArchivoLogoDrive);
			}
        	
        	System.out.println("EntityPost:"+entity.toString());
            entity = CertificadoCursoRepository.save(entity);
            return entity;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    

	@Override
	@Transactional
	public CertificadoEntity update(Integer id, CertificadoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar Entity:"+entidad.toString());
			//observado

			CertificadoEntity entitymod=CertificadoCursoRepository.findById(id).get();
//			entitymod.setCodigo(entidad.getCodigo());
			entitymod.getPersona().setCi(entidad.getPersona().getCi());
			entitymod.getPersona().setExp(entidad.getPersona().getExp());
			entitymod.getPersona().setNombrecompleto(entidad.getPersona().getNombrecompleto());
			entitymod.getPersona().setGenero(entidad.getPersona().getGenero());
			entitymod.getPersona().setFechanacimiento(entidad.getPersona().getFechanacimiento());
			entitymod.getPersona().setEdad(entidad.getPersona().getEdad());
			entitymod.setGradoacademico(entidad.getGradoacademico());
			entitymod.setProfesion(entidad.getProfesion());
			entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
			entitymod.getPersona().setEmail(entidad.getPersona().getEmail());
			entitymod.setDepartamento(entidad.getDepartamento());
			entitymod.setLocalidad(entidad.getLocalidad());
			entitymod.getPersona().setDireccion(entidad.getPersona().getDireccion());
			

			System.out.println("VALOR AFICHE:"+entidad.getLogo()!=null);
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getLogo().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getLogo().isEmpty()) {
//				this.archivoService.eliminarArchivoParticipanteLogo(entitymod.getImagen());
	            // Eliminar el logo existente en Google Drive
	            this.archivoService.eliminarArchivoDrive(Constantes.nameFolderLogoParticipante, entitymod.getImagen());
				
				String nombre="mod-"+entitymod.getCodigo()+entidad.getLogo().getOriginalFilename().substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombre logo modificar:"+nombre);
//    			System.out.println("entitymod.getImagen():"+entitymod.getImagen());
        
//				String nombreLogo=archivoService.guargarLogoParticipante(entidad.getLogo(),nombre);
//				entitymod.setImagen(nombreLogo);
				entitymod.setImagen(nombre);
                // Guardar en Google Drive
                String idArchivoLogoDrive = archivoService.guargarArchivoDrive(Constantes.nameFolderLogoParticipante, entitymod.getLogo(), nombre);
                entitymod.setImagenDriveId(idArchivoLogoDrive);
			}

			System.out.println("SE COMPLETO LA MODIFICACION");
//			entitymod=genericRepository.save(entidad);
			CertificadoEntity cursoEntity3=genericRepository.save(entitymod); 
			System.out.println("ENTIDAD MODIFICADA:"+cursoEntity3);
			return cursoEntity3;
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	*/
	
	
	
	
	/*
	@Override
	public Integer getTotCursoPorCategoria(int idubicacion) throws Exception {
		
        try{
        	int total = CertificadoCursoRepository.getTotCursoPorCategoria(idubicacion);
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0;
      }
	}
	
	@Override
	@Transactional
	public CertificadoEntity customPlantillaCurso(Integer id, CertificadoEntity entidad) throws Exception {
		try {
			System.out.println("Modificar plantilla Entity :"+entidad.toString());
			//observado

			CertificadoEntity entitymod=CertificadoCursoRepository.findById(id).get();
			
			System.out.println("Serv Modificar afiche LLEGO:"+entidad.getCertificado_imagen().getOriginalFilename());
//			if (entidad.getAfiche()!=null) {
			if (!entidad.getCertificado_imagen().isEmpty()) {
				this.archivoService.eliminarArchivoCursoPlantilla(entitymod.getImagencertificado());
				String nombre="ptl-"+entitymod.getNrodocumento()+entidad.getCertificado_imagen().getOriginalFilename().substring(entidad.getCertificado_imagen().getOriginalFilename().lastIndexOf('.'));
    			System.out.println("Nombrea afiche a guardar:"+nombre);
    			System.out.println("entitymod.getImagen():"+entitymod.getImagencertificado());     		

				String nombrePlantilla=archivoService.guargarCursoPlantilla(entidad.getCertificado_imagen(),nombre);
				entitymod.setImagencertificado(nombrePlantilla);
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
    */ 

}
