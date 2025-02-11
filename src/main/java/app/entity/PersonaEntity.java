package app.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="persona")
public class PersonaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	private Integer id; 
	@Column(name = "ci")
	private String ci;
	@Column(name = "exp")
	private String exp;
	@Column(name = "nombres")
	private String nombres;
	
	@Column(name = "apellidos")
	private String apellidos;
	
	@Column(name = "edad")//add
	private Integer edad;
	
//	@JsonFormat(pattern="dd/MM/yyyy HH:mm")//add
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "fechanacimiento") 
	private LocalDate fechanacimiento; 
	
	@Column(name = "genero")//add
	private String genero;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "celular")
	private Integer celular;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "estado")
	private Integer estado;
	public PersonaEntity() {
//		super();
		// TODO Auto-generated constructor stub
	}
	public PersonaEntity(Integer id, String ci, String exp, String nombres, String apellidos, Integer edad,
			LocalDate fechanacimiento, String genero, String email, Integer celular, String direccion, Integer estado) {
		super();
		this.id = id;
		this.ci = ci;
		this.exp = exp;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.edad = edad;
		this.fechanacimiento = fechanacimiento;
		this.genero = genero;
		this.email = email;
		this.celular = celular;
		this.direccion = direccion;
		this.estado = estado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public LocalDate getFechanacimiento() {
		return fechanacimiento;
	}
	public void setFechanacimiento(LocalDate fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCelular() {
		return celular;
	}
	public void setCelular(Integer celular) {
		this.celular = celular;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "PersonaEntity [id=" + id + ", ci=" + ci + ", exp=" + exp + ", nombres=" + nombres + ", apellidos="
				+ apellidos + ", edad=" + edad + ", fechanacimiento=" + fechanacimiento + ", genero=" + genero
				+ ", email=" + email + ", celular=" + celular + ", direccion=" + direccion + ", estado=" + estado + "]";
	}
	

}
