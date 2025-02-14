package app.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.InscritoEntity;
import app.entity.ParticipanteEntity;
import app.entity.PersonaEntity;


@Repository 
public interface InscritoRepository extends GenericRepositoryNormal<InscritoEntity, Integer>{
	 
	
	@Query(value="select COALESCE(max(id),0)+1 as id from inscrito",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as codigo from inscrito",nativeQuery = true)
	public Integer getCodigo();
	
	@Query(value = "SELECT DISTINCT pt.id,p.ci,concat(p.nombres,' ',p.apellidos) as nombrecompleto,p.email,p.celular,\r\n"
			+ "(select count(*) FROM certificadocurso cert WHERE cert.fk_participante=pt.id) as cantidad_cursos\r\n"
			+ "from participante pt,persona p,inscrito ins\r\n"
			+ "WHERE \r\n"
			+ "pt.fk_persona=p.id\r\n"
			+ "and  (upper(concat(p.ci,'')) like concat('%',upper(:search),'%')) \r\n"
			+ "and pt.id=ins.fk_participante\r\n"
			+ "and (ins.estado=:estado or :estado=-1)\r\n"
			+ "and pt.id IN (SELECT cert2.fk_participante from certificadocurso cert2,anio an where cert2.fk_participante=pt.id and an.id=cert2.fk_anio and (an.id=:idanio or :idanio=-1))\r\n"
			+ "ORDER BY pt.id ASC\r\n"
			+ "LIMIT :length OFFSET :start",
			countQuery ="",		
			nativeQuery = true)
	public List<Map<String, Object>> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start,@Param("idanio") int idanio);
	
	@Modifying 
	@Query(value="UPDATE inscrito SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(Integer status,Integer id); 
	

	@Query(value="select count(DISTINCT pt.id) \r\n"
			+ "from inscrito ins,participante pt,persona p \r\n"
			+ "where \r\n"
			+ "pt.fk_persona=p.id\r\n"
			+ "and  (upper(concat(p.ci,'')) like concat('%',upper(:search),'%')) \r\n"
			+ "and pt.id=ins.fk_participante\r\n"
			+ "and (ins.estado=:estado or :estado=-1)\r\n"
			+ "and pt.id IN (SELECT cert2.fk_participante from certificadocurso cert2,anio an where cert2.fk_participante=pt.id and an.id=cert2.fk_anio and (an.id=:idanio or :idanio=-1)) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado,@Param("idanio") int idanio);
	
	@Query(value="select DISTINCT i.*\r\n"
			+ "from participante pt,persona p,inscrito i,inscritodetalle idt\r\n"
			+ "where \r\n"
			+ "i.fk_participante=pt.id \r\n"
			+ "and i.id=idt.fk_inscrito\r\n"
			+ "and pt.fk_persona=p.id \r\n"
			+ "and (upper(concat(p.ci,'')) = concat('',upper(:search),'')) \r\n"
			+ "and (idt.fk_evento=:idevento or :idevento=-1) and i.estado=1",nativeQuery = true)
	public InscritoEntity getInscritoByCi(@Param("search") String search,@Param("idevento") Integer idevento);

} 