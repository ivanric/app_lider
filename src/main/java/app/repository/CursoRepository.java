package app.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.CursoEntity;


@Repository
public interface CursoRepository extends GenericRepositoryNormal<CursoEntity, Integer>{ 
	
	
	@Query(value="select COALESCE(max(id),0)+1 as id from curso",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as codigo from curso",nativeQuery = true)
	public Integer getCodigo();
	
	@Query(value = "select t.* from curso t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombrecurso,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM curso t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombrecurso,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<CursoEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	@Query(value = "select t.* from curso t, categoria c, anio a "
			+ "where (t.estado=:estado or :estado=-1) "
			+ "and  (upper(concat(t.id,t.nombrecurso,'')) like concat('%',upper(:search),'%')) "
			+ "and t.fk_categoria=c.id "
			+ "and t.fk_anio=a.id "
			+ "and (c.id=:idcat or :idcat=-1) " 
			+ "and (a.id=:idanio or :idanio=-1) "
			+ "ORDER BY t.fk_categoria,t.id DESC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM curso t , categoria c, anio a "
					+ "where (t.estado=:estado or :estado=-1) "
					+ "and  (upper(concat(t.id,t.nombrecurso,'')) like concat('%',upper(:search),'%')) "
					+ "and t.fk_categoria=c.id "
					+ "t.fk_anio=a.id "
					+ "and (c.id=:idcat or :idcat=-1) "
					+ "and (a.id=:idanio or :idanio=-1) "
					+ "LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<CursoEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start,@Param("idcat") int idcat,@Param("idanio") int idanio);
	
	
	@Modifying
	@Query(value="UPDATE curso SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(int status,int id);
	
	
	@Query(value="select count(t.*) from curso t where (upper(concat(t.id,t.nombrecurso,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);
	
	@Query(value="select count(t.*) from curso t, categoria c, anio a  "
			+ "where (upper(concat(t.id,t.nombrecurso,'')) like concat('%',upper(:search),'%')) "
			+ "and (t.estado=:estado or :estado=-1) "
			+ "and t.fk_categoria=c.id  "
			+ "and t.fk_anio=a.id "
			+ "and (a.id=:idanio or :idanio=-1) "
			+ "and (c.id=:idcat or :idcat=-1) "
			+ "",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado,@Param("idcat") int idcat,@Param("idanio") int idanio);

	@Query(value="select (count(cr.*)+1) as codcurso from curso cr where cr.fk_categoria=:idcategoria",nativeQuery = true)
	public Integer getTotCursoPorCategoria(@Param("idcategoria") int idcategoria);

}  