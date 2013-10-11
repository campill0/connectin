package beans;

import javax.persistence.*;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.hamcrest.Matchers;

import static ch.lambdaj.Lambda.*; 
import static org.hamcrest.Matchers.*;
import static java.util.Arrays.*;
import beans.TipoNotificacion;
@Entity
@Table(name = "AADD_USUARIO")
@NamedQueries({
	@NamedQuery(name="getTodosUsuarios",
	query="SELECT u FROM Usuario u WHERE u.id <> :idUsuario"),
	@NamedQuery(name="getUsuarioPorNombre",
	query="SELECT u FROM Usuario u WHERE lower(u.login) LIKE :nombreUsuario")
})
public class Usuario implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="USUARIO_ID",nullable=false)
	private Long id;
	@Column(unique=true)
	private String login;
	private String password;
	private String nombre;
	private String apellidos;
	private String locale;
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	private String mail;
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] imagen = null;

	@OneToOne(cascade=CascadeType.ALL )
	private Filtro filtro;
	
	@OneToMany(cascade=CascadeType.MERGE)
	private List<Peticion> peticiones;

	//Relaciones//
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Grupo> grupos;
	@OneToMany(fetch= FetchType.EAGER)
	private List<Usuario> amigos;
	
	@OneToMany(mappedBy="administrador", fetch=FetchType.EAGER)
	private List<Grupo> gruposAdministrados;
	
	
	
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Notificacion> notificaciones;
	
	public String getNombreCompleto(){
		return nombre + " " + apellidos;
	}
	
	@ManyToMany(mappedBy = "receptores", fetch=FetchType.EAGER)
	private List<Mensaje> mensajesRecibidos;
	@OneToMany(mappedBy = "emisor", fetch=FetchType.EAGER) 
	private List<Mensaje> mensajesEnviados;
	
	public Usuario(){
		this.peticiones = new ArrayList<Peticion>();
		this.grupos = new ArrayList<Grupo>();
		this.amigos = new ArrayList<Usuario>();
		this.gruposAdministrados = new ArrayList<Grupo>();
		this.notificaciones = new ArrayList<Notificacion>();
		this.mensajesEnviados = new ArrayList<Mensaje>();
		this.mensajesRecibidos = new ArrayList<Mensaje>();
		this.filtro = new Filtro();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupo(List<Grupo> param) {
		this.grupos = param;
	}



	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public List<Mensaje> getMensajesEnviados() {
		Collections.sort(this.mensajesEnviados, new MensajeComparator());

		return this.mensajesEnviados;
	}

	public void setMensajesEnviados(List<Mensaje> param) {
		this.mensajesEnviados = param;
	}

	public List<Mensaje> getMensajesRecibidos() {
		Collections.sort(this.mensajesRecibidos, new MensajeComparator());
		return this.mensajesRecibidos;
	}

	public void setMensajesRecibidos(List<Mensaje> param) {
		this.mensajesRecibidos = param;
	}

	public void setLogin(String param) {
		this.login = param;
	}

	public String getLogin() {
		return login;
	}

	public void setPassword(String param) {
		this.password = param;
	}

	public String getPassword() {
		return password;
	}

	public void setNombre(String param) {
		this.nombre = param;
	}

	public String getNombre() {
		return nombre;
	}

	public void setApellidos(String param) {
		this.apellidos = param;
	}

	public String getApellidos() {
		return apellidos;
	}


	public void setMail(String param) {
		this.mail = param;
	}

	public String getMail() {
		return mail;
	}

	public List<Grupo> getGruposAdministrados() {
		return gruposAdministrados;
	}

	public void setGruposAdministrados(List<Grupo> gruposAdministrados) {
		this.gruposAdministrados = gruposAdministrados;
	}

	public List<Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Usuario> amigos) {
		this.amigos = amigos;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Peticion> getPeticiones() {
		return peticiones;
	}

	public void setPeticiones(List<Peticion> peticiones) {
		this.peticiones = peticiones;
	}
	
	public boolean isTieneImagen(){
		return (this.imagen != null);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result
				+ ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		Usuario u = (Usuario)obj;
		if(u.getNombre().equals(this.nombre) && u.getLogin().equals(this.login) && u.getMail().equals(this.mail) && u.getPassword().equals(this.password)){
			return true;
		}
		return false;
	}

	public boolean isTieneGruposAdministrados(){
		return (this.gruposAdministrados.size() > 0);
	}
	
	public Integer solicitudEnviadaPendiente(Usuario usu){
		
		if(usu.getAmigos().contains(this)){
			return 3;
		}
		for(Peticion p:this.peticiones){
			if((p.getEstado() == EstadoPeticion.SOLICITADO) && (p.getPeticionario().equals(usu))){
				return 1;
			}
		}
		return 2;
	}
	
	public Integer getNumeroPeticionesAmigos(){
		return this.peticiones.size();
	}
	
	public Integer getNumeroMensajesSinLeer(){
		Integer numero = 0;
		for(Mensaje m:this.mensajesRecibidos){
			if(m.isNuevo(this)){
				numero++;
			}
		}
		return numero;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
	
	public boolean isNotificacionesMensajes(){
		for(Notificacion n:this.notificaciones){
			if(n.isNotificacionMensaje() && !n.isLeida()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isNotificacionesAmistad(){
		for(Notificacion n:this.notificaciones){
			if(n.isNotificacionAmistad() && !n.isLeida()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isNotificacionesGrupo(){
		for(Notificacion n:this.notificaciones){
			if(n.isNotificacionGrupo() && !n.isLeida()){
				return true;
			}
		}
		return false;
	}
	
	public List<Notificacion> getNotificacionesFiltradas()
	{
		
List <TipoNotificacion> tipos = new ArrayList<TipoNotificacion>();
if(filtro.isAmigos()){ tipos.addAll(Arrays.asList(TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_ACEPTADA,TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_RECHAZADA,TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_SOLICITADA));}
if(filtro.isGrupos()){tipos.addAll(Arrays.asList(TipoNotificacion.NOTIFICACION_GRUPO_PETICION_ACEPTADA,TipoNotificacion.NOTIFICACION_GRUPO_PETICION_RECHAZADA,TipoNotificacion.NOTIFICACION_GRUPO_PETICION_SOLICITADA));}
if(filtro.isMensajes()){tipos.add(TipoNotificacion.NOTIFICACION_MENSAJE);}

//List<Integer> biggerThan3 = filter(greaterThan(3), asList(1, 2, 3, 4, 5));
		List<Notificacion> notificacionesFiltradas= 
				filter(
						having(
								on(Notificacion.class).getTipo(),
									isIn(tipos)
								)
						,notificaciones);
		 notificacionesFiltradas = sort(notificacionesFiltradas, on(Notificacion.class).getFecha());
	//	System.out.println(notificacionesFiltradas.size() + "notificaciones filtradas");
	return notificacionesFiltradas;
	}
	
	public String toString(){
		
		return id+":"+login;
	}
	
}