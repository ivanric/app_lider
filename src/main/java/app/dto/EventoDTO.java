package app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import app.entity.AnioEntity;
import app.entity.CategoriaEntity;
import app.entity.CursoEntity;
import app.entity.ExpositorEntity;
import app.entity.MetodoPagoEntity;
import app.entity.UsuarioEntity;

public class EventoDTO {
	
	private Integer id; 
	private Integer codigo; 
	private String codigoevento;
	private String linkqr;
	private String detalle;
	private Integer estado;
	
//	private LocalDateTime fecharegistroinicio;//mod
	@DateTimeFormat(iso =ISO.DATE_TIME)
	private LocalDateTime fecharegistrofin;//add
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechafinal;
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechainicial;
//	@DateTimeFormat(pattern = "HH:mm:ss")
	private LocalTime hora;
	
	private Integer concertificado;//add
	private Integer conevaluacion;//add
	private Integer nroestudiantesinscritos;//add
	private Integer nroestudiantescupo;//add
	
	
	private String imagen;
	private String imagen_qr_what;
	//imagenevento_drive_id
	private String linkgrupo;
	private String modalidad;
	private String nroevento; 
	
	private double precio; 
	private String tipo;	
	private AnioEntity anio;
	private UsuarioEntity usuario;
	
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile afiche;
	
	@Transient
	private MultipartFile qr_grupo_what;
	
//	List <Integer> idcurso=new ArrayList<>();
	List <CursoEntity> curso;
	List <ExpositorEntity> expositor;
	@DateTimeFormat(iso =ISO.DATE)
	List <LocalDate>  fechacurso;//add
	List <String> literalfechacurso;//add
//	@DateTimeFormat(pattern = "HH:mm:ss")
	List <LocalTime>  horainicio;
//	@DateTimeFormat(pattern = "HH:mm:ss")
	List <LocalTime>  horafin;
	List <String> lugarcurso;//add
	List <Integer> horasacademicas;//add
	
	
	private String lugarevento;//add 
	private Integer metodopagosino;//add 2
	private Collection<MetodoPagoEntity> metodopagos;//add 2
	
	public EventoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventoDTO(Integer id, Integer codigo, String codigoevento, String linkqr, String detalle, Integer estado,
			LocalDateTime fecharegistrofin, LocalDate fechafinal, LocalDate fechainicial, LocalTime hora,
			Integer concertificado, Integer conevaluacion, Integer nroestudiantesinscritos, Integer nroestudiantescupo,
			String imagen, String imagen_qr_what, String linkgrupo, String modalidad, String nroevento, double precio,
			String tipo, AnioEntity anio, UsuarioEntity usuario, MultipartFile afiche, MultipartFile qr_grupo_what,
			List<CursoEntity> curso, List<ExpositorEntity> expositor, List<LocalDate> fechacurso,
			List<String> literalfechacurso, List<LocalTime> horainicio, List<LocalTime> horafin,
			List<String> lugarcurso, List<Integer> horasacademicas, String lugarevento, Integer metodopagosino,
			Collection<MetodoPagoEntity> metodopagos) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.codigoevento = codigoevento;
		this.linkqr = linkqr;
		this.detalle = detalle;
		this.estado = estado;
		this.fecharegistrofin = fecharegistrofin;
		this.fechafinal = fechafinal;
		this.fechainicial = fechainicial;
		this.hora = hora;
		this.concertificado = concertificado;
		this.conevaluacion = conevaluacion;
		this.nroestudiantesinscritos = nroestudiantesinscritos;
		this.nroestudiantescupo = nroestudiantescupo;
		this.imagen = imagen;
		this.imagen_qr_what = imagen_qr_what;
		this.linkgrupo = linkgrupo;
		this.modalidad = modalidad;
		this.nroevento = nroevento;
		this.precio = precio;
		this.tipo = tipo;
		this.anio = anio;
		this.usuario = usuario;
		this.afiche = afiche;
		this.qr_grupo_what = qr_grupo_what;
		this.curso = curso;
		this.expositor = expositor;
		this.fechacurso = fechacurso;
		this.literalfechacurso = literalfechacurso;
		this.horainicio = horainicio;
		this.horafin = horafin;
		this.lugarcurso = lugarcurso;
		this.horasacademicas = horasacademicas;
		this.lugarevento = lugarevento;
		this.metodopagosino = metodopagosino;
		this.metodopagos = metodopagos;
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

	public String getCodigoevento() {
		return codigoevento;
	}

	public void setCodigoevento(String codigoevento) {
		this.codigoevento = codigoevento;
	}

	public String getLinkqr() {
		return linkqr;
	}

