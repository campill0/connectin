package controller.backingbeans;

import java.io.Serializable;  

import java.util.ArrayList;  
import java.util.Arrays;
import java.util.Date;
import java.util.List;  
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
import beans.EstadoMensaje;
import beans.EstadoPeticion;
import beans.Filtro;
import beans.Grupo;
import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.TipoNotificacion;
import beans.Usuario;
  
  @ViewScoped
  @ManagedBean(name="messagesBean")
public class MessagesBean implements Serializable  {  
    private Usuario user;
    private List<Mensaje> mensajesRecibidos;
    private List<Mensaje> mensajesEnviados;
    private Mensaje mensajeSeleccionado;
    private boolean carpetaRecibidos;
	public Mensaje getMensajeSeleccionado() {
		return mensajeSeleccionado;
	}

	public void setCarpetaRecibidos(boolean carpetaRecibidos) {
		this.carpetaRecibidos = carpetaRecibidos;
	}
	
	public void establecerCarpetaRecibidos(){
		carpetaRecibidos=true;
	}
	public void establecerCarpetaEnviados(){
		carpetaRecibidos=false;
	}
	
	
	public boolean isCarpetaRecibidos() {
		return carpetaRecibidos;
	}

	public void setMensajeSeleccionado(Mensaje mensajeSeleccionado) {
		this.mensajeSeleccionado = mensajeSeleccionado;
	}
	public boolean isMensajeSeleccionadoDetalle(){
		return mensajeSeleccionado!=null;
	}
	public boolean isTablaMensajes(){
	
		boolean rendered = mensajeSeleccionado == null; 
		System.out.println(rendered);
		return rendered;
		
	}
	public void volverALista(){
		mensajeSeleccionado=null;
	}
	public void fake(){}
	
	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static MensajeDAO mensajeDAO;

	public MessagesBean() throws DAOException { 
		System.out.println("constructor messages bean");
		cargarDatos();
		carpetaRecibidos=true;
		
		
		    
    }
	
	public void cargarDatos() throws DAOException{
		System.out.println("cargar datos");
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
        user = (Usuario) req.getSession().getAttribute("loggedUser");
        user=usuarioDAO.findUsuario(user.getId());
        req.getSession().setAttribute("loggedUser", user);
        mensajesRecibidos = new ArrayList<Mensaje>();
        mensajesRecibidos.addAll(user.getMensajesRecibidos());
        System.out.println("|" + mensajesRecibidos.size() + "|"); 
        mensajesEnviados = new ArrayList<Mensaje>();
        mensajesEnviados.addAll(user.getMensajesEnviados());
        System.out.println("|" + mensajesEnviados.size() + "|");
	}
	
	


	public List<Mensaje> getMensajesRecibidos() throws DAOException {
		
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
	    user=usuarioDAO.findUsuario(user.getId());
	    
		return user.getMensajesRecibidos();
	}

public Integer getMensajesRecibidosSinLeer() throws DAOException {
		
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
	    user=usuarioDAO.findUsuario(user.getId());
	  return user.getNumeroMensajesSinLeer();
	}

public boolean leido (Mensaje m)
{
return (m.getEstadoMensaje(user).getEstado() == EstadoMensaje.leido);	
}



	public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}




	public List<Mensaje> getMensajesEnviados() throws DAOException {
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
	    user=usuarioDAO.findUsuario(user.getId());
	    
		return user.getMensajesEnviados();
	}




	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}




	public boolean rendered(Mensaje m, String boton) throws DAOException{
	//	return (new Random()).nextBoolean();
		EstadoMensaje estado = m.getEstadoMensaje(user);
	if ( boton.equals("marcarleido"))
	{
	
		
		if(estado.isLeido()){
			return false;
		}
		else{
			return true;
		}
		
	} 
	else if ( boton.equals("marcarnoleido"))
	{
	
		
		if(estado.isLeido()){
			return true;
		}
		else{
			return false;
		}
		
	} 
	else if (boton.equals("leer"))
	{
		return true;
	}
	return false;
		
	}
	public String tituloMarcarLeido(Mensaje m){
		if(m.getEstadoMensaje(user).isLeido()){
			return "Marcar como no leído";	
		}
		else{
			return "Marcar como leído";	
		}
		
	}
	public void leerRecibido(Mensaje m) throws DAOException{
		m.getEstadoMensaje(user).setEstado(EstadoMensaje.leido);
		leerMensaje(m);
	}
	public void leerMensaje(Mensaje m) throws DAOException{
		
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		mensajeDAO = df.getMensajeDAO();
		mensajeDAO.update(m);
       
		mensajeSeleccionado=mensajeDAO.findMensaje(m.getId());
		
	}
	public void toggleLeido(Mensaje mensaje) throws DAOException{
		System.out.println("toggleido");
		if(mensaje.getEstadoMensaje(user).isLeido()){
			mensaje.getEstadoMensaje(user).setEstado(EstadoMensaje.noLeido);	
		}
		else{
			mensaje.getEstadoMensaje(user).setEstado(EstadoMensaje.leido);
		} 
		
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		mensajeDAO = df.getMensajeDAO();
		mensajeDAO.update(mensaje);
        cargarDatos();
	}
	
	public String  cssClassIcon(Mensaje m){
	if(m.getEstadoMensaje(user).isLeido()){
		return " icon-envelope";
	}
	else {
		 return "icon-envelope-alt";
	}
	
	}
	
	public String  cssClass(Mensaje m){
	if(m.getEstadoMensaje(user).isLeido()){
		return "readed-message";
	}
	else {
		 return "not-readed-message";
	}
	
	}
      
  
}  