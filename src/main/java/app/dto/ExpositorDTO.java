package app.dto;



import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class ExpositorDTO {
	private Integer id; 
	private Integer codigo; 
	private String imagen;
	private String ci;
	private String exp;
	private String nombres;
	private String apellidos;
	private String rubro;
	private String dedicacion;
	private Integer celular;
	private String email;
	private String direccion;
	
	private Integer gradoacademico;
	private Integer profesion;
	
	@Transient
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile logo;

	public ExpositorDTO() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public ExpositorDTO(Integer id, Integer codigo, String imagen, String ci, String exp, String nombres,
			String apellidos, String rubro, String dedicacion, Integer celular, String email, String direccion,
			Integer gradoacademico, Integer profesion, MultipartFile logo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
		this.ci = ci;
		this.exp = exp;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.rubro = rubro;
		this.dedicacion = dedicacion;
		this.celular = celular;
		this.email = email;
		this.direccion = direccion;
		this.gradoacademico = gradoacademico;
		this.profesion = profesion;
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

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public String getDedicacion() {
		return dedicacion;
	}

	public void setDedicacion(String dedicacion) {
		this.dedicacion = dedicacion;
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

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "ExpositorDTO [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", ci=" + ci + ", exp=" + exp
				+ ", nombres=" + nombres + ", apellidos=" + apellidos + ", rubro=" + rubro + ", dedicacion="
				+ dedicacion + ", celular=" + celular + ", email=" + email + ", direccion=" + direccion
				+ ", gradoacademico=" + gradoacademico + ", profesion=" + profesion + ", logo=" + logo + "]";
	}

	
	
}
