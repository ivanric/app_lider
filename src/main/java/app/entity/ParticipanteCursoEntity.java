package app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="participantecurso")
public class ParticipanteCursoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id; 
	
	@Column(name = "codigo")
	private Integer codigo; 	
	
//	@DateTimeFormat(iso =ISO.DATE_TIME)
	@DateTimeFormat(iso =ISO.DATE)
	@Column(name = "fecharegistro")
	private LocalDateTime fecharegistro;

	@Column(name = "pagado")
	private Integer pagado;
	
	@ManyToOne
	@JoinColumn(name="fk_participante")
	private ParticipanteEntity participante;
	
	 
	@ManyToOne
	@JoinColumn(name="fk_curso")
	private CursoEntity curso;

	
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

	public ParticipanteCursoEntity() { 
		super();
		// TODO Auto-generated constructor stub
	}

	public ParticipanteCursoEntity(Integer id, Integer codigo, LocalDateTime fecharegistro, Integer pagado,
			ParticipanteEntity participante, CursoEntity curso, AnioEntity anio, Integer estado) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.fecharegistro = fecharegistro;
		this.pagado = pagado;
		this.participante = participante;
		this.curso = curso;
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

	public LocalDateTime getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(LocalDateTime fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	public Integer getPagado() {
		return pagado;
	}

	public void setPagado(Integer pagado) {
		this.pagado = pagado;
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

	@Override
	public String toString() {
		return "ParticipanteCursoEntity [id=" + id + ", codigo=" + codigo + ", fecharegistro=" + fecharegistro
				+ ", pagado=" + pagado + ", participante=" + participante + ", curso=" + curso + ", anio=" + anio
				+ ", estado=" + estado + "]";
	}

	
}
