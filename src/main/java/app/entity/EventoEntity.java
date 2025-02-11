package app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "evento") // el email sera unico
public class EventoEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	private Integer id; 
	
	@Column(name = "codigo")
	private Integer codigo; 
	
	@Column(name = "nroevento")
	private String nroevento; 
	
	@Column(name = "codigoevento")
	private String codigoevento; 
	
	@Column(name = "linkqr")//guarda el nombre del qr del evento para obtener el id del qr en drive
	private String linkqr;
	
	@Column(name = "imagen") 
	private String imagen;
	
	@Column(name = "imageneventoDriveId")  
	private String imageneventoDriveId;
		
	@Column(name = "imagencredencial") 
	private String imagencredencial;
	
	
	@Column(name = "imagencredencialDriveId")  
	private String imagencredencialDriveId;
	
	@Column(name = "detalle")
	private String detalle; 
	
	@Column(name = "modalidad")
	private String modalidad; 
	
	
	@Column(name = "tipo")
	private String tipo; 
	
	@Column(name = "linkgrupo")
	private String linkgrupo; 
	
	@Column(name = "imagen_qr_what") //para la imagen el qr generado del  grupo de whatsapp
	private String imagen_qr_what;
	
//	@DateTimeFormat(iso =ISO.DATE_TIME)
	@DateTimeFormat(iso =ISO.DATE)
	@Column(name = "fecharegistroinicio")
	private LocalDateTime fecharegistroinicio;//mod
	
	@DateTimeFormat(iso =ISO.DATE)
	@Column(name = "fecharegistrofin")
	private LocalDateTime fecharegistrofin;//add
	
//	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "fechainicial")
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechainicial;
	
