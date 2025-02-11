
package app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "anio")
public class AnioEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;  
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "estado")
	private Integer estado;
	
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_institucion")
    private InstitucionEntity institucion;
	
	public AnioEntity() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public AnioEntity(Integer id, String nombre, Integer estado, InstitucionEntity institucion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
		this.institucion = institucion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public InstitucionEntity getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionEntity institucion) {
		this.institucion = institucion;
	}

	@Override
	public String toString() {
		return "AnioEntity [id=" + id + ", nombre=" + nombre + ", estado=" + estado + ", institucion=" + institucion
				+ "]";
	}



}
