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
import dao.GrupoDAO;
import dao.NotificacionDAO;
import dao.PeticionDAO;
import dao.UsuarioDAO;

import beans.Categoria;
import beans.EstadoPeticion;
import beans.Grupo;
import beans.Notificacion;
import beans.Peticion;
import beans.Usuario;

@ManagedBean(name ="gruposBean")
@ViewScoped
public class GruposBean implements Serializable {
	private String nuevoGrupoNombre;
	private String nuevoGrupoDescripcion;
	private Long nuevoGrupoCategoria;
	private List<Categoria> categorias;
	private Usuario user;
	private static DAOFactory df;
	private static UsuarioDAO usuarioDAO;
	private static CategoriaDAO categoriaDAO;
	private static PeticionDAO peticionDAO;
	private static GrupoDAO grupoDAO;
	
	private List<Grupo> grupos;
	private List<Grupo> gruposFiltered;
	public List<Grupo> getGruposFiltered() {
		return gruposFiltered;
	}
	public void setGruposFiltered(List<Grupo> gruposFiltered) {
		this.gruposFiltered = gruposFiltered;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	public String urlGroup(Long id){
		return "groupdetail";
		//System.out.println("groupdetail?groupId=" +id.toString());
		//return  "groupdetail?groupId=" +id.toString();
	}
	
	public GruposBean() throws DAOException {
		// TODO Auto-generated constructor stub
		df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO = df.getUsuarioDAO();
		categoriaDAO = df.getCategoriaDAO();
		peticionDAO = df.getPeticionDAO();
		grupoDAO = df.getGrupoDAO();
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		user = (Usuario) req.getSession().getAttribute("loggedUser");
        user=usuarioDAO.findUsuario(user.getId());
        req.getSession().setAttribute("loggedUser", user);
        grupos=grupoDAO.findAll();
        categorias = categoriaDAO.findAll();
        
	}
	public void selectCategoriaValueChangeHandler(){
		System.out.println("cambiado");
	}
	public void resetCrearGrupo(){
		nuevoGrupoNombre="";
		nuevoGrupoDescripcion="";
		nuevoGrupoCategoria=categorias.get(0).getId();
	}
	
	public boolean admin(Grupo g){
		return g.isAdministradorGrupo(user);
	}
	public void crearGrupo() throws DAOException{
		categoriaDAO = df.getCategoriaDAO();
		Categoria cat = categoriaDAO.findCategoria(nuevoGrupoCategoria);
	
		grupoDAO.createGrupo(nuevoGrupoNombre, nuevoGrupoDescripcion, user, cat);
		
		grupos=grupoDAO.findAll();
		nuevoGrupoCategoria = categorias.get(0).getId();
		nuevoGrupoDescripcion = "";
		nuevoGrupoNombre = "";
		
	}
	public void join(Grupo g) throws DAOException{
		peticionDAO.createPeticionGrupo(new Date(), EstadoPeticion.SOLICITADO, g, user);
		System.out.println(g.getNombre());
	}
	public void unjoin(Grupo g) throws DAOException{
		grupoDAO.removeUsuario(g, user);
		user = usuarioDAO.findUsuario(user.getId());
		grupos = grupoDAO.findAll();
		System.out.println(g.getNombre());
	}
	public boolean  renderJoinButton(Grupo g) throws DAOException{
		Grupo grupo =grupoDAO.findGrupo(g.getId());
		List<Peticion> peticiones = grupo.getPeticiones();
		// si tiene una solicitud pendiente no se muestra
		for (Peticion p : peticiones) {
			if (p.getPeticionario().equals(user) && p.isSolicitada())
			{
				return false;
			}
		}
		return !grupo.getUsuarios().contains(user);
	}
	
	public String getNuevoGrupoNombre() {
		return nuevoGrupoNombre;
	}
	public void setNuevoGrupoNombre(String nuevoGrupoNombre) {
		this.nuevoGrupoNombre = nuevoGrupoNombre;
	}
	public String getNuevoGrupoDescripcion() {
		return nuevoGrupoDescripcion;
	}
	public void setNuevoGrupoDescripcion(String nuevoGrupoDescripcion) {
		this.nuevoGrupoDescripcion = nuevoGrupoDescripcion;
	}
	public Long getNuevoGrupoCategoria() {
		return nuevoGrupoCategoria;
	}
	public void setNuevoGrupoCategoria(Long nuevoGrupoCategoria) {
		this.nuevoGrupoCategoria = nuevoGrupoCategoria;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}

}
