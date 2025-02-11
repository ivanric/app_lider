package app.dto;

import java.time.LocalDate;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;


public class ParticipanteDTO {
	
	private Integer id; 
	private Integer codigo; 
	private String imagen;
	
	private String ci;
	private String exp;
	private Integer gradoacademico;
	private String genero;
	private Integer departamento;
	private String nombres;
	private String apellidos;
	private Integer edad;
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechanacimiento;
	private String localidad;
	private Integer profesion;
	private Integer celular;
	private String email;
	private String direccion;
	private Integer estado;
	  
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile logo;
	
	public ParticipanteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParticipanteDTO(Integer id, Integer codigo, String imagen, String ci, String exp, Integer gradoacademico,
			String genero, Integer departamento, String nombres, String apellidos, Integer edad,
			LocalDate fechanacimiento, String localidad, Integer profesion, Integer celular, String email,
			String direccion, Integer estado, MultipartFile logo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
		this.ci = ci;
		this.exp = exp;
		this.gradoacademico = gradoacademico;
		this.genero = genero;
		this.departamento = departamento;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.edad = edad;
		this.fechanacimiento = fechanacimiento;
		this.localidad = localidad;
		this.profesion = profesion;
		this.celular = celular;
		this.email = email;
		this.direccion = direccion;
		this.estado = estado;
		this.logo = logo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
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

	public Integer getGradoacademico() {
		return gradoacademico;
	}

	public void setGradoacademico(Integer gradoacademico) {
		this.gradoacademico = gradoacademico;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
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

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Integer getProfesion() {
		return profesion;
	}

	public void setProfesion(Integer profesion) {
		this.profesion = profesion;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "ParticipanteDTO [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", ci=" + ci + ", exp=" + exp
				+ ", gradoacademico=" + gradoacademico + ", genero=" + genero + ", departamento=" + departamento
				+ ", nombres=" + nombres + ", apellidos=" + apellidos + ", edad=" + edad + ", fechanacimiento="
				+ fechanacimiento + ", localidad=" + localidad + ", profesion=" + profesion + ", celular=" + celular
				+ ", email=" + email + ", direccion=" + direccion + ", estado=" + estado + ", logo=" + logo + "]";
	}

	
	
}
