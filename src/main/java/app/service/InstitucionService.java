package app.service;

import java.util.List;

import app.entity.InstitucionEntity;





public interface InstitucionService extends GenericServiceNormal<InstitucionEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public List<InstitucionEntity> findAll(int estado,String search,int length,int start)throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
}
