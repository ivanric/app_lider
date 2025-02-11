package app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import app.dto.EventoDTO;
import app.entity.CursoEntity;
import app.entity.EventoEntity;
import app.entity.ExpositorEntity;



public interface EventoService extends GenericServiceNormal<EventoEntity,Integer>{

	public int getIdPrimaryKey() throws Exception;
	public Integer getCodigo()throws Exception;
	public List<EventoEntity> findAll(int estado,String search,int length,int start )throws Exception;
	public void updateStatus(int status,int id) throws Exception;
	public Integer getTotAll(String search,int estado) throws Exception;
	public EventoEntity save(EventoEntity entity,List <CursoEntity> curso,List <ExpositorEntity> expositor,
			List<LocalDate> fechacurso,List<String> literalfechacurso,List <LocalTime> horainicio,
			List <LocalTime> horafin,List<String>lugarcurso,List<Integer>horasacademicas) throws Exception;
	
	public EventoEntity update(Integer id, EventoDTO eventoDTO) throws Exception;
	public Integer getTotCursoPorCategoria(int idcategoria)throws Exception;
	public EventoEntity customPlantillaCurso(Integer id, EventoEntity entidad) throws Exception;
	
	EventoEntity findByCodeEncrypt(String codigo)throws Exception;
	
}
