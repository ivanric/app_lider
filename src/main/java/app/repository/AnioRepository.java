package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.AnioEntity;


@Repository
public interface AnioRepository extends GenericRepositoryNormal<AnioEntity, Integer>{ 
	
	@Query(value="select COALESCE(max(id),0)+1 as id from anio",nativeQuery = true)
	public Integer getIdPrimaryKey();
	@Query(value = "SELECT  p.* FROM anio p WHERE upper(concat(p.nombre)) LIKE  concat('%',upper(?1),'%') and (p.estado=?2 or ?2=-1) ORDER BY p.id DESC LIMIT 10",nativeQuery = true)
	public List<AnioEntity> findAll(String clave,int estado);
	
	@Query(value = "select t.* from anio t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM anio t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<AnioEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	@Modifying
	@Query(value="UPDATE anio SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(int status,Integer id);
	
	@Query(value="select count(t.*) from anio t where (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);


} 