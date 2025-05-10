package app.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import app.entity.CertificadoEntity;



public interface CertificadoCursoService extends GenericServiceNormal<CertificadoEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<CertificadoEntity> findAll(int estado,String search,int idevento,int idanio,int idparticipante,int length,int start )throws Exception;
	public List<CertificadoEntity> findAll(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start )throws Exception;
	public List<Map<String, Object>> findAll_m(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start )throws Exception;
//	public List<Map<String, Object>> findAll_m_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,int length,int start )throws Exception;
	public List<Map<String, Object>> findAll_m_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,int length,int start,String tipocertificado ) throws Exception;
	public List<Map<String, Object>> findAll_listar_cursos(int estado,String search,int idevento,int idcategoria,int idanio,int length,int start )throws Exception;
	public Integer getTotAll_cursos(int estado,String search,int idevento,int idcategoria,int idanio) throws Exception;
	public List<Map<String, Object>> getIdCertiByPart(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante)throws Exception;
	public List<Map<String, Object>> getIdCertiByCurso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,String tipocertificado)throws Exception;
	
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante) throws Exception;
	public Integer getTotAll_curso(int estado,String search,int idevento,int idcategoria,int idanio,int idcurso,String tipocertificado) throws Exception;
	public List<Map<String, Object>> getCategoryById(int idevent,int idanio,int idpart)throws Exception ;
	public List<Map<String, Object>> getEventoById(int idanio,int idpart)throws Exception ;
	public Map<String, Object>  findByNrodocumento(String  codigo) throws Exception;

	//listar evento
	public List<Map<String, Object>> getEventByIdAnio(int idanio)throws Exception ;
	public List<Map<String, Object>> getCategoryByIdAnio(int idevent,int idanio)throws Exception ;
	public CertificadoEntity renovarQR(CertificadoEntity entidad) throws Exception ;
	
	public CertificadoEntity getCertificado(Integer id1,Integer id2, Integer id3);
	public List<CertificadoEntity> getCertificados(Integer id1,Integer id2);

}
