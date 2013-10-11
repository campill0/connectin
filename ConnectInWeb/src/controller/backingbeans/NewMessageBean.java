package controller.backingbeans;

import java.io.Serializable;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;  

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import dao.DAOException;
import dao.DAOFactory;
import dao.MensajeDAO;
import dao.UsuarioDAO;

import beans.Mensaje;
import beans.Usuario;
  
  @ViewScoped
  @ManagedBean(name="newMessageBean")
public class NewMessageBean implements Serializable  {  
  private String asunto;
  private boolean nuevoMensajeRender;
  
  public boolean isListaMensajesRender() {
	return !nuevoMensajeRender;
}

public boolean isEnviarMensajeBotonRender(){
	return (receptores.size()>0 && nuevoMensajeRender && !asunto.equals(""));
}

public boolean isNuevoMensajeRender() {
	return nuevoMensajeRender;
}

public void setNuevoMensajeRender(boolean nuevoMensajeRender) {
	this.nuevoMensajeRender = nuevoMensajeRender;
}

private String mensaje;
  private Usuario user;
	private List<Usuario> contactos;
  public List<Usuario> getContactos() {
		return contactos;
	}

	public void setContactos(List<Usuario> contactos) {
		this.contactos = contactos;
	}

private List<Usuario> receptores;
  private Mensaje mensajeRespondido;
	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static MensajeDAO mensajeDAO;
	
	public void nuevoMensaje(Mensaje replyMensaje) throws DAOException{
		nuevoMensajeRender=!nuevoMensajeRender;
		receptores= new ArrayList <Usuario>();
		asunto = "";
	
	
		mensaje = "";
		mensajeRespondido = replyMensaje;
		if(mensajeRespondido!=null){
			
			Date date = mensajeRespondido.getFecha();
			SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy H:m");
			String fecha = sdf.format(date);
			
			String cabecera="<p>\n <br/><br/><br/>From: " + mensajeRespondido.getEmisor().getLogin() + "<br/>\n" +
			"To: " + mensajeRespondido.getDestinatariosString() + "<br/>\n" +
			"Subject: " + mensajeRespondido.getAsunto() + "<br/>\n" +
			"Date: "  + fecha + "<br/>\n" ;
			asunto = "FW: "+mensajeRespondido.getAsunto();
			mensaje= cabecera ;
			mensaje+= "\n<hr/>\n";
			mensaje+= mensajeRespondido.getTexto();
		}
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		MessagesBean messagesBean = (MessagesBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "messagesBean");
		messagesBean.setMensajeSeleccionado(null);
	
	}

public NewMessageBean() throws DAOException {
	super();
	nuevoMensajeRender=false;
	// TODO Auto-generated constructor stub
	receptores= new ArrayList <Usuario>();
	asunto = "";
	mensaje = "";
	
	df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
	usuarioDAO= df.getUsuarioDAO();
	mensajeDAO= df.getMensajeDAO();
	HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
    user = (Usuario) req.getSession().getAttribute("loggedUser");
    
    contactos = user.getAmigos();
    contactos.add(user);
   // user.getAmigos().get(0);
    //contactos=user.getAmigos();
    System.out.println();
	
	}

public String getAsunto() {
	return asunto;
}

public void setAsunto(String asunto) {
	this.asunto = asunto;
}
public void cancelarNuevoMensaje(){
	nuevoMensajeRender=false;
}
public void enviarNuevoMensaje() throws DAOException{
	nuevoMensajeRender=false;
	mensajeDAO.createMensaje(mensajeRespondido, false, mensaje,new Date(),asunto,user,receptores);
}
public void handleSave() { 
	//add faces message with update text value
 }
public String getReceptoresStr()
{
	if(receptores.size()==0){return "";}
	String receptoresStr="";
	for (Usuario u : receptores) {
		receptoresStr+=u.getLogin()+",";
	}
	
	return receptoresStr.substring(0, receptoresStr.length()-1);
}
public String getMensaje() {
	return mensaje;
}
public void seleccionarContactoHandler(){
	
}

public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
}

public List<Usuario> getReceptores() {
	return receptores;
}

public void setReceptores(List<Usuario> receptores) {
	this.receptores = receptores;
}

public Mensaje getMensajeRespondido() {
	return mensajeRespondido;
}

public void setMensajeRespondido(Mensaje mensajeRespondido) {
	this.mensajeRespondido = mensajeRespondido;
}

}  