//	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "fechafinal")
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechafinal;
	

	
	@DateTimeFormat(iso = ISO.TIME) 
	@Column(name = "hora")
	private LocalTime hora;
	
	@Column(name = "precio")
	private double precio; 
	
	@Column(name = "concertificado")
	private Integer concertificado;//add
	
	@Column(name = "conevaluacion")
	private Integer conevaluacion;//add
	
	@Column(name = "nroestudiantesinscritos")
	private Integer nroestudiantesinscritos;//add
	
	@Column(name = "nroestudiantescupo")
	private Integer nroestudiantescupo;//add
	
	
	@Column(name = "lugarevento")
	private String lugarevento; //add
	
	@Column(name = "detalleemail")
	private String detalleemail;
	
	
	@Column(name = "estado")
	private Integer estado;

	
	
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_anio")
    private AnioEntity anio;

	
	@OneToMany(cascade = CascadeType.REFRESH)//CascadeType.REFRESH,cuando hay un cambio en un autor se debe actualizar en un libro
	@JoinColumn(name = "fk_evento")
	private List<EventoDetalleEntity> eventodetalle=new ArrayList<>();
	
	
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_usuario")
    private UsuarioEntity usuario;
	
    
	@Column(name = "metodopagosino") 
	private Integer metodopagosino;//add 2
    
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE) //FetchType.EAGER =lo usaremos cuando lo necesitamos
	@JoinTable( //add 2
			name="eventopago",//crearemos una tabla intermedia+
			joinColumns = @JoinColumn(name="evento_id",referencedColumnName = "id"),
					inverseJoinColumns = {@JoinColumn(name="pago_id",referencedColumnName = "id")}
			)
	private Collection<MetodoPagoEntity> metodopagos;
    
    
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	@JsonIgnore
	private MultipartFile afiche;
	
	@Transient
	@JsonIgnore
	private MultipartFile qr_grupo_what;
	
	
	
	
	//metodos pre	
	@PrePersist
	public void asignarFechaRegistroInicio() {
		fecharegistroinicio=LocalDateTime.now();
	}


	public EventoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EventoEntity(Integer id, Integer codigo, String nroevento, String codigoevento, String linkqr, String imagen,
			String imageneventoDriveId, String imagencredencial, String imagencredencialDriveId, String detalle,
			String modalidad, String tipo, String linkgrupo, String imagen_qr_what, LocalDateTime fecharegistroinicio,
			LocalDateTime fecharegistrofin, LocalDate fechainicial, LocalDate fechafinal, LocalTime hora, double precio,
			Integer concertificado, Integer conevaluacion, Integer nroestudiantesinscritos, Integer nroestudiantescupo,
			String lugarevento, String detalleemail, Integer estado, AnioEntity anio,
			List<EventoDetalleEntity> eventodetalle, UsuarioEntity usuario, Integer metodopagosino,
			Collection<MetodoPagoEntity> metodopagos, MultipartFile afiche, MultipartFile qr_grupo_what) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nroevento = nroevento;
		this.codigoevento = codigoevento;
		this.linkqr = linkqr;
		this.imagen = imagen;
		this.imageneventoDriveId = imageneventoDriveId;
		this.imagencredencial = imagencredencial;
		this.imagencredencialDriveId = imagencredencialDriveId;
		this.detalle = detalle;
		this.modalidad = modalidad;
		this.tipo = tipo;
		this.linkgrupo = linkgrupo;
		this.imagen_qr_what = imagen_qr_what;
		this.fecharegistroinicio = fecharegistroinicio;
		this.fecharegistrofin = fecharegistrofin;
		this.fechainicial = fechainicial;
		this.fechafinal = fechafinal;
		this.hora = hora;
		this.precio = precio;
		this.concertificado = concertificado;
		this.conevaluacion = conevaluacion;
		this.nroestudiantesinscritos = nroestudiantesinscritos;
		this.nroestudiantescupo = nroestudiantescupo;
		this.lugarevento = lugarevento;
		this.detalleemail = detalleemail;
		this.estado = estado;
		this.anio = anio;
		this.eventodetalle = eventodetalle;
		this.usuario = usuario;
		this.metodopagosino = metodopagosino;
		this.metodopagos = metodopagos;
		this.afiche = afiche;
		this.qr_grupo_what = qr_grupo_what;
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


	public String getNroevento() {
		return nroevento;
	}


	public void setNroevento(String nroevento) {
		this.nroevento = nroevento;
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


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public String getImageneventoDriveId() {
		return imageneventoDriveId;
	}


	public void setImageneventoDriveId(String imageneventoDriveId) {
		this.imageneventoDriveId = imageneventoDriveId;
	}


	public String getImagencredencial() {
		return imagencredencial;
	}


	public void setImagencredencial(String imagencredencial) {
		this.imagencredencial = imagencredencial;
	}


	public String getImagencredencialDriveId() {
		return imagencredencialDriveId;
	}


	public void setImagencredencialDriveId(String imagencredencialDriveId) {
		this.imagencredencialDriveId = imagencredencialDriveId;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}


	public String getModalidad() {
		return modalidad;
	}


	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getLinkgrupo() {
		return linkgrupo;
	}


	public void setLinkgrupo(String linkgrupo) {
		this.linkgrupo = linkgrupo;
	}


	public String getImagen_qr_what() {
		return imagen_qr_what;
	}


	public void setImagen_qr_what(String imagen_qr_what) {
		this.imagen_qr_what = imagen_qr_what;
	}


	public LocalDateTime getFecharegistroinicio() {
		return fecharegistroinicio;
	}


	public void setFecharegistroinicio(LocalDateTime fecharegistroinicio) {
		this.fecharegistroinicio = fecharegistroinicio;
	}


	public LocalDateTime getFecharegistrofin() {
		return fecharegistrofin;
	}


	public void setFecharegistrofin(LocalDateTime fecharegistrofin) {
		this.fecharegistrofin = fecharegistrofin;
	}


	public LocalDate getFechainicial() {
		return fechainicial;
	}


	public void setFechainicial(LocalDate fechainicial) {
		this.fechainicial = fechainicial;
	}


	public LocalDate getFechafinal() {
		return fechafinal;
	}


	public void setFechafinal(LocalDate fechafinal) {
		this.fechafinal = fechafinal;
	}


	public LocalTime getHora() {
		return hora;
	}


	public void setHora(LocalTime hora) {
		this.hora = hora;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
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


	public String getLugarevento() {
		return lugarevento;
	}


	public void setLugarevento(String lugarevento) {
		this.lugarevento = lugarevento;
	}


	public String getDetalleemail() {
		return detalleemail;
	}


	public void setDetalleemail(String detalleemail) {
		this.detalleemail = detalleemail;
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


	public List<EventoDetalleEntity> getEventodetalle() {
		return eventodetalle;
	}


	public void setEventodetalle(List<EventoDetalleEntity> eventodetalle) {
		this.eventodetalle = eventodetalle;
	}


	public UsuarioEntity getUsuario() {
		return usuario;
	}


	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "EventoEntity [id=" + id + ", codigo=" + codigo + ", nroevento=" + nroevento + ", codigoevento="
				+ codigoevento + ", linkqr=" + linkqr + ", imagen=" + imagen + ", imageneventoDriveId="
				+ imageneventoDriveId + ", imagencredencial=" + imagencredencial + ", imagencredencialDriveId="
				+ imagencredencialDriveId + ", detalle=" + detalle + ", modalidad=" + modalidad + ", tipo=" + tipo
				+ ", linkgrupo=" + linkgrupo + ", imagen_qr_what=" + imagen_qr_what + ", fecharegistroinicio="
				+ fecharegistroinicio + ", fecharegistrofin=" + fecharegistrofin + ", fechainicial=" + fechainicial
				+ ", fechafinal=" + fechafinal + ", hora=" + hora + ", precio=" + precio + ", concertificado="
				+ concertificado + ", conevaluacion=" + conevaluacion + ", nroestudiantesinscritos="
				+ nroestudiantesinscritos + ", nroestudiantescupo=" + nroestudiantescupo + ", lugarevento="
				+ lugarevento + ", detalleemail=" + detalleemail + ", estado=" + estado + ", anio=" + anio
				+ ", eventodetalle=" + eventodetalle + ", usuario=" + usuario + ", metodopagosino=" + metodopagosino
				+ ", metodopagos=" + metodopagos + ", afiche=" + afiche + ", qr_grupo_what=" + qr_grupo_what + "]";
	}


	


}
