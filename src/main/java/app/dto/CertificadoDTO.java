package app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import app.entity.AnioEntity;
import app.entity.BancoEntity;
import app.entity.DepartamentoEntity;
import app.entity.EventoEntity;
import app.entity.GradoAcademicoEntity;
import app.entity.InscritoEntity;
import app.entity.ProfesionEntity;
import app.entity.ProvinciaEntity;

public class CertificadoDTO {

	private Integer id;
	private Integer codigo;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime fecharegistro;
	private String qr;
	private String linkqr;
	private String nrofolio;
	private String nrodocumento;
	private Integer nota;
	private String lugarcurso; // add
	private Integer horasacademicas;// add
	private String tipocertificado;// add2
	private Integer certificadoenviado;// add2

	// datosInscrito
	private Integer idinscrito;
	private InscritoEntity inscrito;

	private Integer idper;
	private Integer idpart;
	private Integer idpartact;
//	private Integer codigo;//ins
//	private String imagen;
//	private String imagenDriveId;
	private String imagenparticipante;
	private String imagenpago;// ins

	private String nrocuenta;
	private BancoEntity banco;
	private double importe;
	private double cantidad;
	private double precio;
	private double descuento;
	private double subtotal;
	private EventoEntity evento;
	private AnioEntity anio;
	@Transient // para que no se guarde en la bd, para que sea tempora
	private MultipartFile archivoimgparticipante;
	@Transient
	private MultipartFile archivoimgpago;

	// datosParticipante
	private String ci;
	private String exp;
	private String nombres;
	private String apellidos;
	private String genero;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechanacimiento;
	private Integer edad;
	private Integer celular;
	private GradoAcademicoEntity gradoacademico;
	private ProfesionEntity profesion;
	private String email;
	private DepartamentoEntity departamento;
	private ProvinciaEntity provincia;
	private String localidad;
	private String direccion;
//	private String tipocertificado;
	private Integer estado;
	
