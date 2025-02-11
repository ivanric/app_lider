package app.service;

import java.util.List;

import app.entity.MetodoPagoEntity;



public interface MetodoPagoService extends GenericServiceNormal<MetodoPagoEntity,Integer>{
	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<MetodoPagoEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
}
