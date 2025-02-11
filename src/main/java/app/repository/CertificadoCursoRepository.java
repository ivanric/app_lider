package app.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.CertificadoEntity;


@Repository 
public interface CertificadoCursoRepository extends GenericRepositoryNormal<CertificadoEntity, Integer>{
	 
	
	@Query(value="select COALESCE(max(id),0)+1 as id from certificadocurso",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as id from certificadocurso",nativeQuery = true)
	public Integer getCodigo();
	
	@Query(value = "select DISTINCT ct.* from certificadocurso ct,curso c,eventodetalle ed,evento e,participante pa,persona p,anio a,inscritodetalle idt "
			+ "where ct.fk_participante=pa.id "
			+ "and pa.fk_persona=p.id "
			+ "and ct.fk_anio=a.id "
			+ "and ct.fk_curso=c.id "
			+ "and c.id=ed.fk_curso "
			+ "and e.id=ed.fk_evento "
			+ "and e.id=idt.fk_evento "
			+ "and e.fk_anio=a.id "
			+ "and  (ct.estado=:estado or :estado=-1) and  (upper(concat(ct.id,p.ci,'')) like concat('%',upper(:search),'%')) "
			+ "and (e.id=:idevento or :idevento=-1) "
			+ "and (ct.fk_anio=:idanio or :idanio=-1) "
			+ "and (ct.fk_participante=:idparticipante or :idparticipante=-1) "
			+ "ORDER BY ct.id ASC LIMIT "
			+ ":length OFFSET :start ",
				
			nativeQuery = true)
	public List<CertificadoEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("idevento") int idevento,@Param("idanio") int idanio,@Param("idparticipante") int idparticipante,@Param("length") int length,@Param("start") int start);
	
	@Query(value = "SELECT DISTINCT ct.* \r\n"
			+ "FROM certificadocurso ct\r\n"
			+ "JOIN participante pa ON ct.fk_participante = pa.id\r\n"
			+ "JOIN persona p ON pa.fk_persona = p.id\r\n"
			+ "JOIN anio a ON ct.fk_anio = a.id\r\n"
			+ "JOIN curso c ON ct.fk_curso = c.id\r\n"
			+ "JOIN categoria cat ON c.fk_categoria = cat.id\r\n"
			+ "JOIN eventodetalle ed ON c.id = ed.fk_curso \r\n"
			+ "JOIN evento e ON ct.fk_evento = e.id \r\n"
			+ "WHERE (ct.estado = :estado OR :estado = -1) \r\n"
			+ "AND (upper(c.id || c.nombrecurso || '') LIKE concat('%', upper(:search), '%'))\r\n"
			+ "AND (e.id = :idevento OR :idevento = -1)\r\n"
			+ "AND (cat.id = :idcategoria OR :idcategoria = -1)\r\n"
			+ "AND (ct.fk_anio = :idanio OR :idanio = -1)\r\n"
			+ "AND (ct.fk_participante = :idparticipante OR :idparticipante = -1)\r\n"
			+ "ORDER BY ct.id ASC\r\n"
			+ "LIMIT :length OFFSET :start",
	    nativeQuery = true)
	public List<CertificadoEntity> findAll(
	        @Param("estado") int estado,
	        @Param("search") String search,
	        @Param("idevento") int idevento,
	        @Param("idcategoria") int idcategoria,
	        @Param("idanio") int idanio,
	        @Param("idparticipante") int idparticipante,
	        @Param("length") int length,
	        @Param("start") int start);
	
	
	@Query(value = "SELECT DISTINCT ct.id,\r\n"
			+ "ct.nrofolio,\r\n"
			+ "(SELECT  edt2.fechacurso FROM evento ev2,eventodetalle edt2 where ev2.id=ct.fk_evento and ev2.id=edt2.fk_evento and edt2.fk_curso=ct.fk_curso ),\r\n"
			+ "--TO_CHAR(ed.fechacurso, 'DD/MM/YYYY') AS fechacurso,\r\n"
			+ "e.detalle, \r\n"
			+ "c.nombrecurso, \r\n"
			+ "cat.nombre as categoria\r\n"
			+ "FROM certificadocurso ct\r\n"
			+ "JOIN participante pa ON ct.fk_participante = pa.id\r\n"
			+ "JOIN persona p ON pa.fk_persona = p.id\r\n"
			+ "JOIN anio a ON ct.fk_anio = a.id\r\n"
			+ "JOIN curso c ON ct.fk_curso = c.id\r\n"
			+ "JOIN categoria cat ON c.fk_categoria = cat.id\r\n"
			+ "JOIN eventodetalle ed ON c.id = ed.fk_curso \r\n"
			+ "JOIN evento e ON ct.fk_evento = e.id \r\n"
			+ "WHERE (ct.estado = :estado OR :estado = -1) \r\n"
			+ "AND (upper(c.id || c.nombrecurso || '') LIKE concat('%', upper(:search), '%'))\r\n"
			+ "AND (e.id = :idevento OR :idevento = -1)\r\n"
			+ "AND (cat.id = :idcategoria OR :idcategoria = -1)\r\n"
			+ "AND (ct.fk_anio = :idanio OR :idanio = -1)\r\n"
			+ "AND (ct.fk_participante = :idparticipante OR :idparticipante = -1)\r\n"
			+ "ORDER BY ct.id ASC\r\n"
			+ "LIMIT :length OFFSET :start",
	    nativeQuery = true)
	public List<Map<String, Object>> findAll_m(
	        @Param("estado") int estado,
	        @Param("search") String search,
	        @Param("idevento") int idevento,
	        @Param("idcategoria") int idcategoria,
	        @Param("idanio") int idanio,
	        @Param("idparticipante") int idparticipante,
	        @Param("length") int length,
	        @Param("start") int start);

