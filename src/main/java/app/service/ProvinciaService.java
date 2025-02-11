package app.service;

import java.util.List;

import app.entity.ProvinciaEntity;



public interface ProvinciaService extends GenericServiceNormal<ProvinciaEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public List<ProvinciaEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
	public List<ProvinciaEntity> findAll(int iddep)  throws Exception;;
}
