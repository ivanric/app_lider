package app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "metodopago") // el email sera unico
public class MetodoPagoEntity  implements Serializable{


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
	
	
	@Column(name = "titular") 
	private String titular;
	
//	@Column(name = "nombrebanco") 
//	private String nombrebanco;
	
	@Column(name = "nrocuenta") 
	private String nrocuenta;
	
	@Column(name = "tipo") //credito o debito
	private String tipo;
	
	@Column(name = "estado")
	private Integer estado;
	
	
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_banco")
    private BancoEntity banco;
    
	@Transient//para que no se guarde en la bd, para que sea tempora
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile logo;
	
	
	public MetodoPagoEntity() {
//		super();
	}


	public MetodoPagoEntity(Integer id, Integer codigo, String imagen, String imagenDriveId, String titular,
			String nrocuenta, String tipo, Integer estado, BancoEntity banco, MultipartFile logo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
		this.imagenDriveId = imagenDriveId;
		this.titular = titular;
		this.nrocuenta = nrocuenta;
		this.tipo = tipo;
		this.estado = estado;
		this.banco = banco;
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


	public String getTitular() {
		return titular;
	}


	public void setTitular(String titular) {
		this.titular = titular;
	}


	public String getNrocuenta() {
		return nrocuenta;
	}


	public void setNrocuenta(String nrocuenta) {
		this.nrocuenta = nrocuenta;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public BancoEntity getBanco() {
		return banco;
	}


	public void setBanco(BancoEntity banco) {
		this.banco = banco;
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
		return "MetodoPagoEntity [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", imagenDriveId="
				+ imagenDriveId + ", titular=" + titular + ", nrocuenta=" + nrocuenta + ", tipo=" + tipo + ", estado="
				+ estado + ", banco=" + banco + ", logo=" + logo + "]";
	}


	 
}