	public void setLinkqr(String linkqr) {
		this.linkqr = linkqr;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public LocalDateTime getFecharegistrofin() {
		return fecharegistrofin;
	}

	public void setFecharegistrofin(LocalDateTime fecharegistrofin) {
		this.fecharegistrofin = fecharegistrofin;
	}

	public LocalDate getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(LocalDate fechafinal) {
		this.fechafinal = fechafinal;
	}

	public LocalDate getFechainicial() {
		return fechainicial;
	}

	public void setFechainicial(LocalDate fechainicial) {
		this.fechainicial = fechainicial;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Integer getConcertificado() {
		return concertificado;
	}

	public void setConcertificado(Integer concertificado) {
		this.concertificado = concertificado;
	}

	public Integer getConevaluacion() {
		return conevaluacion;
	}

	public void setConevaluacion(Integer conevaluacion) {
		this.conevaluacion = conevaluacion;
	}

	public Integer getNroestudiantesinscritos() {
		return nroestudiantesinscritos;
	}

	public void setNroestudiantesinscritos(Integer nroestudiantesinscritos) {
		this.nroestudiantesinscritos = nroestudiantesinscritos;
	}

	public Integer getNroestudiantescupo() {
		return nroestudiantescupo;
	}

	public void setNroestudiantescupo(Integer nroestudiantescupo) {
		this.nroestudiantescupo = nroestudiantescupo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getImagen_qr_what() {
		return imagen_qr_what;
	}

	public void setImagen_qr_what(String imagen_qr_what) {
		this.imagen_qr_what = imagen_qr_what;
	}

	public String getLinkgrupo() {
		return linkgrupo;
	}

	public void setLinkgrupo(String linkgrupo) {
		this.linkgrupo = linkgrupo;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getNroevento() {
		return nroevento;
	}

	public void setNroevento(String nroevento) {
		this.nroevento = nroevento;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public AnioEntity getAnio() {
		return anio;
	}

	public void setAnio(AnioEntity anio) {
		this.anio = anio;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public MultipartFile getAfiche() {
		return afiche;
	}

	public void setAfiche(MultipartFile afiche) {
		this.afiche = afiche;
	}

	public MultipartFile getQr_grupo_what() {
		return qr_grupo_what;
	}

	public void setQr_grupo_what(MultipartFile qr_grupo_what) {
		this.qr_grupo_what = qr_grupo_what;
	}

	public List<CursoEntity> getCurso() {
		return curso;
	}

	public void setCurso(List<CursoEntity> curso) {
		this.curso = curso;
	}

	public List<ExpositorEntity> getExpositor() {
		return expositor;
	}

	public void setExpositor(List<ExpositorEntity> expositor) {
		this.expositor = expositor;
	}

	public List<LocalDate> getFechacurso() {
		return fechacurso;
	}

	public void setFechacurso(List<LocalDate> fechacurso) {
		this.fechacurso = fechacurso;
	}

	public List<String> getLiteralfechacurso() {
		return literalfechacurso;
	}

	public void setLiteralfechacurso(List<String> literalfechacurso) {
		this.literalfechacurso = literalfechacurso;
	}

	public List<LocalTime> getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(List<LocalTime> horainicio) {
		this.horainicio = horainicio;
	}

	public List<LocalTime> getHorafin() {
		return horafin;
	}

	public void setHorafin(List<LocalTime> horafin) {
		this.horafin = horafin;
	}

	public List<String> getLugarcurso() {
		return lugarcurso;
	}

	public void setLugarcurso(List<String> lugarcurso) {
		this.lugarcurso = lugarcurso;
	}

	public List<Integer> getHorasacademicas() {
		return horasacademicas;
	}

	public void setHorasacademicas(List<Integer> horasacademicas) {
		this.horasacademicas = horasacademicas;
	}

	public String getLugarevento() {
		return lugarevento;
	}

	public void setLugarevento(String lugarevento) {
		this.lugarevento = lugarevento;
	}

	public Integer getMetodopagosino() {
		return metodopagosino;
	}

	public void setMetodopagosino(Integer metodopagosino) {
		this.metodopagosino = metodopagosino;
	}

	public Collection<MetodoPagoEntity> getMetodopagos() {
		return metodopagos;
	}

	public void setMetodopagos(Collection<MetodoPagoEntity> metodopagos) {
		this.metodopagos = metodopagos;
	}

	@Override
	public String toString() {
		return "EventoDTO [id=" + id + ", codigo=" + codigo + ", codigoevento=" + codigoevento + ", linkqr=" + linkqr
				+ ", detalle=" + detalle + ", estado=" + estado + ", fecharegistrofin=" + fecharegistrofin
				+ ", fechafinal=" + fechafinal + ", fechainicial=" + fechainicial + ", hora=" + hora
				+ ", concertificado=" + concertificado + ", conevaluacion=" + conevaluacion
				+ ", nroestudiantesinscritos=" + nroestudiantesinscritos + ", nroestudiantescupo=" + nroestudiantescupo
				+ ", imagen=" + imagen + ", imagen_qr_what=" + imagen_qr_what + ", linkgrupo=" + linkgrupo
				+ ", modalidad=" + modalidad + ", nroevento=" + nroevento + ", precio=" + precio + ", tipo=" + tipo
				+ ", anio=" + anio + ", usuario=" + usuario + ", afiche=" + afiche + ", qr_grupo_what=" + qr_grupo_what
				+ ", curso=" + curso + ", expositor=" + expositor + ", fechacurso=" + fechacurso
				+ ", literalfechacurso=" + literalfechacurso + ", horainicio=" + horainicio + ", horafin=" + horafin
				+ ", lugarcurso=" + lugarcurso + ", horasacademicas=" + horasacademicas + ", lugarevento=" + lugarevento
				+ ", metodopagosino=" + metodopagosino + ", metodopagos=" + metodopagos + "]";
	}

	
}
