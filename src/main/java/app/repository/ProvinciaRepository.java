package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.ProvinciaEntity;


@Repository
public interface ProvinciaRepository extends GenericRepositoryNormal<ProvinciaEntity, Integer>{ 
	
	ProvinciaEntity findByNombre(String name);
	
	@Query(value="select COALESCE(max(id),0)+1 as id from provincia",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value = "select t.* from provincia t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM provincia t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<ProvinciaEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	@Modifying
	@Query(value="UPDATE provincia SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(int status,int id);
	
	
	@Query(value="select count(t.*) from provincia t where (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);

	
	@Query(value = "select t.* from provincia t where t.estado=1  and t.fk_departamento=:iddep ORDER BY t.id ASC ",
			nativeQuery = true)
	public List<ProvinciaEntity> findAll(@Param("iddep") int iddep);
	
	
}  