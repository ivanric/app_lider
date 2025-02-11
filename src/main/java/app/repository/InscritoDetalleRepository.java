package app.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.InscritoDetalleEntity;


@Repository 
public interface InscritoDetalleRepository extends GenericRepositoryNormal<InscritoDetalleEntity, Integer>{
	 
	
	@Query(value="select COALESCE(max(id),0)+1 as id from inscritodetalle",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as id from inscritodetalle",nativeQuery = true)
	public Integer getCodigo();
	
	/*
	@Query(value = "select t.* from inscritodetalle t,inscritodetalle pt,persona p where t.fk_inscritodetalle=pt.id and pt.fk_persona=p.id and  (t.estado=:estado or :estado=-1) and  (upper(concat(p.ci,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) from inscritodetalle t,inscritodetalle pt,persona p where t.fk_inscritodetalle=pt.id and pt.fk_persona=p.id (t.estado=:estado or :estado=-1) and  (upper(concat(p.ci,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<inscritodetalleDetalleEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	@Modifying 
	@Query(value="UPDATE inscritodetalle SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(Integer status,Integer id); 
	

	@Query(value="select count(t.*) from inscritodetalle t,inscritodetalle pt,persona p where t.fk_inscritodetalle=pt.id and tp.fk_persona=p.id and (upper(concat(p.ci,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);
	*/
	
} 