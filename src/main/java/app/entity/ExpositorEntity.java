package app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "expositor") // el email sera unico
public class ExpositorEntity  implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id 
	private Integer id; 
	
	
	@Column(name = "codigo")
	private Integer codigo; 	
	
	@Column(name = "imagen") 
	private String imagen;
	
	@Column(name = "imagenDriveId") 
	private String imagenDriveId;
	
	@Column(name = "rubro") 
	private String rubro;
	
	@Column(name = "dedicacion") 
	private String dedicacion;
	
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_gradoacademico")
    private GradoAcademicoEntity gradoacademico; 
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_profesion")
    private ProfesionEntity profesion; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_persona")
    private PersonaEntity persona;
	
	@Column(name = "estado")
	private Integer estado;
    
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile logo;
	
	
	public ExpositorEntity() {
//		super();
	}


	public ExpositorEntity(Integer id, Integer codigo, String imagen, String imagenDriveId, String rubro,
			String dedicacion, GradoAcademicoEntity gradoacademico, ProfesionEntity profesion, PersonaEntity persona,
			Integer estado, MultipartFile logo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
		this.imagenDriveId = imagenDriveId;
		this.rubro = rubro;
		this.dedicacion = dedicacion;
		this.gradoacademico = gradoacademico;
		this.profesion = profesion;
		this.persona = persona;
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


	public String getImagenDriveId() {
		return imagenDriveId;
	}


	public void setImagenDriveId(String imagenDriveId) {
		this.imagenDriveId = imagenDriveId;
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


	public GradoAcademicoEntity getGradoacademico() {
		return gradoacademico;
	}


	public void setGradoacademico(GradoAcademicoEntity gradoacademico) {
		this.gradoacademico = gradoacademico;
	}


	public ProfesionEntity getProfesion() {
		return profesion;
	}


	public void setProfesion(ProfesionEntity profesion) {
		this.profesion = profesion;
	}


	public PersonaEntity getPersona() {
		return persona;
	}


	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "ExpositorEntity [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", imagenDriveId="
				+ imagenDriveId + ", rubro=" + rubro + ", dedicacion=" + dedicacion + ", gradoacademico="
				+ gradoacademico + ", profesion=" + profesion + ", persona=" + persona + ", estado=" + estado
				+ ", logo=" + logo + "]";
	}


	
	
	
}