	@Query(value = "SELECT DISTINCT ct.id\r\n"
			+ "FROM certificadocurso ct\r\n"
			+ "JOIN participante pa ON ct.fk_participante = pa.id\r\n"
			+ "JOIN persona p ON pa.fk_persona = p.id\r\n"
			+ "JOIN anio a ON ct.fk_anio = a.id\r\n"
			+ "JOIN curso c ON ct.fk_curso = c.id\r\n"
			+ "JOIN categoria cat ON c.fk_categoria = cat.id\r\n"
			+ "JOIN eventodetalle ed ON c.id = ed.fk_curso \r\n"
			+ "JOIN evento e ON ct.fk_evento = e.id \r\n"
			+ "WHERE (ct.estado = :estado OR :estado = -1) \r\n"
			+ "AND (upper(c.id || c.nombrecurso || '') LIKE concat('%', upper(:search), '%'))\r\n"
			+ "AND (e.id = :idevento OR :idevento = -1)\r\n"
			+ "AND (cat.id = :idcategoria OR :idcategoria = -1)\r\n"
			+ "AND (ct.fk_anio = :idanio OR :idanio = -1)\r\n"
			+ "AND (ct.fk_participante = :idparticipante OR :idparticipante = -1)\r\n"
			+ "ORDER BY ct.id ASC\r\n",
	    nativeQuery = true)
	public List<Map<String, Object>> getIdCertiByPart(
	        @Param("estado") int estado,
	        @Param("search") String search,
	        @Param("idevento") int idevento,
	        @Param("idcategoria") int idcategoria,
	        @Param("idanio") int idanio,
	        @Param("idparticipante") int idparticipante);
	
