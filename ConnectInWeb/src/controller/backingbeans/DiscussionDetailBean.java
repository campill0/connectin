package controller.backingbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import dao.CategoriaDAO;
import dao.DAOException;
import dao.DAOFactory;
import dao.DiscusionDAO;
import dao.GrupoDAO;
import dao.NotificacionDAO;
import dao.PeticionDAO;
import dao.UsuarioDAO;

import beans.Categoria;
import beans.Discusion;
import beans.Grupo;
import beans.Notificacion;
import beans.Usuario;

@ManagedBean(name ="discussionDetailBean")
@ViewScoped
public class DiscussionDetailBean implements Serializable {

	private Discusion discussion;

	private Usuario user;
	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static CategoriaDAO categoriaDAO;
	private static GrupoDAO grupoDAO;
	private static DiscusionDAO discusionDAO;
	private String nuevoPostTexto;
	public void nuevoPost() throws DAOException{
		discusionDAO.createPost(discussion, nuevoPostTexto, new Date(), user);
		discussion = discusionDAO.findDiscusion(discussion.getId());
		nuevoPostTexto="";
	}
	public String getNuevoPostTexto() {
		return nuevoPostTexto;
	}
	public void setNuevoPostTexto(String nuevoPostTexto) {
		this.nuevoPostTexto = nuevoPostTexto;
	}
	
	public boolean isMember(){
		
		return discussion.getGrupo().getUsuarios().contains(user);
	}
	public DiscussionDetailBean() throws DAOException {
		// TODO Auto-generated constructor stub
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
		categoriaDAO = df.getCategoriaDAO();
		grupoDAO = df.getGrupoDAO();
		discusionDAO = df.getDiscusionDAO();
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		user = (Usuario) req.getSession().getAttribute("loggedUser");
		user=usuarioDAO.findUsuario(user.getId());
	    req.getSession().setAttribute("loggedUser", user);
		Long discussionId = Long.parseLong(req.getParameter("discussionId"));
		discussion = discusionDAO.findDiscusion(discussionId);
      
    
      //  grupos=grupoDAO.findAll();
      //  categorias = categoriaDAO.findAll();
        
	}

	public Discusion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discusion discussion) {
		this.discussion = discussion;
	}



}
