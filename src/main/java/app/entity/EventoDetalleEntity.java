package app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "eventodetalle")
public class EventoDetalleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id; 
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "fechacurso")
	@DateTimeFormat(iso =ISO.DATE)
	private LocalDate fechacurso;//add
	
	@Column(name = "literalfechacurso")
	private String literalfechacurso; //add
	
	
	@DateTimeFormat(iso = ISO.TIME) 
	@Column(name = "horainicio")
	private LocalTime horainicio;

	@DateTimeFormat(iso = ISO.TIME) 
	@Column(name = "horafin")
	private LocalTime horafin;
	
	
	@Column(name = "lugarcurso")
	private String lugarcurso; //add
	
	@Column(name = "horasacademicas")
	private Integer horasacademicas;//add
	
	
	
	@Column(name = "estado")
	private Integer estado;
	
	@ManyToOne
	@JoinColumn(name="fk_curso")
	private CursoEntity curso;
	
	@ManyToOne
	@JoinColumn(name="fk_expositor")
	private ExpositorEntity expositor;

    @Transient//importante para que no cargue una compra previa
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_evento")
    private EventoEntity evento;


	public EventoDetalleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EventoDetalleEntity(Integer id, LocalDate fechacurso, String literalfechacurso, LocalTime horainicio,
			LocalTime horafin, String lugarcurso, Integer horasacademicas, Integer estado, CursoEntity curso,
			ExpositorEntity expositor, EventoEntity evento) {
		super();
		this.id = id;
		this.fechacurso = fechacurso;
		this.literalfechacurso = literalfechacurso;
		this.horainicio = horainicio;
		this.horafin = horafin;
		this.lugarcurso = lugarcurso;
		this.horasacademicas = horasacademicas;
		this.estado = estado;
		this.curso = curso;
		this.expositor = expositor;
		this.evento = evento;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public LocalDate getFechacurso() {
		return fechacurso;
	}


	public void setFechacurso(LocalDate fechacurso) {
		this.fechacurso = fechacurso;
	}


	public String getLiteralfechacurso() {
		return literalfechacurso;
	}


	public void setLiteralfechacurso(String literalfechacurso) {
		this.literalfechacurso = literalfechacurso;
	}


	public LocalTime getHorainicio() {
		return horainicio;
	}


	public void setHorainicio(LocalTime horainicio) {
		this.horainicio = horainicio;
	}


	public LocalTime getHorafin() {
		return horafin;
	}


	public void setHorafin(LocalTime horafin) {
		this.horafin = horafin;
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


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "EventoDetalleEntity [id=" + id + ", fechacurso=" + fechacurso + ", literalfechacurso="
				+ literalfechacurso + ", horainicio=" + horainicio + ", horafin=" + horafin + ", lugarcurso="
				+ lugarcurso + ", horasacademicas=" + horasacademicas + ", estado=" + estado + ", curso=" + curso
				+ ", expositor=" + expositor + ", evento=" + evento + "]";
	}


	

	
	
}