	@Modifying 
	@Query(value="UPDATE certificadocurso SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(Integer status,Integer id); 
	

	@Query(value="SELECT  count ( DISTINCT ct.id)\r\n"
			+ "FROM certificadocurso ct\r\n"
			+ "JOIN participante pa ON ct.fk_participante = pa.id\r\n"
			+ "JOIN persona p ON pa.fk_persona = p.id\r\n"
			+ "JOIN anio a ON ct.fk_anio = a.id\r\n"
			+ "JOIN curso c ON ct.fk_curso = c.id\r\n"
			+ "JOIN categoria cat ON c.fk_categoria = cat.id\r\n"
			+ "JOIN eventodetalle ed ON c.id = ed.fk_curso \r\n"
			+ "JOIN evento e ON ct.fk_evento = e.id \r\n"
			+ "WHERE (ct.estado = :estado OR :estado = -1) \r\n"
			+ "AND (upper(c.id || c.nombrecurso || '') LIKE concat('%', upper(:search), '%'))\r\n"
			+ "AND (e.id = :idevento OR :idevento = -1)\r\n"
			+ "AND (cat.id = :idcategoria OR :idcategoria = -1)\r\n"
			+ "AND (ct.fk_anio = :idanio OR :idanio = -1)\r\n"
			+ "AND (ct.fk_participante = :idparticipante OR :idparticipante = -1)\r\n"
			+ "\r\n"
			+ "--LIMIT :length OFFSET :start "
			+ " ",nativeQuery = true)
	public Integer getTotAll(@Param("estado") Integer estado,@Param("search") String search,@Param("idevento") int idevento,@Param("idcategoria") int idcategoria,@Param("idanio") int idanio,@Param("idparticipante") int idparticipante);

	@Query(value = "SELECT DISTINCT e.id, e.detalle, e.estado "
			+ "FROM certificadocurso ct "
			+ "JOIN participante pa ON ct.fk_participante = pa.id "
			+ "JOIN persona p ON pa.fk_persona = p.id "
			+ "JOIN anio a ON ct.fk_anio = a.id "
			+ "JOIN inscrito ins ON ins.fk_participante=pa.id AND (ins.fk_participante = :idpart OR :idpart = -1)"
			+ "JOIN inscritodetalle idt ON idt.fk_inscrito = ins.id "
			+ "JOIN evento e ON idt.fk_evento = e.id "
			+ "WHERE (e.estado = 1) "
			+ "AND (ct.fk_anio = :idanio OR :idanio = -1) "
			+ "AND (ct.fk_participante = :idpart OR :idpart = -1)",
	    nativeQuery = true)
	public List<Map<String, Object>> getEventoById(
	        @Param("idanio") int idanio,
	        @Param("idpart") int idpart)throws Exception ;
	
	@Query(value = "SELECT DISTINCT cat.id, cat.nombre, cat.estado\r\n"
			+ "FROM certificadocurso ct\r\n"
			+ "JOIN participante pa ON ct.fk_participante = pa.id\r\n"
			+ "JOIN persona p ON pa.fk_persona = p.id\r\n"
			+ "JOIN anio a ON ct.fk_anio = a.id\r\n"
			+ "JOIN curso c ON ct.fk_curso = c.id\r\n"
			+ "JOIN categoria cat ON c.fk_categoria = cat.id\r\n"
			+ "JOIN eventodetalle ed ON c.id = ed.fk_curso\r\n"
			+ "JOIN evento e ON ed.fk_evento = e.id\r\n"
			+ "JOIN inscritodetalle idt ON e.id = idt.fk_evento\r\n"
			+ "JOIN inscrito ins ON  ins.id=idt.fk_inscrito AND (ins.fk_participante = :idpart OR :idpart = -1)\r\n"
			+ "WHERE (cat.estado = 1)\r\n"
			+ "AND (e.id = :idevento OR :idevento = -1)\r\n"
			+ "AND (ct.fk_anio = :idanio OR :idanio = -1)\r\n"
			+ "AND (ct.fk_participante = :idpart OR :idpart = -1)",
	    nativeQuery = true)
	public List<Map<String, Object>> getCategoryById(
	 
	        @Param("idevento") int idevento,
	        @Param("idanio") int idanio,
	        @Param("idpart") int idpart)throws Exception ;
	
	
	@Query(value = "SELECT DISTINCT \r\n"
			+ "cert.id,\r\n"
			+ "cert.nrofolio,\r\n"
			+ "per1.id as id_per1,\r\n"
			+ "per1.ci as ci_part,\r\n"
			+ "per1.nombres as nombres_part,\r\n"
			+ "per1.apellidos as apellidos_part,\r\n"
			+ "c.nombrecurso as nombre_curso,\r\n"
			+ "e.fechainicial,\r\n"
			+ "cert.horasacademicas,\r\n"
			+ "per2.nombres as nombres_exp,\r\n"
			+ "per2.apellidos as apellidos_exp\r\n"
			+ "from certificadocurso cert\r\n"
			+ "JOIN curso c ON cert.fk_curso=c.id\r\n"
			+ "JOIN evento e ON cert.fk_evento=e.id\r\n"
			+ "JOIN participante part ON cert.fk_participante=part.id\r\n"
			+ "JOIN persona per1 ON part.fk_persona=per1.id\r\n"
			+ "JOIN expositor exp ON cert.fk_expositor=exp.id\r\n"
			+ "JOIN persona per2 ON exp.fk_persona=per2.id\r\n"
			+ "where cert.nrodocumento=:codigo \r\n"
			+ "",
	    nativeQuery = true)
	Map<String, Object>  findPorNrodocumento(String codigo);
} 