package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import app.entity.InstitucionEntity;


@Repository
public interface InstitucionRepository extends GenericRepositoryNormal<InstitucionEntity, Integer>{ 
	
	
	@Query(value="select COALESCE(max(id),0)+1 as id from institucion",nativeQuery = true)
	public Integer getIdPrimaryKey();
	
	@Query(value = "SELECT  p.* FROM institucion p WHERE upper(concat(p.institucion)) LIKE  concat('%',upper(?1),'%') and (p.estado=?2 or ?2=-1) ORDER BY p.id DESC LIMIT 10",nativeQuery = true)
	public List<InstitucionEntity> findAll(String clave,int estado);
	
	@Query(value = "select t.* from institucion t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.institucion,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM institucion t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.institucion,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<InstitucionEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	@Modifying
	@Query(value="UPDATE institucion SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(int status,Integer id);
	
	@Query(value="select count(t.*) from institucion t where (upper(concat(t.id,t.institucion,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);
	


} 