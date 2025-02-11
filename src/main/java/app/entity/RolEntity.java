package app.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="rol")
public class RolEntity  implements Serializable{


	private static final long serialVersionUID = 1L;
	@Id 
	private Integer id; 
	private String nombre;
	private int estado;
	
//	@ManyToOne(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER)
//	@JoinColumn(name = "usuario_id")
//    private UsuarioEntity usuario; 
	 
	

	public RolEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolEntity(Integer id, String nombre, int estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
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
	
	public int getEstado() {
		return estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "RolEntity [id=" + id + ", nombre=" + nombre + ", estado=" + estado + "]";
	}
		
	
}
