package controller.backingbeans;

import java.io.Serializable;  

import java.util.ArrayList;  
import java.util.Arrays;
import java.util.Date;
import java.util.List;  
import java.util.Random;
import java.util.UUID;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import beans.EstadoPeticion;
import beans.Filtro;
import beans.Grupo;
import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.TipoNotificacion;
import beans.Usuario;
  

@ManagedBean(name = "loggedUserBean")  
@SessionScoped
public class LoggedUserBean implements Serializable  {  
    private Usuario user;
    private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;

	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
    


	public LoggedUserBean() throws DAOException { 
		System.out.println("constructor loggedUserBean");
		cargarDatos();
    }
	
	public void cargarDatos()throws DAOException{
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
        user = (Usuario) req.getSession().getAttribute("loggedUser");
        user=usuarioDAO.findUsuario(user.getId());
        req.getSession().setAttribute("loggedUser", user);

	}
	
	public Integer getNumeroMensajesSinLeer()throws DAOException{
		
		cargarDatos();
		return user.getNumeroMensajesSinLeer();
	}
	
	
      
  
}  