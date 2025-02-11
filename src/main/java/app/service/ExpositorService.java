package app.service;

import java.util.List;

import app.entity.ExpositorEntity;



public interface ExpositorService extends GenericServiceNormal<ExpositorEntity,Integer>{
	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<ExpositorEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
}
