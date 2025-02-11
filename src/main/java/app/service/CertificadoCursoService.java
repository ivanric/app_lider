package app.service;

import java.util.List;
import java.util.Map;

import app.entity.CertificadoEntity;



public interface CertificadoCursoService extends GenericServiceNormal<CertificadoEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<CertificadoEntity> findAll(int estado,String search,int idevento,int idanio,int idparticipante,int length,int start )throws Exception;
	public List<CertificadoEntity> findAll(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start )throws Exception;
	public List<Map<String, Object>> findAll_m(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante,int length,int start )throws Exception;
	public List<Map<String, Object>> getIdCertiByPart(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante)throws Exception;
	
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(int estado,String search,int idevento,int idcategoria,int idanio,int idparticipante) throws Exception;
	public List<Map<String, Object>> getCategoryById(int idevent,int idanio,int idpart)throws Exception ;
	public List<Map<String, Object>> getEventoById(int idanio,int idpart)throws Exception ;
	public Map<String, Object>  findByNrodocumento(String  codigo) throws Exception;
	//	public Integer getTotCursoPorCategoria(int idcategoria)throws Exception;
//	public CertificadoEntity customPlantillaCurso(Integer id, CertificadoEntity entidad) throws Exception;
}
