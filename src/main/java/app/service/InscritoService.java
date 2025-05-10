package app.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import app.entity.InscritoEntity;



public interface InscritoService extends GenericServiceNormal<InscritoEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<Map<String, Object>> findAll(int estado,String search,int length,int start,int idanio)throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado,int idanio) throws Exception;
	public InscritoEntity getInscritoByCi(String search,int  idevento)throws Exception;
	public InscritoEntity getInscritoByIdEventoByIdPart(Integer idevent,Integer idpart) throws Exception;
	
//	public Integer getTotCursoPorCategoria(int idcategoria)throws Exception;
//	public InscritoEntity customPlantillaCurso(Integer id, InscritoEntity entidad) throws Exception;
}
