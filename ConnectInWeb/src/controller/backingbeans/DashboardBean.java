package controller.backingbeans;

import java.io.Serializable;  

import java.util.ArrayList;  
import java.util.Arrays;
import java.util.Date;
import java.util.List;  
import java.util.Random;
import java.util.UUID;  

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import dao.CategoriaDAO;
import dao.DAOException;
import dao.DAOFactory;
import dao.GrupoDAO;
import dao.MensajeDAO;
import dao.NotificacionDAO;
import dao.PeticionDAO;
import dao.UsuarioDAO;
import dao.jpa.Util;

import beans.Categoria;
import beans.EstadoPeticion;
import beans.Filtro;
import beans.Grupo;
import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.TipoNotificacion;
import beans.Usuario;
  

@ManagedBean(name="dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable  {  
    private Usuario user;
    private List<Notificacion> notificaciones;
    public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

    private List<Notificacion> prueba;
	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
	public static String getRealPath(){
		return FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
	}
	public void strings(){
		System.out.println( getRealPath());
	}
	private Notificacion selectedNotificacion;
    private boolean filtroMensaje, filtroAmistad, filtroGrupo; 
	public boolean isFiltroMensaje() {
		
		
		return filtroMensaje;
	}

	public void setFiltroMensaje(boolean filtroMensaje) throws DAOException {
		user.getFiltro().setMensajes(filtroMensaje);
		usuarioDAO.update(user);
		user=usuarioDAO.findUsuario(user.getId());
		this.filtroMensaje = filtroMensaje;
		System.out.println("mensajeee");
		  notificaciones = new ArrayList<Notificacion>();
	        notificaciones.addAll(user.getNotificacionesFiltradas());
	        strings();
	}

	public boolean isFiltroAmistad() {
		return filtroAmistad;
	}

	public void setFiltroAmistad(boolean filtroAmistad) throws DAOException {
		user.getFiltro().setAmigos(filtroAmistad);
		usuarioDAO.update(user);
		user=usuarioDAO.findUsuario(user.getId());
		System.out.println("amistaddd");
		this.filtroAmistad = filtroAmistad;
		  notificaciones = new ArrayList<Notificacion>();
	        notificaciones.addAll(user.getNotificacionesFiltradas());
	}

	public boolean isFiltroGrupo() {
		return filtroGrupo;
	}

	public void fake(){}
	public void setFiltroGrupo(boolean filtroGrupo) throws DAOException {
		user.getFiltro().setGrupos(filtroGrupo);
		usuarioDAO.update(user);
		user=usuarioDAO.findUsuario(user.getId());
		this.filtroGrupo = filtroGrupo;
		System.out.println("grupoooo");
		  notificaciones = new ArrayList<Notificacion>();
	        notificaciones.addAll(user.getNotificacionesFiltradas());
	}

	public Notificacion getSelectedNotificacion() {
		return selectedNotificacion;
	}

	public void setSelectedNotificacion(Notificacion selectedNotificacion) {
		this.selectedNotificacion = selectedNotificacion;
		if(selectedNotificacion.getMensaje()!=null){
		System.out.println(this.selectedNotificacion.getMensaje().getTexto());
		}
	}

	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static PeticionDAO peticionDAO;
	private static NotificacionDAO notificacionDAO;
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
    


	public DashboardBean() throws DAOException { 
		System.out.println("constructor++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
		peticionDAO = df.getPeticionDAO();
		notificacionDAO =  df.getNotificacionDAO();
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
        user = (Usuario) req.getSession().getAttribute("loggedUser");
        user=usuarioDAO.findUsuario(user.getId());
        this.filtroAmistad=user.getFiltro().isAmigos();
        this.filtroGrupo=user.getFiltro().isGrupos();
        this.filtroMensaje =user.getFiltro().isMensajes();
        req.getSession().setAttribute("loggedUser", user);
        notificaciones = new ArrayList<Notificacion>();
        notificaciones.addAll(user.getNotificacionesFiltradas());
     /*   Mensaje m= new Mensaje();
        m.setAsunto("hola");
        selectedNotificacion= new Notificacion();
        selectedNotificacion.setDescripcion("prueba");
        selectedNotificacion.setFecha(new Date());
        selectedNotificacion.setId(1L);
        selectedNotificacion.setLeida(false);
        selectedNotificacion.setMensaje(m);
        selectedNotificacion.setPeticion(new Peticion());
        selectedNotificacion.setTipo(TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_ACEPTADA);
		*/
        prueba= new ArrayList<Notificacion>();
        Notificacion n1,n2,n3,n4,n5;
        n1= new Notificacion();n2= new Notificacion();n3= new Notificacion();n4= new Notificacion();n5= new Notificacion();
        n1.setDescripcion("Albacete");
        n2.setDescripcion("Bilbao");
        n3.setDescripcion("Cascais");
        n4.setDescripcion("Durango");
        n5.setDescripcion("Ernani");
        prueba.addAll(Arrays.asList(n1,n2,n3,n4,n5));
        
    }
	
	


	public List<Notificacion> getPrueba() {
		return prueba;
	}

	public void setPrueba(List<Notificacion> prueba) {
		this.prueba = prueba;
	}

	public boolean rendered(Notificacion n, String boton) throws DAOException{
	//	return (new Random()).nextBoolean();
	
	if ( boton.equals("aceptar") || boton.equals("rechazar"))
	{
		Peticion p=n.getPeticion();
		if(p==null){return false;}
		 p= peticionDAO.findPeticion(n.getPeticion().getId());
		// si la notificacion es de una peticion en estado todavia solicitado aparecen los botones en otro caso no
		// ademas el tipo de notificacion debe ser peticion solicitada
		boolean estadoSolicitado=(p.getEstado()==EstadoPeticion.SOLICITADO);
		
		switch (n.getTipo()) {
		case NOTIFICACION_CONTACTO_PETICION_SOLICITADA: return (true && estadoSolicitado);
		case NOTIFICACION_GRUPO_PETICION_SOLICITADA:	return (true && estadoSolicitado);
		
		case NOTIFICACION_CONTACTO_PETICION_ACEPTADA: return false;
		case NOTIFICACION_CONTACTO_PETICION_RECHAZADA: return false;
		case NOTIFICACION_GRUPO_PETICION_RECHAZADA:	return false;
		case NOTIFICACION_GRUPO_PETICION_ACEPTADA:	return false;
		case NOTIFICACION_MENSAJE:		return  false; 
			
		default:
			
			return false;
		}
	} 
	else if (boton.equals("leer"))
	{
		switch (n.getTipo()) {
		case NOTIFICACION_CONTACTO_PETICION_SOLICITADA: return false;
		case NOTIFICACION_CONTACTO_PETICION_ACEPTADA: return false;
		case NOTIFICACION_CONTACTO_PETICION_RECHAZADA: return false;
		case NOTIFICACION_GRUPO_PETICION_SOLICITADA:	return false;
		case NOTIFICACION_GRUPO_PETICION_RECHAZADA:	return false;
		case NOTIFICACION_GRUPO_PETICION_ACEPTADA:	return false;
		case NOTIFICACION_MENSAJE:		return  true; 
			
		default:
			
			return false;
		}
	}
	return false;
		
	}
	public String descripcionAmpliada(Notificacion n) throws DAOException{

		Notificacion notificacion=notificacionDAO.findNotificacion(n.getId());
		if (notificacion.getPeticion() == null){
			
			return controller.Util.internationalizedStringTokens(notificacion.getDescripcion());	
		}
		else{
			 
			
			
				
			if ( (notificacion.getPeticion().getEstado()!= EstadoPeticion.SOLICITADO) && ((notificacion.getTipo()==TipoNotificacion.NOTIFICACION_GRUPO_PETICION_SOLICITADA)|| (notificacion.getTipo()==TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_SOLICITADA))){
			
				return  controller.Util.internationalizedStringTokens(notificacion.getDescripcion() +"( #{msg.state_" + notificacion.getPeticion().getEstado().name().toLowerCase()+ "})");	
			}
			else{
				
				return controller.Util.internationalizedStringTokens(notificacion.getDescripcion());	
			}
			
		}
	}
	
	
	
	public void aceptarPeticion(Notificacion n) throws DAOException{
		
			if (n.getTipo()== TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_SOLICITADA){
			
		peticionDAO.aceptarPeticionAmistad(user, n.getPeticion());
		}
		else if (n.getTipo()== TipoNotificacion.NOTIFICACION_GRUPO_PETICION_SOLICITADA){
			peticionDAO.aceptarPeticionGrupo(n.getPeticion().getGrupo(), n.getPeticion());
		}
		
	
	}
	
	
	public void rechazarPeticion(Notificacion n) throws DAOException{
		
		if (n.getTipo()== TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_SOLICITADA){
			
		peticionDAO.rechazarPeticionAmistad(user, n.getPeticion());
		}
		else if (n.getTipo()== TipoNotificacion.NOTIFICACION_GRUPO_PETICION_SOLICITADA){
			peticionDAO.rechazarPeticionGrupo(n.getPeticion().getGrupo(), n.getPeticion());
		}
	
	}
	
	public String  cssClass(Notificacion n){
	
	switch (n.getTipo()) {
	case NOTIFICACION_CONTACTO_PETICION_SOLICITADA: return "icon-user";
	case NOTIFICACION_CONTACTO_PETICION_ACEPTADA: return "icon-thumbs-up";
	case NOTIFICACION_CONTACTO_PETICION_RECHAZADA: return "icon-thumbs-down";
	case NOTIFICACION_GRUPO_PETICION_SOLICITADA:	return "icon-group";
	case NOTIFICACION_GRUPO_PETICION_RECHAZADA:	return "icon-thumbs-down";
	case NOTIFICACION_GRUPO_PETICION_ACEPTADA:	return "icon-thumbs-up";
	case NOTIFICACION_MENSAJE:		return " icon-envelope"; 
		
	default:
		
		return "nada";
	}
		
	}
      
  
}  