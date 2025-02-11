package app.service;

import java.util.List;

import app.entity.CursoEntity;



public interface CursoService extends GenericServiceNormal<CursoEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<CursoEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public List<CursoEntity> findAll(int estado,String search,int length,int start,int idcat,int idanio )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
	public Integer getTotAll(String search,int estado,int idcat,int idanio) throws Exception;
	public Integer getTotCursoPorCategoria(int idcategoria)throws Exception;
	public CursoEntity customPlantillaCurso(Integer id, CursoEntity entidad) throws Exception;
}
