package controller.backingbeans;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.startsWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.hamcrest.core.IsNot;

import controller.Util;

import beans.Notificacion;
import beans.Usuario;

import dao.DAOException;
import dao.DAOFactory;
import dao.PeticionDAO;
import dao.UsuarioDAO;

@ManagedBean(name="contactosBean")
@ViewScoped
public class ContactosBean implements Serializable{
	private static final long serialVersionUID = 6534129279183404641L;
	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static PeticionDAO peticionDAO;
	private List<Usuario> contactos;
	private List<Usuario> usuarios;
	private List<Usuario> todos;
	private Usuario contactoSeleccionado=null;
	private Usuario usuarioSeleccionado=null;
	private boolean botonPeticionAmistadDisabled=true;
	
	private List<Usuario> contactosyyo;
	private Usuario user;
	public boolean isMostrarContactoSeleccionado() {
		return (contactoSeleccionado!=null);
	}
	public boolean isMostrarTextoPeticionYaSolicitada(){
		return (isBotonPeticionAmistadDisabled()&& isMostrarUsuarioSeleccionado());
	}
	public boolean isMostrarUsuarioSeleccionado() {
		return (usuarioSeleccionado!=null);
	}
	public Usuario getContactoSeleccionado() {
		return contactoSeleccionado;
	}
	public void setContactoSeleccionado(Usuario contactoSeleccionado) {
		System.out.println("contactoSeleccionado");
		this.contactoSeleccionado = contactoSeleccionado;
	}
	public List<Usuario> getContactos() {
		return contactos;
	}
	public void setContactos(List<Usuario> contactos) {
		this.contactos = contactos;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public List<Usuario> getTodos() {
		return todos;
	}
	public void setTodos(List<Usuario> todos) {
		this.todos = todos;
	}
	public ContactosBean() throws DAOException{
		
		botonPeticionAmistadDisabled=true;
	df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
	usuarioDAO= df.getUsuarioDAO();
	peticionDAO= df.getPeticionDAO();
	HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
    user = (Usuario) req.getSession().getAttribute("loggedUser");
    refresh();
    if (contactos.size()>0){
	contactoSeleccionado=contactos.get(0);
    }
    
    
//	usuarioSeleccionado= usuarios.get(0);
}
	private void refresh() throws DAOException {
	user=usuarioDAO.findUsuario(user.getId());
	
	contactos=user.getAmigos();

	// lambdaj
	
	contactosyyo= new ArrayList<Usuario> ();
	contactosyyo.addAll(user.getAmigos());
	contactosyyo.add(user);
	
	 todos = usuarioDAO.findAll();
	// lambdaj
	 // usuarios que no soy yo ni mis contactos
	usuarios= filter(
			having(
					on(Usuario.class),
									
					 org.hamcrest.Matchers.not(	isIn(contactosyyo))
					)
			,todos);
}
	public void contactosSelectValueChange(){}
	public List searchCompleteUsuarios(String query) throws DAOException {
	System.out.println("Search Complete Usuarios");
	System.out.println("query:" + query); 
	System.out.println("usuarios:" + usuarios.size()); 
	if(query.length()>=3){
	        List<Usuario> u = filter(
	    			having(
	    					on(Usuario.class).getLogin(),
	    									
	    				startsWith(query)
	    					)
	    			,usuarios); 
	        System.out.println(contactos.size());
	      return u;
	        
	        } 
	 else{
			return new ArrayList();
		} 
	       
}
	public Usuario getUsuarioSeleccionado() {
	return usuarioSeleccionado;
}
	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) throws DAOException {
	System.out.println("usuarioSeleccionado");
	
	
	this.usuarioSeleccionado = usuarioSeleccionado;
	botonPeticionAmistadDisabled=peticionDAO.isPeticionAmistadYaSolicitada(usuarioSeleccionado, user);
}
	
	public List searchCompleteContactos(String query) throws DAOException {
	System.out.println("Search Complete Contactos");
	System.out.println("query:" + query); 
	System.out.println("usuarios:" + usuarios.size()); 
	if(query.length()>=3){
	        List<Usuario> u = filter(
	    			having(
	    					on(Usuario.class).getLogin(),
	    									
	    				startsWith(query)
	    					)
	    			,contactos); 
	        System.out.println(contactos.size());
	      return u;
	        
	        } 
	 else{
			return new ArrayList();
		} 
	       
}
	public void seleccionarContactoHandler(){
	
}
	public void seleccionarUsuarioHandler(){

}
	
	

	public boolean isBotonPeticionAmistadDisabled() {
		return botonPeticionAmistadDisabled;
	}
	public void setBotonPeticionAmistadDisabled(boolean botonPeticionAmistadDisabled) {
		this.botonPeticionAmistadDisabled = botonPeticionAmistadDisabled;
	}
	public void solicitarAmistadHandler() throws DAOException{
		 
		System.out.println("Quieres ser mi amigo");
	if(!peticionDAO.isPeticionAmistadYaSolicitada(user, usuarioSeleccionado)){
		peticionDAO.createPeticionAmistad(usuarioSeleccionado,user);
		 
		Util.facesMessage(Util.getLocaleString("friendship_request_sent"),Util.internationalizedStringTokens("#{msg.friendship_request_has_been_sent_to_the_user} " + usuarioSeleccionado.getLogin()+ "."));
		
	   
	      refresh();
	}
	else{
		Util.facesMessage(Util.getLocaleString("friendship_request_duplicated"), Util.getLocaleString("friendship_request_already_was_sent_to_the_user")  + usuarioSeleccionado.getLogin() +".");
		    
//	      context.addMessage(null, new FacesMessage("Peticion de amistad duplicada", "Ya se había enviado una peticion de amistad al usuario " + usuarioSeleccionado.getLogin() + " anteriormente." ));

		
	}
  
	
}
	public void eliminarContactoHandler() throws DAOException{
	System.out.println("Quieres ser mi enemigo");
	usuarioDAO.removeAmigo(user, contactoSeleccionado);
	Util.facesMessage(Util.getLocaleString("the_contact_was_removed"), " " + contactoSeleccionado.getLogin() +  Util.getLocaleString("and_you_are_no_longer_friends")  );

	refresh();
	  if (contactos.size()>0){
			contactoSeleccionado=contactos.get(0);
		    }
	  else{
		  contactoSeleccionado=null;
	  }
}



}
