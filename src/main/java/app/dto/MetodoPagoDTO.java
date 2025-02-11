package app.dto;



import javax.persistence.Column;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import app.entity.BancoEntity;

public class MetodoPagoDTO {
	private Integer id; 
	private Integer codigo; 
	private String imagen;
	private String titular;
//	private String nombrebanco;
	private String nrocuenta;
	private String tipo;;
	private Integer estado;
	
	private BancoEntity banco;
	
	@Transient
//	@NotNull(message = "Por favor, seleccione un archivo")
	private MultipartFile logo;

	public MetodoPagoDTO() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public MetodoPagoDTO(Integer id, Integer codigo, String imagen, String titular, String nrocuenta, String tipo,
			Integer estado, BancoEntity banco, MultipartFile logo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.imagen = imagen;
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

	@Override
	public String toString() {
		return "MetodoPagoDTO [id=" + id + ", codigo=" + codigo + ", imagen=" + imagen + ", titular=" + titular
				+ ", nrocuenta=" + nrocuenta + ", tipo=" + tipo + ", estado=" + estado + ", banco=" + banco + ", logo="
				+ logo + "]";
	}

	
}
