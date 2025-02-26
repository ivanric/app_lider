package app.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entity.CertificadoEntity;
import app.entity.InstitucionEntity;
import app.entity.PersonaEntity;
import app.repository.CertificadoCursoRepository;
import app.repository.PersonaRepository;
import app.repository.GenericRepositoryNormal;
import app.repository.InstitucionRepository;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class CertificadoCursoServiceImpl extends GenericServiceImplNormal<CertificadoEntity, Integer> implements CertificadoCursoService {

	@Autowired private PersonaRepository PersonaRepository;
	@Autowired private CertificadoCursoRepository CertificadoCursoRepository;
	@Autowired private InstitucionRepository InstitucionRepository;
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
	public List<Map<String, Object>> findAll_listar_cursos( int estado,String search,int idevento,int idanio,int idparticipante,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.findAll_listar_curso(estado, search,idevento,idanio,idparticipante, length, start);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	public Integer getTotAll_cursos(int estado,String search,int idevento,int idcategoria,int idanio) throws Exception {
		
        try{
        	int total = CertificadoCursoRepository.getTotAll_cursos(estado,search, idevento,idcategoria,idanio );
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0; 
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
	public List<Map<String, Object>> getIdCertiByCurso( int estado,String search,int idevento,int idcategoria,int idanio,int idcurso) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getIdCertiByCurso(estado, search,idevento,idcategoria,idanio,idcurso);
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
	 private S3Service s3Service;
	@Override
	public CertificadoEntity renovarQR(CertificadoEntity entidad) throws Exception {
	    try {
	        System.out.println("Modificar Entity QR*****************:" + entidad.toString());

	        // Buscar y actualizar los datos del socio
	        CertificadoEntity entitymod = CertificadoCursoRepository.findById(entidad.getId())
	            .orElseThrow(() -> new Exception("CertificadoEntity no encontrado"));


	        // Generar el contenido del QR
	        String codigoDocumento = entitymod.getNrodocumento();
	        String  nrofolio_x = entitymod.getNrofolio();
	        System.out.println("************************** NUMERO DE DOCUMENTO UPDATE QR:" + codigoDocumento);

	        InstitucionEntity institucionEntity = InstitucionRepository.findById(1)
	            .orElseThrow(() -> new Exception("Institución no encontrada"));
	        
	        System.out.println("*************INSTITUCION:" + institucionEntity.toString());
	        
	        String bodyQR = institucionEntity.getHost() + "/certificado/" + codigoDocumento;
	        System.out.println("****************CONTENIDO QR:" + bodyQR);

	        // Intentar eliminar el QR anterior en Google Drive
	        try {
	            System.out.println("************* ELIMINANDO QR de nube: " + entitymod.getLinkqr());

                s3Service.deleteFile(Constantes.nameFolderQrIncritoCertificado + "/" + entitymod.getLinkqr());
	        } catch (Exception e) {
	            System.out.println("No se pudo eliminar el QR anterior : " + e.getMessage());
	        }


	        qrCodeGeneratorService.generateQRCode(
            		Constantes.nameFolderQrIncritoCertificado,
            		bodyQR,
            		"QRCERT - "+nrofolio_x
            );
	        
	        
	        
	        entitymod.setLinkqr("QRCERT - "+nrofolio_x+ ".png");
	        entitymod = genericRepository.save(entitymod);

	        return entitymod;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
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
	public Integer getTotAll_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso) throws Exception {
		
        try{
        	int total = CertificadoCursoRepository.getTotAll_curso(estado,search, idevento,idcategoria,idanio,idcurso );
          return total;
      } catch (Exception e){
      		System.out.println(e.getMessage());
//          throw new Exception(e.getMessage());
          return 0; 
      }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> getCategoryByIdAnio(int idevento,int idanio) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getCategoryByIdAnio(idevento,idanio);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
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
	@Transactional
	public List<Map<String, Object>> getEventByIdAnio(int idanio) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getEventoByIdAnio(idanio);
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

	@Override
	@Transactional
	public List<Map<String, Object>> findAll_m_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,int length,int start ) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.findAll_m_curso(estado, search,idevento,idcategoria,idanio,idcurso, length, start);
            return entities;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
	}
	@Override
	@Transactional
	public List<Map<String, Object>> findAll_m(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start ) throws Exception {
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

}
