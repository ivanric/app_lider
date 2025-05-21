package app.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.ParticipanteEntity;
import app.entity.PersonaEntity;


@Repository 
public interface ParticipanteRepository extends GenericRepositoryNormal<ParticipanteEntity, Integer>{
	 
	
	@Query(value="select COALESCE(max(id),0)+1 as id from participante",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as codigo from participante",nativeQuery = true)
	public Integer getCodigo();
	
	@Query(value = "select t.* from participante t,persona p where t.fk_persona=p.id and  (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,p.ci,p.nombres,p.apellidos,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM participante t where t.fk_persona=p.id and  (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,p.ci,p.nombres,p.apellidos,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<ParticipanteEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	
	@Modifying 
	@Query(value="UPDATE participante SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(Integer status,Integer id); 
	

	@Query(value="select count(t.*) from participante t,persona p where t.fk_persona=p.id and (upper(concat(t.id,p.ci,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);

	@Query(value="select DISTINCT pt.* \r\n"
			+ "from participante pt,persona p\r\n"
			+ "where pt.fk_persona=p.id and (upper(concat(p.ci,'')) = concat('',upper(:search),'')) and pt.estado=1",nativeQuery = true)
	public ParticipanteEntity getParticipanteByCi(@Param("search") String search);
	@Query(value="select DISTINCT part.* \r\n"
			+ "from participante part\r\n"
			+ "where part.fk_persona=:idper and part.estado=1",nativeQuery = true)
	public ParticipanteEntity getParticipanteByIdPer(@Param("idper") Integer idper);
} 