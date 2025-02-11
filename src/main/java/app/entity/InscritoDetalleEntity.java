package app.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "inscritodetalle")
public class InscritoDetalleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id; 
 
	@Column(name = "codigo")
	private Integer codigo; 	
	
	@Column(name = "cantidad")
	private double cantidad;
	
	@Column(name = "precio")
	private double precio; 
	 
	@Column(name = "descuento")
	private double descuento;
	
	@Column(name = "subtotal")
	private double subtotal;
	
	@Column(name = "estado")
	private Integer estado;
	
	@ManyToOne
	@JoinColumn(name="fk_evento")
	private EventoEntity evento;

	

    @Transient//importante para que no cargue una compra previa
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fk_inscrito")
    private InscritoEntity inscrito;
        
	public InscritoDetalleEntity() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public InscritoDetalleEntity(Integer id, Integer codigo, double cantidad, double precio, double descuento,
			double subtotal, Integer estado, EventoEntity evento, InscritoEntity inscrito) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.cantidad = cantidad;
		this.precio = precio;
		this.descuento = descuento;
		this.subtotal = subtotal;
		this.estado = estado;
		this.evento = evento;
		this.inscrito = inscrito;
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

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
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

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public EventoEntity getEvento() {
		return evento;
	}

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}

	public InscritoEntity getInscrito() {
		return inscrito;
	}

	public void setInscrito(InscritoEntity inscrito) {
		this.inscrito = inscrito;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
