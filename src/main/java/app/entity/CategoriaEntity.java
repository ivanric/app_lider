package app.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="categoria")
public class CategoriaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;  
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "abreviatura")
	private String abreviatura;
	
	@Column(name = "contacto")
	private String contacto;
	
	
	@Column(name = "estado")
	private Integer estado;

	
	
	public CategoriaEntity() {
//		super();
		// TODO Auto-generated constructor stub
	}



	public CategoriaEntity(Integer id, String nombre, String abreviatura, String contacto, Integer estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.abreviatura = abreviatura;
		this.contacto = contacto;
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



	public String getAbreviatura() {
		return abreviatura;
	}



	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}



	public String getContacto() {
		return contacto;
	}



	public void setContacto(String contacto) {
		this.contacto = contacto;
	}



	public Integer getEstado() {
		return estado;
	}



	public void setEstado(Integer estado) {
		this.estado = estado;
	}



	@Override
	public String toString() {
		return "CategoriaEntity [id=" + id + ", nombre=" + nombre + ", abreviatura=" + abreviatura + ", contacto="
				+ contacto + ", estado=" + estado + "]";
	}
	

}