	public CertificadoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificadoDTO(Integer id, Integer codigo, LocalDateTime fecharegistro, String qr, String linkqr,
			String nrofolio, String nrodocumento, Integer nota, String lugarcurso, Integer horasacademicas,
			String tipocertificado, Integer certificadoenviado, Integer idinscrito, InscritoEntity inscrito,
			Integer idper, Integer idpart, Integer idpartact, String imagenparticipante, String imagenpago,
			String nrocuenta, BancoEntity banco, double importe, double cantidad, double precio, double descuento,
			double subtotal, EventoEntity evento, AnioEntity anio, MultipartFile archivoimgparticipante,
			MultipartFile archivoimgpago, String ci, String exp, String nombres, String apellidos, String genero,
			LocalDate fechanacimiento, Integer edad, Integer celular, GradoAcademicoEntity gradoacademico,
			ProfesionEntity profesion, String email, DepartamentoEntity departamento, ProvinciaEntity provincia,
			String localidad, String direccion, Integer estado) {
		super();
		this.id = id;
		this.codigo = codigo;
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
		this.idinscrito = idinscrito;
		this.inscrito = inscrito;
		this.idper = idper;
		this.idpart = idpart;
		this.idpartact = idpartact;
		this.imagenparticipante = imagenparticipante;
		this.imagenpago = imagenpago;
		this.nrocuenta = nrocuenta;
		this.banco = banco;
		this.importe = importe;
		this.cantidad = cantidad;
		this.precio = precio;
		this.descuento = descuento;
		this.subtotal = subtotal;
		this.evento = evento;
		this.anio = anio;
		this.archivoimgparticipante = archivoimgparticipante;
		this.archivoimgpago = archivoimgpago;
		this.ci = ci;
		this.exp = exp;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.genero = genero;
		this.fechanacimiento = fechanacimiento;
		this.edad = edad;
		this.celular = celular;
		this.gradoacademico = gradoacademico;
		this.profesion = profesion;
		this.email = email;
		this.departamento = departamento;
		this.provincia = provincia;
		this.localidad = localidad;
		this.direccion = direccion;
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

	public Integer getIdinscrito() {
		return idinscrito;
	}

	public void setIdinscrito(Integer idinscrito) {
		this.idinscrito = idinscrito;
	}

	public InscritoEntity getInscrito() {
		return inscrito;
	}

	public void setInscrito(InscritoEntity inscrito) {
		this.inscrito = inscrito;
	}

	public Integer getIdper() {
		return idper;
	}

	public void setIdper(Integer idper) {
		this.idper = idper;
	}

	public Integer getIdpart() {
		return idpart;
	}

	public void setIdpart(Integer idpart) {
		this.idpart = idpart;
	}

	public Integer getIdpartact() {
		return idpartact;
	}

	public void setIdpartact(Integer idpartact) {
		this.idpartact = idpartact;
	}

	public String getImagenparticipante() {
		return imagenparticipante;
	}

	public void setImagenparticipante(String imagenparticipante) {
		this.imagenparticipante = imagenparticipante;
	}

	public String getImagenpago() {
		return imagenpago;
	}

	public void setImagenpago(String imagenpago) {
		this.imagenpago = imagenpago;
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

	public MultipartFile getArchivoimgparticipante() {
		return archivoimgparticipante;
	}

	public void setArchivoimgparticipante(MultipartFile archivoimgparticipante) {
		this.archivoimgparticipante = archivoimgparticipante;
	}

	public MultipartFile getArchivoimgpago() {
		return archivoimgpago;
	}

	public void setArchivoimgpago(MultipartFile archivoimgpago) {
		this.archivoimgpago = archivoimgpago;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public LocalDate getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(LocalDate fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Integer getCelular() {
		return celular;
	}

	public void setCelular(Integer celular) {
		this.celular = celular;
	}

	public GradoAcademicoEntity getGradoacademico() {
		return gradoacademico;
	}

	public void setGradoacademico(GradoAcademicoEntity gradoacademico) {
		this.gradoacademico = gradoacademico;
	}

	public ProfesionEntity getProfesion() {
		return profesion;
	}

	public void setProfesion(ProfesionEntity profesion) {
		this.profesion = profesion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DepartamentoEntity getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoEntity departamento) {
		this.departamento = departamento;
	}

	public ProvinciaEntity getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaEntity provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "CertificadoDTO [id=" + id + ", codigo=" + codigo + ", fecharegistro=" + fecharegistro + ", qr=" + qr
				+ ", linkqr=" + linkqr + ", nrofolio=" + nrofolio + ", nrodocumento=" + nrodocumento + ", nota=" + nota
				+ ", lugarcurso=" + lugarcurso + ", horasacademicas=" + horasacademicas + ", tipocertificado="
				+ tipocertificado + ", certificadoenviado=" + certificadoenviado + ", idinscrito=" + idinscrito
				+ ", inscrito=" + inscrito + ", idper=" + idper + ", idpart=" + idpart + ", idpartact=" + idpartact
				+ ", imagenparticipante=" + imagenparticipante + ", imagenpago=" + imagenpago + ", nrocuenta="
				+ nrocuenta + ", banco=" + banco + ", importe=" + importe + ", cantidad=" + cantidad + ", precio="
				+ precio + ", descuento=" + descuento + ", subtotal=" + subtotal + ", evento=" + evento + ", anio="
				+ anio + ", archivoimgparticipante=" + archivoimgparticipante + ", archivoimgpago=" + archivoimgpago
				+ ", ci=" + ci + ", exp=" + exp + ", nombres=" + nombres + ", apellidos=" + apellidos + ", genero="
				+ genero + ", fechanacimiento=" + fechanacimiento + ", edad=" + edad + ", celular=" + celular
				+ ", gradoacademico=" + gradoacademico + ", profesion=" + profesion + ", email=" + email
				+ ", departamento=" + departamento + ", provincia=" + provincia + ", localidad=" + localidad
				+ ", direccion=" + direccion + ", estado=" + estado + "]";
	}
	
}
