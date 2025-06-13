package app.service;

import java.util.List;

import app.entity.ParticipanteEntity;



public interface ParticipanteService extends GenericServiceNormal<ParticipanteEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<ParticipanteEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
	public List<ParticipanteEntity> getParticipanteByCi(String search)throws Exception;
	
//	public Integer getTotCursoPorCategoria(int idcategoria)throws Exception;
//	public ParticipanteEntity customPlantillaCurso(Integer id, ParticipanteEntity entidad) throws Exception;
}
