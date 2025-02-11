package app.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import app.entity.AnioEntity;
import app.entity.CategoriaEntity;
import app.entity.ExpositorEntity;

public class CursoDTO {
	
	private Integer id; 
	private Integer codigo; 
	private String nrodocumento; 
	private String nombrecurso;
	private String nivel;
	private String dedicatoriacertificado;
	private String glosacertificado;
	private Integer estado;
	
	
	private AnioEntity anio;
    private CategoriaEntity categoria;

	
	@Transient
	private MultipartFile certificado_imagen;
	
	@Transient
	private MultipartFile certificado_imagen_sf;
	
	public CursoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CursoDTO(Integer id, Integer codigo, String nrodocumento, String nombrecurso, String nivel,
			String dedicatoriacertificado, String glosacertificado, Integer estado, AnioEntity anio,
			CategoriaEntity categoria, MultipartFile certificado_imagen, MultipartFile certificado_imagen_sf) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nrodocumento = nrodocumento;
		this.nombrecurso = nombrecurso;
		this.nivel = nivel;
		this.dedicatoriacertificado = dedicatoriacertificado;
		this.glosacertificado = glosacertificado;
		this.estado = estado;
		this.anio = anio;
		this.categoria = categoria;
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

	public String getDedicatoriacertificado() {
		return dedicatoriacertificado;
	}

	public void setDedicatoriacertificado(String dedicatoriacertificado) {
		this.dedicatoriacertificado = dedicatoriacertificado;
	}

	public String getGlosacertificado() {
		return glosacertificado;
	}

	public void setGlosacertificado(String glosacertificado) {
		this.glosacertificado = glosacertificado;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public AnioEntity getAnio() {
		return anio;
	}

	public void setAnio(AnioEntity anio) {
		this.anio = anio;
	}

	public CategoriaEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
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
		return "CursoDTO [id=" + id + ", codigo=" + codigo + ", nrodocumento=" + nrodocumento + ", nombrecurso="
				+ nombrecurso + ", nivel=" + nivel + ", dedicatoriacertificado=" + dedicatoriacertificado
				+ ", glosacertificado=" + glosacertificado + ", estado=" + estado + ", anio=" + anio + ", categoria="
				+ categoria + ", certificado_imagen=" + certificado_imagen + ", certificado_imagen_sf="
				+ certificado_imagen_sf + "]";
	}

	
	
}
