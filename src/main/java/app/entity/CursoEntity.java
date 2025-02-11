package app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "curso") // el email sera unico
public class CursoEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	private Integer id; 
	
	@Column(name = "codigo")
	private Integer codigo; 
	
	@Column(name = "nrodocumento")
	private String nrodocumento; 
	
//	@DateTimeFormat(iso =ISO.DATE_TIME)
	@DateTimeFormat(iso =ISO.DATE)
	@Column(name = "fecharegistro")
	private LocalDateTime fecharegistro;
	
	@Column(name = "imagencertificado")
	private String imagencertificado;
	
	@Column(name = "imagencertificadoDriveId") 
	private String imagencertificadoDriveId;
	
	@Column(name = "imagencertificadosf")
	private String imagencertificadosf;
	
	@Column(name = "imagencertificadoDriveIdsf") 
	private String imagencertificadoDriveIdsf;
	
	@Column(name = "nombrecurso")
	private String nombrecurso; 
	
	
	@Column(name = "nivel")
	private String nivel; 
	
	@Column(name = "glosacertificado")
	private String glosacertificado; 
	
	@Column(name = "dedicatoriacertificado")
	private String dedicatoriacertificado;
	
	@Column(name = "estado")
	private Integer estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_categoria")
    private CategoriaEntity categoria; 
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_anio")
    private AnioEntity anio;
	
	@Transient
	@JsonIgnore
	private MultipartFile certificado_imagen;
	@Transient
	@JsonIgnore
	private MultipartFile certificado_imagen_sf;
	
	
	//metodos pre	
	@PrePersist
	public void asignarFechaRegistro() {
		fecharegistro=LocalDateTime.now();
	}

	
	public CursoEntity() {
		super();
		// TODO Auto-generated constructor stub 
	}


	public CursoEntity(Integer id, Integer codigo, String nrodocumento, LocalDateTime fecharegistro,
			String imagencertificado, String imagencertificadoDriveId, String imagencertificadosf,
			String imagencertificadoDriveIdsf, String nombrecurso, String nivel, String glosacertificado,
			String dedicatoriacertificado, Integer estado, CategoriaEntity categoria, AnioEntity anio,
			MultipartFile certificado_imagen, MultipartFile certificado_imagen_sf) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nrodocumento = nrodocumento;
		this.fecharegistro = fecharegistro;
		this.imagencertificado = imagencertificado;
		this.imagencertificadoDriveId = imagencertificadoDriveId;
		this.imagencertificadosf = imagencertificadosf;
		this.imagencertificadoDriveIdsf = imagencertificadoDriveIdsf;
		this.nombrecurso = nombrecurso;
		this.nivel = nivel;
		this.glosacertificado = glosacertificado;
		this.dedicatoriacertificado = dedicatoriacertificado;
		this.estado = estado;
		this.categoria = categoria;
		this.anio = anio;
		this.certificado_imagen = certificado_imagen;
		this.certificado_imagen_sf = certificado_imagen_sf;
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


	public String getNrodocumento() {
		return nrodocumento;
	}


	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}


	public LocalDateTime getFecharegistro() {
		return fecharegistro;
	}


	public void setFecharegistro(LocalDateTime fecharegistro) {
		this.fecharegistro = fecharegistro;
	}


	public String getImagencertificado() {
		return imagencertificado;
	}


	public void setImagencertificado(String imagencertificado) {
		this.imagencertificado = imagencertificado;
	}


	public String getImagencertificadoDriveId() {
		return imagencertificadoDriveId;
	}


	public void setImagencertificadoDriveId(String imagencertificadoDriveId) {
		this.imagencertificadoDriveId = imagencertificadoDriveId;
	}


	public String getImagencertificadosf() {
		return imagencertificadosf;
	}


	public void setImagencertificadosf(String imagencertificadosf) {
		this.imagencertificadosf = imagencertificadosf;
	}


	public String getImagencertificadoDriveIdsf() {
		return imagencertificadoDriveIdsf;
	}


	public void setImagencertificadoDriveIdsf(String imagencertificadoDriveIdsf) {
		this.imagencertificadoDriveIdsf = imagencertificadoDriveIdsf;
	}


	public String getNombrecurso() {
		return nombrecurso;
	}


	public void setNombrecurso(String nombrecurso) {
		this.nombrecurso = nombrecurso;
	}


	public String getNivel() {
		return nivel;
	}


	public void setNivel(String nivel) {
		this.nivel = nivel;
	}


	public String getGlosacertificado() {
		return glosacertificado;
	}


	public void setGlosacertificado(String glosacertificado) {
		this.glosacertificado = glosacertificado;
	}


	public String getDedicatoriacertificado() {
		return dedicatoriacertificado;
	}


	public void setDedicatoriacertificado(String dedicatoriacertificado) {
		this.dedicatoriacertificado = dedicatoriacertificado;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public CategoriaEntity getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
	}


	public AnioEntity getAnio() {
		return anio;
	}


	public void setAnio(AnioEntity anio) {
		this.anio = anio;
	}


	public MultipartFile getCertificado_imagen() {
		return certificado_imagen;
	}


	public void setCertificado_imagen(MultipartFile certificado_imagen) {
		this.certificado_imagen = certificado_imagen;
	}


	public MultipartFile getCertificado_imagen_sf() {
		return certificado_imagen_sf;
	}


	public void setCertificado_imagen_sf(MultipartFile certificado_imagen_sf) {
		this.certificado_imagen_sf = certificado_imagen_sf;
	}


	@Override
	public String toString() {
		return "CursoEntity [id=" + id + ", codigo=" + codigo + ", nrodocumento=" + nrodocumento + ", fecharegistro="
				+ fecharegistro + ", imagencertificado=" + imagencertificado + ", imagencertificadoDriveId="
				+ imagencertificadoDriveId + ", imagencertificadosf=" + imagencertificadosf
				+ ", imagencertificadoDriveIdsf=" + imagencertificadoDriveIdsf + ", nombrecurso=" + nombrecurso
				+ ", nivel=" + nivel + ", glosacertificado=" + glosacertificado + ", dedicatoriacertificado="
				+ dedicatoriacertificado + ", estado=" + estado + ", categoria=" + categoria + ", anio=" + anio
				+ ", certificado_imagen=" + certificado_imagen + ", certificado_imagen_sf=" + certificado_imagen_sf
				+ "]";
	}


	
	
}
