package app.service;

import java.util.List;

import app.entity.ProfesionEntity;



public interface ProfesionService extends GenericServiceNormal<ProfesionEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public List<ProfesionEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
}
