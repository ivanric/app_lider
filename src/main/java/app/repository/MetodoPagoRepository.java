package app.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.MetodoPagoEntity;

@Repository 
public interface MetodoPagoRepository extends GenericRepositoryNormal<MetodoPagoEntity, Integer>{
	 
	
	@Query(value="select COALESCE(max(id),0)+1 as id from metodopago",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as codigo from metodopago",nativeQuery = true)
	public Integer getCodigo();
	
	@Query(value = "select t.* from metodopago t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.titular,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM metodopago t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.titular,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<MetodoPagoEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	
	@Modifying 
	@Query(value="UPDATE metodopago SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(Integer status,Integer id); 
	

	@Query(value="select count(t.*) from metodopago t where (upper(concat(t.id,t.titular,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);
	
	
} 