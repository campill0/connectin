package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.*;

import org.ocpsoft.prettytime.PrettyTime;

@Entity
@Table(name = "AADD_NOTIFICACION")
public class Notificacion  implements Serializable{

	public Notificacion(){
		this.leida = false;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@OneToOne
	private Peticion peticion;
	@OneToOne
	private Mensaje mensaje;
	
	public Peticion getPeticion() {
		return peticion;
	}

	public void setPeticion(Peticion peticion) {
		this.peticion = peticion;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	private boolean leida;
	@Enumerated(EnumType.STRING)
	private TipoNotificacion tipo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@Basic
	private String descripcion;
	

	public TipoNotificacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoNotificacion tipo) {
		this.tipo = tipo;
	}

	public void setFecha(Date param) {
		this.fecha = param;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setDescripcion(String param) {
		this.descripcion = param;
	}

	public String getDescripcion() {
		return descripcion;
	}




	public boolean isLeida() {
		return leida;
	}

	public void setLeida(boolean leida) {
		this.leida = leida;
	}

	public String getFechaS(){
		PrettyTime p = new PrettyTime(new Locale("es"));
		return p.format(this.fecha);
	}
	// no nombro al metodo siguiendo la convención de los getter setter porque al pasarle un parámetro
	// desde los facelets lo entendería como una propiedad y fallaría. 
	public String fechaS(String locale){
		PrettyTime p = new PrettyTime(new Locale(locale));
		return p.format(this.fecha);
	}
	public boolean isNotificacionMensaje(){
		return this.tipo == TipoNotificacion.NOTIFICACION_MENSAJE;
	}

	public boolean isNotificacionGrupo(){
		return ( (this.tipo == TipoNotificacion.NOTIFICACION_GRUPO_PETICION_ACEPTADA) ||  (this.tipo == TipoNotificacion.NOTIFICACION_GRUPO_PETICION_RECHAZADA) ||  (this.tipo == TipoNotificacion.NOTIFICACION_GRUPO_PETICION_SOLICITADA));
	}

	public boolean isNotificacionAmistad(){
		return ((this.tipo == TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_ACEPTADA)||(this.tipo == TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_RECHAZADA)||(this.tipo == TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_SOLICITADA));
	}


}