package app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="certificadocurso")
public class CertificadoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id; 
	
	@Column(name = "codigo")
	private Integer codigo; 
	
	@Column(name = "codigocertificado")
	private Integer codigocertificado; 
	
//	@DateTimeFormat(iso =ISO.DATE_TIME)
	@DateTimeFormat(iso =ISO.DATE)
	@Column(name = "fecharegistro")
	private LocalDateTime fecharegistro;
	
	@Column(name = "qr")
	private String qr;
	
	@Column(name = "linkqr")
	private String linkqr;
	
	
	@Column(name = "nrofolio") 
	private String nrofolio; 
	
	@Column(name = "nrodocumento")
	private String nrodocumento; 
	
	@Column(name = "nota")
	private Integer nota;
	
	@Column(name = "lugarcurso")
	private String lugarcurso; //add
	
	@Column(name = "horasacademicas")
	private Integer horasacademicas;// add
	
	@Column(name = "tipocertificado")//add2
	private String tipocertificado;//add2
	
	@Column(name = "certificadoenviado")//add2
	private Integer certificadoenviado;//add2
	
//    @Transient//importante para que no cargue una compra previa
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_participante")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "detallecertificados"})
//  @JsonIgnore  // Evita la serialización
//	@JsonBackReference  // Evita la serialización infinita
//	@JsonIgnoreProperties({"detallecertificados"})
	private ParticipanteEntity participante;
	
	@ManyToOne
	@JoinColumn(name="fk_curso")
	private CursoEntity curso;
 
	@ManyToOne(optional = true)
	@JoinColumn(name="fk_expositor")//add
	private ExpositorEntity expositor;
	
    @ManyToOne
    @JoinColumn(name = "fk_evento")
	private EventoEntity evento;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_anio")
    private AnioEntity anio;
	
	
	@Column(name = "estado")
	private Integer estado;
	
	//metodos pre	
	@PrePersist
	public void asignarFechaRegistro() {
		fecharegistro=LocalDateTime.now();
	}

	public CertificadoEntity() { 
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificadoEntity(Integer id, Integer codigo, Integer codigocertificado, LocalDateTime fecharegistro,
			String qr, String linkqr, String nrofolio, String nrodocumento, Integer nota, String lugarcurso,
			Integer horasacademicas, String tipocertificado, Integer certificadoenviado,
			ParticipanteEntity participante, CursoEntity curso, ExpositorEntity expositor, EventoEntity evento,
			AnioEntity anio, Integer estado) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.codigocertificado = codigocertificado;
		this.fecharegistro = fecharegistro;
		this.qr = qr;
		this.linkqr = linkqr;
		this.nrofolio = nrofolio;
		this.nrodocumento = nrodocumento;
		this.nota = nota;
		this.lugarcurso = lugarcurso;
		this.horasacademicas = horasacademicas;
		this.tipocertificado = tipocertificado;
		this.certificadoenviado = certificadoenviado;
		this.participante = participante;
		this.curso = curso;
		this.expositor = expositor;
		this.evento = evento;
		this.anio = anio;
		this.estado = estado;
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

	public Integer getCodigocertificado() {
		return codigocertificado;
	}

	public void setCodigocertificado(Integer codigocertificado) {
		this.codigocertificado = codigocertificado;
	}

	public LocalDateTime getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(LocalDateTime fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	public String getLinkqr() {
		return linkqr;
	}

	public void setLinkqr(String linkqr) {
		this.linkqr = linkqr;
	}

	public String getNrofolio() {
		return nrofolio;
	}

	public void setNrofolio(String nrofolio) {
		this.nrofolio = nrofolio;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public String getLugarcurso() {
		return lugarcurso;
	}

	public void setLugarcurso(String lugarcurso) {
		this.lugarcurso = lugarcurso;
	}

	public Integer getHorasacademicas() {
		return horasacademicas;
	}

	public void setHorasacademicas(Integer horasacademicas) {
		this.horasacademicas = horasacademicas;
	}

	public String getTipocertificado() {
		return tipocertificado;
	}

	public void setTipocertificado(String tipocertificado) {
		this.tipocertificado = tipocertificado;
	}

	public Integer getCertificadoenviado() {
		return certificadoenviado;
	}

	public void setCertificadoenviado(Integer certificadoenviado) {
		this.certificadoenviado = certificadoenviado;
	}

	public ParticipanteEntity getParticipante() {
		return participante;
	}

	public void setParticipante(ParticipanteEntity participante) {
		this.participante = participante;
	}

	public CursoEntity getCurso() {
		return curso;
	}

	public void setCurso(CursoEntity curso) {
		this.curso = curso;
	}

	public ExpositorEntity getExpositor() {
		return expositor;
	}

	public void setExpositor(ExpositorEntity expositor) {
		this.expositor = expositor;
	}

	public EventoEntity getEvento() {
		return evento;
	}

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}

	public AnioEntity getAnio() {
		return anio;
	}

	public void setAnio(AnioEntity anio) {
		this.anio = anio;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CertificadoEntity [id=" + id + ", codigo=" + codigo + ", codigocertificado=" + codigocertificado
				+ ", fecharegistro=" + fecharegistro + ", qr=" + qr + ", linkqr=" + linkqr + ", nrofolio=" + nrofolio
				+ ", nrodocumento=" + nrodocumento + ", nota=" + nota + ", lugarcurso=" + lugarcurso
				+ ", horasacademicas=" + horasacademicas + ", tipocertificado=" + tipocertificado
				+ ", certificadoenviado=" + certificadoenviado + ", participante=" + participante + ", curso=" + curso
				+ ", expositor=" + expositor + ", evento=" + evento + ", anio=" + anio + ", estado=" + estado + "]";
	}

	
	
}
