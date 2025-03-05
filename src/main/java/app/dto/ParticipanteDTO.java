package app.dto;

import java.time.LocalDate;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import app.entity.ProvinciaEntity;


public class ParticipanteDTO {
	
	private Integer id; 
	private Integer codigo; 
	private String imagen;
	
	private String ci;
	private String exp;
	private String nombres;
	private String apellidos;
	private String genero;
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechanacimiento;
	private Integer edad;
	private Integer celular;
	private Integer gradoacademico;
	private Integer profesion;
	private String email;
	private Integer departamento;
	private Integer provincia;
	private String localidad;
	private String direccion;
	private Integer estado;
	  
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile archivoimgparticipante;
	
	public ParticipanteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParticipanteDTO(Integer id, Integer codigo, String imagen, String ci, String exp, String nombres,
			String apellidos, String genero, LocalDate fechanacimiento, Integer edad, Integer celular,
			Integer gradoacademico, Integer profesion, String email, Integer departamento, Integer provincia,
			String localidad, String direccion, Integer estado, MultipartFile archivoimgparticipante) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
		this.ci = ci;
		this.exp = exp;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.genero = genero;
		this.fechanacimiento = fechanacimiento;
		this.edad = edad;
		this.celular = celular;
		this.gradoacademico = gradoacademico;
		this.profesion = profesion;
		this.email = email;
		this.departamento = departamento;
		this.provincia = provincia;
		this.localidad = localidad;
		this.direccion = direccion;
		this.estado = estado;
		this.archivoimgparticipante = archivoimgparticipante;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public LocalDate getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(LocalDate fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}

	public Integer getGradoacademico() {
		return gradoacademico;
	}

	public void setGradoacademico(Integer gradoacademico) {
		this.gradoacademico = gradoacademico;
	}

	public Integer getProfesion() {
		return profesion;
	}

	public void setProfesion(Integer profesion) {
		this.profesion = profesion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Integer departamento) {
		this.departamento = departamento;
	}

	public Integer getProvincia() {
		return provincia;
	}

	public void setProvincia(Integer provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
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

	public MultipartFile getArchivoimgparticipante() {
		return archivoimgparticipante;
	}

	public void setArchivoimgparticipante(MultipartFile archivoimgparticipante) {
		this.archivoimgparticipante = archivoimgparticipante;
	}

	@Override
	public String toString() {
		return "ParticipanteDTO [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", ci=" + ci + ", exp=" + exp
				+ ", nombres=" + nombres + ", apellidos=" + apellidos + ", genero=" + genero + ", fechanacimiento="
				+ fechanacimiento + ", edad=" + edad + ", celular=" + celular + ", gradoacademico=" + gradoacademico
				+ ", profesion=" + profesion + ", email=" + email + ", departamento=" + departamento + ", provincia="
				+ provincia + ", localidad=" + localidad + ", direccion=" + direccion + ", estado=" + estado
				+ ", archivoimgparticipante=" + archivoimgparticipante + "]";
	}
	
}
