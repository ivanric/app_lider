package app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="inscrito")
public class InscritoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id; 
	
	@Column(name = "codigo")
	private Integer codigo; 	
	
//	@DateTimeFormat(iso =ISO.DATE_TIME)
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	@DateTimeFormat(iso =ISO.DATE)
	@Column(name = "fecharegistro")
	private LocalDateTime fecharegistro;

	@Column(name = "imagen") 
	private String imagen;
	
	@Column(name = "imagenDriveId") 
	private String imagenDriveId;
	
	
	@Column(name = "nrocuenta")
	private String nrocuenta; 
	
	@ManyToOne
	@JoinColumn(name="fk_banco")
	private BancoEntity banco; 

	@Column(name = "importe") 
	private double importe;

	
	@Column(name = "cantidad")
	private double cantidad;
	
	@Column(name = "descuento") 
	private double descuento;
	
	@Column(name = "subtotal")
	private double subtotal;
	
	@Column(name = "total") 
	private double total;
	
	
	@Column(name = "estado")
	private Integer estado;
	
	@ManyToOne
	@JoinColumn(name="fk_participante")
	private ParticipanteEntity participante;
	
	
    @ManyToOne(optional = false) 
    @JoinColumn(name = "fk_anio")
    private AnioEntity anio;
    
//	@Transient
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private UsuarioEntity usuario;
    
	@OneToMany(cascade = CascadeType.REFRESH)//CascadeType.REFRESH,cuando hay un cambio en un autor se debe actualizar en un libro
	@JoinColumn(name = "fk_inscrito")
	private List<InscritoDetalleEntity> detalleInscrito=new ArrayList<>();;
	
	
	//metodos pre	
	@PrePersist
	public void asignarFechaRegistro() {
		fecharegistro=LocalDateTime.now();
	}

	public InscritoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InscritoEntity(Integer id, Integer codigo, LocalDateTime fecharegistro, String imagen, String imagenDriveId,
			String nrocuenta, BancoEntity banco, double importe, double cantidad, double descuento, double subtotal,
			double total, Integer estado, ParticipanteEntity participante, AnioEntity anio, UsuarioEntity usuario,
			List<InscritoDetalleEntity> detalleInscrito) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.fecharegistro = fecharegistro;
		this.imagen = imagen;
		this.imagenDriveId = imagenDriveId;
		this.nrocuenta = nrocuenta;
		this.banco = banco;
		this.importe = importe;
		this.cantidad = cantidad;
		this.descuento = descuento;
		this.subtotal = subtotal;
		this.total = total;
		this.estado = estado;
		this.participante = participante;
		this.anio = anio;
		this.usuario = usuario;
		this.detalleInscrito = detalleInscrito;
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

	public String getNrocuenta() {
		return nrocuenta;
	}

	public void setNrocuenta(String nrocuenta) {
		this.nrocuenta = nrocuenta;
	}

	public BancoEntity getBanco() {
		return banco;
	}

	public void setBanco(BancoEntity banco) {
		this.banco = banco;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public ParticipanteEntity getParticipante() {
		return participante;
	}

	public void setParticipante(ParticipanteEntity participante) {
		this.participante = participante;
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

	public List<InscritoDetalleEntity> getDetalleInscrito() {
		return detalleInscrito;
	}

	public void setDetalleInscrito(List<InscritoDetalleEntity> detalleInscrito) {
		this.detalleInscrito = detalleInscrito;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "InscritoEntity [id=" + id + ", codigo=" + codigo + ", fecharegistro=" + fecharegistro + ", imagen="
				+ imagen + ", imagenDriveId=" + imagenDriveId + ", nrocuenta=" + nrocuenta + ", banco=" + banco
				+ ", importe=" + importe + ", cantidad=" + cantidad + ", descuento=" + descuento + ", subtotal="
				+ subtotal + ", total=" + total + ", estado=" + estado + ", participante=" + participante + ", anio="
				+ anio + ", usuario=" + usuario + ", detalleInscrito=" + detalleInscrito + "]";
	}
//	@Override
//	public String toString() {
//	    return "InscritoEntity [id=" + id + ", codigo=" + codigo + ", fecharegistro=" + fecharegistro + ", imagen=" + imagen
//	            + ", imagenDriveId=" + imagenDriveId + ", nrocuenta=" + nrocuenta + ", banco=" + banco
//	            + ", importe=" + importe + ", cantidad=" + cantidad + ", descuento=" + descuento + ", subtotal="
//	            + subtotal + ", total=" + total + ", estado=" + estado + ", participante=" + participante
//	            + ", anio=" + anio + ", usuario=" + usuario + "]";
//	}

	

}
