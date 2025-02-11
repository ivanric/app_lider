package app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "participante") // el email sera unico
public class ParticipanteEntity  implements Serializable{


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
	
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_gradoacademico")
    private GradoAcademicoEntity gradoacademico; 
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_profesion")
    private ProfesionEntity profesion; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_departamento")
    private DepartamentoEntity departamento;
	
	@Column(name = "localidad") 
	private String localidad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_persona")
    private PersonaEntity persona;
	
    
//  	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE) //FetchType.EAGER =lo usaremos cuando lo necesitamos
//  	@JoinTable(
//  			name="participante_curso",//crearemos una tabla intermedia+
//  			joinColumns = @JoinColumn(name="participante_id",referencedColumnName = "id"),
//  					inverseJoinColumns = {@JoinColumn(name="curso_id",referencedColumnName = "id")}
//  			)
//  	private Collection<CursoEntity> cursos;
    
	@Column(name = "cantidad_cursos")
	private Integer cantidad_cursos;
  	
	@Column(name = "estado")
	private Integer estado;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)//CascadeType.REFRESH,cuando hay un cambio en un autor se debe actualizar en un libro
	@JoinColumn(name = "fk_participante")
	private List<CertificadoEntity> detallecertificados=new ArrayList<>();
	
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	@JsonIgnore
	private MultipartFile logo;
    
	public ParticipanteEntity() {
//		super();
	}

	
	public ParticipanteEntity(Integer id, Integer codigo, String imagen, String imagenDriveId,
			GradoAcademicoEntity gradoacademico, ProfesionEntity profesion, DepartamentoEntity departamento,
			String localidad, PersonaEntity persona, Integer cantidad_cursos, Integer estado,
			List<CertificadoEntity> detallecertificados, MultipartFile logo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
		this.imagenDriveId = imagenDriveId;
		this.gradoacademico = gradoacademico;
		this.profesion = profesion;
		this.departamento = departamento;
		this.localidad = localidad;
		this.persona = persona;
		this.cantidad_cursos = cantidad_cursos;
		this.estado = estado;
		this.detallecertificados = detallecertificados;
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


	public DepartamentoEntity getDepartamento() {
		return departamento;
	}


	public void setDepartamento(DepartamentoEntity departamento) {
		this.departamento = departamento;
	}


	public String getLocalidad() {
		return localidad;
	}


	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}


	public PersonaEntity getPersona() {
		return persona;
	}


	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}


	public Integer getCantidad_cursos() {
//		int cont=0;
//		for (int i = 0; i < detallecertificados.size(); i++) {
//			cont= cont+(i+1);
//		}
		this.cantidad_cursos=detallecertificados.size();
		return cantidad_cursos;
	}


	public void setCantidad_cursos(Integer cantidad_cursos) {
		this.cantidad_cursos = cantidad_cursos;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public List<CertificadoEntity> getDetallecertificados() {
		return detallecertificados;
	}


	public void setDetallecertificados(List<CertificadoEntity> detallecertificados) {
		this.detallecertificados = detallecertificados;
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
		return "ParticipanteEntity [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", imagenDriveId="
				+ imagenDriveId + ", gradoacademico=" + gradoacademico + ", profesion=" + profesion + ", departamento="
				+ departamento + ", localidad=" + localidad + ", persona=" + persona + ", cantidad_cursos="
				+ cantidad_cursos + ", estado=" + estado + ", logo=" + logo + "]";
	}
	

	
}
