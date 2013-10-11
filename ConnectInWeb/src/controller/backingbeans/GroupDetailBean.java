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

@ManagedBean(name ="groupDetailBean")
@ViewScoped
public class GroupDetailBean implements Serializable {
	private Grupo group;



	public Grupo getGroup() {
		return group;
	}


	public void setGroup(Grupo group) {
		this.group = group;
	}


	private Usuario user;
	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static CategoriaDAO categoriaDAO;
	private static GrupoDAO grupoDAO;
	private static DiscusionDAO discusionDAO;
	private List<Discusion> discusiones;



	public List<Discusion> getDiscusiones() {
		return discusiones;
	}


	public void setDiscusiones(List<Discusion> discusiones) {
		this.discusiones = discusiones;
	}


	private String nuevaDiscusionTitulo;
	private String nuevaDiscusionTexto;
	
	public String getNuevaDiscusionTitulo() {
		return nuevaDiscusionTitulo;
	}


	public void setNuevaDiscusionTitulo(String nuevaDiscusionTitulo) {
		this.nuevaDiscusionTitulo = nuevaDiscusionTitulo;
	}


	public String getNuevaDiscusionTexto() {
		return nuevaDiscusionTexto;
	}


	public void setNuevaDiscusionTexto(String nuevaDiscusionTexto) {
		this.nuevaDiscusionTexto = nuevaDiscusionTexto;
	}
public void nuevaDiscusion() throws DAOException{
	discusionDAO = df.getDiscusionDAO();
	discusionDAO.createDiscusion(nuevaDiscusionTexto, nuevaDiscusionTitulo,group , new Date(), usuarioDAO.findUsuario(user.getId()));
	
	discusiones=grupoDAO.findDiscusiones(group.getId());
	
	nuevaDiscusionTexto="";
	nuevaDiscusionTitulo="";
	
}


public boolean isMember(){
	return group.getUsuarios().contains(user);
}
	public GroupDetailBean() throws DAOException {
		// TODO Auto-generated constructor stub
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
		categoriaDAO = df.getCategoriaDAO();
		grupoDAO = df.getGrupoDAO();
		discusionDAO = df.getDiscusionDAO();
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		user = (Usuario) req.getSession().getAttribute("loggedUser");
		Long groupId = Long.parseLong(req.getParameter("groupId"));
		group= grupoDAO.findGrupo(groupId);
		discusiones=grupoDAO.findDiscusiones(group.getId());
        user=usuarioDAO.findUsuario(user.getId());
        req.getSession().setAttribute("loggedUser", user);
    
      //  grupos=grupoDAO.findAll();
      //  categorias = categoriaDAO.findAll();
        
	}



}
