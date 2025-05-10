package app.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dto.CertificadoDTO;
import app.entity.AnioEntity;
import app.entity.CertificadoEntity;
import app.entity.InscritoDetalleEntity;
import app.entity.InscritoEntity;
import app.entity.InstitucionEntity;
import app.entity.ParticipanteEntity;
import app.entity.PersonaEntity;
import app.repository.AnioRepository;
import app.repository.CertificadoCursoRepository;
import app.repository.PersonaRepository;
import app.repository.GenericRepositoryNormal;
import app.repository.InscritoDetalleRepository;
import app.repository.InscritoRepository;
import app.repository.InstitucionRepository;
import app.repository.ParticipanteRepository;
import app.util.Constantes;
import app.util.QRCodeGeneratorService;

@Service
public class CertificadoCursoServiceImpl extends GenericServiceImplNormal<CertificadoEntity, Integer> implements CertificadoCursoService {

	@Autowired private PersonaRepository PersonaRepository;
	@Autowired private ParticipanteRepository ParticipanteRepository;
	@Autowired private InscritoRepository InscritoRepository;
	@Autowired private InscritoDetalleRepository InscritoDetalleRepository;
	@Autowired private CertificadoCursoRepository CertificadoCursoRepository;
	@Autowired private InstitucionRepository InstitucionRepository;
	@Autowired private AnioRepository anioRepository;;
//	@Autowired private ArchivoService archivoService;
	@Autowired QRCodeGeneratorService qrCodeGeneratorService;
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private MailService mailService;
	@Autowired private S3Service s3Service; 
    
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
	public List<Map<String, Object>> getIdCertiByCurso( int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,String tipocertificado) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.getIdCertiByCurso(estado, search,idevento,idcategoria,idanio,idcurso,tipocertificado);
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
	public Integer getTotAll_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,String tipocertificado) throws Exception {
		
        try{
        	int total = CertificadoCursoRepository.getTotAll_curso(estado,search, idevento,idcategoria,idanio,idcurso,tipocertificado);
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
	
   @Transactional
    public InscritoEntity guardarcertificadoinscrito(CertificadoDTO DTO) throws Exception {
        try{
        	System.out.println("DTO_S:"+DTO.toString());
        	System.out.println("DT_DEPARTAMENTO_S:"+DTO.getDepartamento());
        	System.out.println("DT_PROVINCIA_S:"+DTO.getProvincia());
    		
        	LocalDate ahora = LocalDate.now();
//    		int dia = ahora.getDayOfMonth();
//    		int mes = ahora.getMonthValue();
    		long año = ahora.getYear();
    		System.out.println("ANIOOOOOO ANT:"+año);
    		String nombre1=String.valueOf(año);
    		AnioEntity anio=anioRepository.findByNombre(nombre1);
        	
        	
        	PersonaEntity persona2=null;
        	ParticipanteEntity participanteEntity2=null;
        	if (DTO.getIdper()==null) {
    			PersonaEntity personaEntity=new PersonaEntity();
    			personaEntity.setId(PersonaRepository.getIdPrimaryKey());
    			personaEntity.setCi(DTO.getCi());
    			personaEntity.setExp(DTO.getExp());
    			personaEntity.setNombres(DTO.getNombres());
    			personaEntity.setApellidos(DTO.getApellidos());
    			personaEntity.setGenero(DTO.getGenero());
    			personaEntity.setFechanacimiento(DTO.getFechanacimiento());
    			personaEntity.setEdad(DTO.getEdad());
    			personaEntity.setCelular(DTO.getCelular());
    			personaEntity.setEmail(DTO.getEmail());
    			personaEntity.setDireccion(DTO.getDireccion());
    			personaEntity.setEstado(1);
    			persona2=PersonaRepository.save(personaEntity);
			} else {
				PersonaEntity personabd=PersonaRepository.findById(DTO.getIdper()).get();
				personabd.setCi(DTO.getCi());
				personabd.setExp(DTO.getExp());
				personabd.setNombres(DTO.getNombres());
				personabd.setApellidos(DTO.getApellidos());
				personabd.setGenero(DTO.getGenero());
				personabd.setFechanacimiento(DTO.getFechanacimiento());
				personabd.setEdad(DTO.getEdad());
				personabd.setCelular(DTO.getCelular());
				personabd.setEmail(DTO.getEmail());
				personabd.setDireccion(DTO.getDireccion());
				persona2=PersonaRepository.save(personabd);
			}
        	
        	if (DTO.getIdpart()==null) {
   			 // Crear la entidad de Participante
    			System.out.println("CREANDO NUEVO PARTICIPANTE");
    			System.out.println("dTO DEPARTAMENTO:"+DTO.getDepartamento());
        		ParticipanteEntity ParticipanteEntity=new ParticipanteEntity();
//	    			InscritoEntity.setImagen(null)
    			ParticipanteEntity.setId(ParticipanteRepository.getIdPrimaryKey());
    			ParticipanteEntity.setCodigo(ParticipanteRepository.getCodigo());
    			ParticipanteEntity.setLogo(DTO.getArchivoimgparticipante());
    			ParticipanteEntity.setGradoacademico(DTO.getGradoacademico());
    			ParticipanteEntity.setProfesion(DTO.getProfesion());
    			ParticipanteEntity.setDepartamento(DTO.getDepartamento());
    			if (DTO.getProvincia()!=null) {
    				ParticipanteEntity.setProvincia(DTO.getProvincia());
    			}
    			ParticipanteEntity.setProvincia(DTO.getProvincia());
    			ParticipanteEntity.setLocalidad(DTO.getLocalidad());
    			ParticipanteEntity.setPersona(persona2);
    			ParticipanteEntity.setEstado(1);
    			
    			// Subir imagen del participante a Amazon S3
    			if (!DTO.getArchivoimgparticipante().isEmpty()) {
    	    		String nombre = "PTE-" + DTO.getCi() + DTO.getArchivoimgparticipante().getOriginalFilename()
    	    				.substring(DTO.getArchivoimgparticipante().getOriginalFilename().lastIndexOf('.'));
    	    		ParticipanteEntity.setImagen(nombre);

    	    		// Subir archivo a Amazon S3
    	    		String fileKey = s3Service.uploadFileToS3(
    	    				Constantes.nameFolderLogoParticipante,
    	    				DTO.getArchivoimgparticipante(), 
    	    				nombre
    	    		);
    	    		ParticipanteEntity.setImagenDriveId(fileKey);  // Guardar la clave S3 en lugar del ID de Google Drive
            	}
    			
    			
    			participanteEntity2=this.ParticipanteRepository.save(ParticipanteEntity);
    			System.out.println("fin CREANDO NUEVO PARTICIPANTE");
			} else {
				//ya es participante de algun curso
				System.out.println("PARTICIPANTE ENCONTRADO");
				ParticipanteEntity participantemod=this.ParticipanteRepository.findById(DTO.getIdpart()).get();
				participantemod.setGradoacademico(DTO.getGradoacademico());
				participantemod.setProfesion(DTO.getProfesion());
				participantemod.setDepartamento(DTO.getDepartamento());
				participantemod.setProvincia(DTO.getProvincia());
				participantemod.setLocalidad(DTO.getLocalidad());
				participantemod.setPersona(persona2);
				
				//actualizar la foto del participante
				System.out.println("Serv Modificar afiche LLEGO:"+DTO.getArchivoimgparticipante().getOriginalFilename());
//					if (entidad.getAfiche()!=null) {
				if (!DTO.getArchivoimgparticipante().isEmpty()) {
//								          // Eliminar el logo existente de S3 si aplica
		            if (participantemod.getImagen() != null) {
		                System.out.println("Eliminando logo anterior de S3: " + participantemod.getImagen());
		                s3Service.deleteFile(Constantes.nameFolderLogoParticipante + "/" + participantemod.getImagen());
		            }
	                
		            // Generar el nombre para el nuevo logo
		            String nombreLogo = "PTE-"+participantemod.getPersona().getCi() + DTO.getArchivoimgparticipante().getOriginalFilename()
		                .substring(DTO.getArchivoimgparticipante().getOriginalFilename().lastIndexOf('.'));
		            System.out.println("Nombre del logo a guardar: " + nombreLogo);
		            
		         // Subir el archivo de logo a S3
		            String fileKey = s3Service.uploadFileToS3(Constantes.nameFolderLogoParticipante, DTO.getArchivoimgparticipante(), nombreLogo);

		            // Actualizar la entidad con los datos del logo
		            participantemod.setImagen(nombreLogo);
		            participantemod.setImagenDriveId(fileKey); // Guardamos la clave del archivo en S3
				}else {
				    if (participantemod.getImagen() != null) {
		                System.out.println("*******************Eliminando222222 logo anterior de S3: " + participantemod.getImagen());
		                s3Service.deleteFile(Constantes.nameFolderLogoParticipante + "/" + participantemod.getImagen());
		            }
				    participantemod.setImagen(null);
		            participantemod.setImagenDriveId(null);
				}
				participanteEntity2=this.ParticipanteRepository.save(participantemod);
			}
			
        	
        	InscritoEntity inscritobd=DTO.getInscrito();
        	
        	inscritobd.setParticipante(participanteEntity2);
        	
        	System.out.println("inscritobd:"+DTO.getInscrito());
            if (!DTO.getArchivoimgpago().isEmpty()) {
                //eliminamos la imagen de pago anterior
	            if (inscritobd.getImagen() != null) {
	                System.out.println("Eliminando pago anterior de S3: " + inscritobd.getImagen());
	                s3Service.deleteFile(Constantes.nameFolderLogoPagoInscrito + "/" + inscritobd.getImagen());
	            }
            	String nombrePago = "PAGO-"+inscritobd.getCodigo()+"-" + DTO.getCi()
                    + DTO.getArchivoimgpago().getOriginalFilename()
                      .substring(DTO.getArchivoimgpago().getOriginalFilename().lastIndexOf('.'));
                
                // Subir archivo de pago a Amazon S3
                String fileKeyPago = s3Service.uploadFileToS3(Constantes.nameFolderLogoPagoInscrito,DTO.getArchivoimgpago(), nombrePago
                );
                inscritobd.setImagen(nombrePago);
                inscritobd.setImagenDriveId(fileKeyPago); // Guardar la clave S3
            }
        	

            
            
//            for (int i = 0; i < DTO.getEvento().getEventodetalle().size(); i++) {
//				
//			}
        	
        	
        	/*
			List<InscritoDetalleEntity> array_detalleIns=new ArrayList<>();
			
			InscritoDetalleEntity InscritoDetalleEntity=new InscritoDetalleEntity();
			InscritoDetalleEntity.setId(this.InscritoDetalleRepository.getIdPrimaryKey());
			InscritoDetalleEntity.setCodigo(this.InscritoDetalleRepository.getCodigo());
			InscritoDetalleEntity.setCantidad(DTO.getCantidad());
			InscritoDetalleEntity.setPrecio(DTO.getPrecio());
			InscritoDetalleEntity.setDescuento(DTO.getDescuento());
			InscritoDetalleEntity.setSubtotal(DTO.getSubtotal());
			InscritoDetalleEntity.setEstado(1);
			InscritoDetalleEntity.setEvento(DTO.getEvento());
			
			InscritoDetalleEntity InscritoDetalleEntity2=this.InscritoDetalleRepository.save(InscritoDetalleEntity);
			array_detalleIns.add(InscritoDetalleEntity2);
			
			InscritoEntity InscritoEntity= new InscritoEntity();
			InscritoEntity.setId(this.InscritoRepository.getIdPrimaryKey());
			int codigox_inscrito=this.InscritoRepository.getCodigo();
			InscritoEntity.setCodigo(codigox_inscrito);
        	//deposito
			InscritoEntity.setNrocuenta(DTO.getNrocuenta());
			InscritoEntity.setBanco(DTO.getBanco());
			InscritoEntity.setImporte(DTO.getImporte());
			
			//totales
			double xtotcantidad=0,xtotdescuento=0,xtotsubtotal=0;
			
			if (DTO.getCantidad()!=0) {
				xtotcantidad=DTO.getCantidad();
			}
			if (DTO.getDescuento()!=0) {
				xtotdescuento=DTO.getDescuento();
			}
			if (DTO.getSubtotal()!=0) {
				xtotsubtotal=DTO.getSubtotal();
			}
			InscritoEntity.setCantidad(xtotcantidad);
			InscritoEntity.setDescuento(xtotdescuento);
			InscritoEntity.setSubtotal(xtotsubtotal);
			InscritoEntity.setTotal(xtotsubtotal-xtotdescuento);
        	InscritoEntity.setEstado(1);
        	InscritoEntity.setAnio(anio);
        	InscritoEntity.setParticipante(participanteEntity2);
        	InscritoEntity.setDetalleInscrito(array_detalleIns);
        	
        	// Subir imagen del pago a Amazon S3
            if (!DTO.getArchivoimgpago().isEmpty()) {
                String nombrePago = "PAGO-"+codigox_inscrito+"-" + DTO.getCi()
                    + DTO.getArchivoimgpago().getOriginalFilename()
                      .substring(DTO.getArchivoimgpago().getOriginalFilename().lastIndexOf('.'));
                
                InscritoEntity.setImagen(nombrePago);

                // Subir archivo de pago a Amazon S3
                String fileKeyPago = s3Service.uploadFileToS3(
                    Constantes.nameFolderLogoPagoInscrito,
                    DTO.getArchivoimgpago(),
                    nombrePago
                );
                InscritoEntity.setImagenDriveId(fileKeyPago); // Guardar la clave S3
            }
         	
         	
         	//agregando certificados
         	int tam= DTO.getEvento().getEventodetalle().size();
         	for (int i = 0; i < tam; i++) {
         		CertificadoEntity CertificadoEntity=new CertificadoEntity();
         		CertificadoEntity.setId(this.CertificadoCursoRepository.getIdPrimaryKey());
         		CertificadoEntity.setCodigo(this.CertificadoCursoRepository.getCodigo());
         		 // Generar y guardar QR
         		String nrofolio_x=DTO.getEvento().getEventodetalle().get(i).getCurso().getNrodocumento()+"-"+codigox_inscrito+"-"+participanteEntity2.getCodigo();
         		System.out.println("nrofolio_x "+ i + ":"+nrofolio_x);
         		
         		
         	// Generar código QR
                String codigoDocumento = passwordEncoder.encode(nrofolio_x + "");
                codigoDocumento = codigoDocumento.replace("/", "c").replace(".", "a").replace("$", "d");
                
	            CertificadoEntity.setNrodocumento(codigoDocumento);
	            CertificadoEntity.setLinkqr("QRCERT - "+nrofolio_x+ ".png");
	            
	            InstitucionEntity institucionEntity = InstitucionRepository.findById(1).get();
	            String bodyQR = institucionEntity.getHost() + "/certificado/" + codigoDocumento;
	            qrCodeGeneratorService.generateQRCode(
	            		Constantes.nameFolderQrIncritoCertificado,
	            		bodyQR,
	            		"QRCERT - "+nrofolio_x
	            );
			
	            CertificadoEntity.setNrofolio(nrofolio_x);
	            CertificadoEntity.setParticipante(participanteEntity2);
	            CertificadoEntity.setCurso(DTO.getEvento().getEventodetalle().get(i).getCurso());
	            CertificadoEntity.setAnio(anio);
	            CertificadoEntity.setEstado(1);
	            CertificadoEntity.setEvento(DTO.getEvento());
	            CertificadoEntity.setLugarcurso(DTO.getEvento().getEventodetalle().get(i).getLugarcurso());
	            CertificadoEntity.setHorasacademicas(DTO.getEvento().getEventodetalle().get(i).getHorasacademicas());
	            CertificadoEntity.setExpositor(DTO.getEvento().getEventodetalle().get(i).getExpositor());
	            CertificadoEntity.setTipocertificado(DTO.getTipocertificado());
	            CertificadoEntity CertificadoEntity2=this.CertificadoCursoRepository.save(CertificadoEntity);            
         	}
	
        	System.out.println("EntityInscritoPost:"+InscritoEntity.toString());

            //aqui enviamos el mensaje
            
            String send_detalle_email="";
            if (DTO.getEvento().getDetalleemail()!=null) {
            	send_detalle_email=DTO.getEvento().getDetalleemail();
			}else {
				send_detalle_email=Constantes.DETALLE_EMAIL;
			}
            Map<String, Object> variables = Map.of(
                    "nombre", DTO.getNombres()+" "+DTO.getApellidos(),
                    "detalle", send_detalle_email,
                    "evento", DTO.getEvento().getDetalle(),
                    "fecha", DTO.getEvento().getFechainicial(),
                    "grupo", DTO.getEvento().getLinkgrupo(),
                    "pagina", Constantes.NOMBRE_PAGINA_EMAIL
                );
             // Enviar correo
                mailService.sendEmailWithTemplate(
                    DTO.getEmail(),
                    "Registro de Evento",
                    "email-inscrito.html",
                    variables,
                    "no-reply@" + "academialider".toLowerCase() + ".com"
                );
                
           	// Guardar InscritoEntity final
            InscritoEntity InscritoEntity2 = InscritoRepository.save(InscritoEntity);
            */
            InscritoEntity InscritoEntity2 = InscritoRepository.save(inscritobd);
            System.out.println("inscritomod:"+InscritoEntity2.toString());
            
            System.out.println("*******EVENTO:"+DTO.getEvento().toString());
            System.out.println("*******INSCRITO:"+DTO.getInscrito().toString());
            
            
            List<CertificadoEntity> certificados=this.CertificadoCursoRepository.getCertificados(DTO.getEvento().getId(), DTO.getIdpartact());
            for (int i = 0; i < certificados.size(); i++) {
				CertificadoEntity certificado = certificados.get(i);
				System.out.println("***********certificad1_:"+certificado.toString());
				certificado.setParticipante(participanteEntity2); 
				certificado.setTipocertificado(DTO.getTipocertificado());
			 	CertificadoEntity cer2= this.CertificadoCursoRepository.save(certificado);
			 	System.out.println("***********certificad2_:"+cer2.toString());
            }    
            return InscritoEntity2;
        } catch (Exception e){
            throw new Exception(e.getMessage());
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
	public List<Map<String, Object>> findAll_m_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,int length,int start,String tipocertificado) throws Exception {
        try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
            List<Map<String, Object>> entities = CertificadoCursoRepository.findAll_m_curso(estado, search,idevento,idcategoria,idanio,idcurso,tipocertificado, length, start);
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

	@Override
	public CertificadoEntity getCertificado(Integer id1, Integer id2, Integer id3) {
	      try{
//            List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
	    	  CertificadoEntity entitie = CertificadoCursoRepository.getCertificado(id1, id2, id3);
            return entitie;
        } catch (Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            return null;
        }
	}

	@Override
	public List<CertificadoEntity> getCertificados(Integer id1, Integer id2) {
		 try{
//           List<PaisEntity> entities = paisRepository.findAll(Sort.by("idpais").ascending());
           List<CertificadoEntity> entities = CertificadoCursoRepository.getCertificados(id1, id2);
           return entities;
       } catch (Exception e){
       	e.printStackTrace();
       	System.out.println(e.getMessage());
       		return null;
       }
	}

}
