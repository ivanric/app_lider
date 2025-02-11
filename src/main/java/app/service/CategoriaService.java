package app.service;

import java.util.List;

import app.entity.CategoriaEntity;



public interface CategoriaService extends GenericServiceNormal<CategoriaEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public List<CategoriaEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
}
