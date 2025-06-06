package app.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.entity.EventoEntity;


@Repository
public interface EventoRepository extends GenericRepositoryNormal<EventoEntity, Integer>{ 
	
	
	@Query(value="select COALESCE(max(id),0)+1 as id from evento",nativeQuery = true)
	public int getIdPrimaryKey();
	
	@Query(value="SELECT COALESCE(max(codigo),0)+1 as codigo from evento",nativeQuery = true)
	public Integer getCodigo();
	
	@Query(value = "select t.* from evento t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.detalle,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ",
			countQuery ="SELECT count(t.*) FROM evento t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.detalle,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ",		
			nativeQuery = true)
	public List<EventoEntity> findAll(@Param("estado") int estado,@Param("search") String search,@Param("length") int length,@Param("start") int start);
	
	@Modifying
	@Query(value="UPDATE evento SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2",nativeQuery = true)
	public void updateStatus(int status,int id);
	
	
	@Query(value="select count(t.*) from evento t where (upper(concat(t.id,t.detalle,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ",nativeQuery = true)
	public Integer getTotAll(@Param("search") String search,@Param("estado") Integer estado);

	@Query(value="select (count(cr.*)+1) as codevento from evento cr where cr.fk_categoria=:idcategoria",nativeQuery = true)
	public Integer getTotCursoPorCategoria(@Param("idcategoria") int idcategoria);
	
	
	EventoEntity findByCodigoevento(String codigo);

}  