package controller.backingbeans;

import interfaces.CategoriaLocal;
import interfaces.CategoriaLocalHome;
import interfaces.CategoriaVO;
import interfaces.GrupoLocal;
import interfaces.GrupoLocalHome;
import interfaces.GrupoVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@ManagedBean(name = "ejbCmp2Bean")
@ViewScoped
public class EjbCmp2Bean implements Serializable{
	private String nuevoGrupoNombre;
	private String nuevoGrupoDescripcion;
	private String nuevoGrupoAdministrador;
	private String nuevoGrupoCategoria;
	private String nuevaCategoriaNombre;
	private List<GrupoLocal> grupos;
	private List<CategoriaLocal> categorias;
	
	
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

	public String getNuevoGrupoAdministrador() {
		return nuevoGrupoAdministrador;
	}

	public void setNuevoGrupoAdministrador(String nuevoGrupoAdministrador) {
		this.nuevoGrupoAdministrador = nuevoGrupoAdministrador;
	}

	public String getNuevoGrupoCategoria() {
		return nuevoGrupoCategoria;
	}

	public void setNuevoGrupoCategoria(String nuevoGrupoCategoria) {
		this.nuevoGrupoCategoria = nuevoGrupoCategoria;
	}

	public String getNuevaCategoriaNombre() {
		return nuevaCategoriaNombre;
	}

	public void setNuevaCategoriaNombre(String nuevaCategoriaNombre) {
		this.nuevaCategoriaNombre = nuevaCategoriaNombre;
	}


	public EjbCmp2Bean(){
		 try {
			 
			InitialContext contexto;
			CategoriaLocalHome categoriaHome; 
			GrupoLocalHome grupoHome;    
			contexto = new 	InitialContext();
			grupoHome = (GrupoLocalHome)contexto.lookup("local/GrupoCMP2");
			categoriaHome= (CategoriaLocalHome)contexto.lookup("local/CategoriaCMP2"); 
			categorias= new ArrayList<CategoriaLocal>();
			grupos= new ArrayList<GrupoLocal>();
			categorias.addAll(categoriaHome.findAll());
		    grupos.addAll(grupoHome.findAll());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	


	public List<GrupoLocal> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoLocal> grupos) {
		this.grupos = grupos;
	}

	public List<CategoriaLocal> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaLocal> categorias) {
		this.categorias = categorias;
	}

	public void addCategoria() throws CreateException, FinderException, NamingException{
	InitialContext contexto= new 	InitialContext();
	CategoriaLocalHome categoriaHome =	(CategoriaLocalHome)contexto.lookup("local/CategoriaCMP2"); 
		   
	 categoriaHome.create(nuevaCategoriaNombre);
	 categorias.clear();
	 categorias.addAll(categoriaHome.findAll());
	 nuevaCategoriaNombre="";
	}
	
	public void addGrupo() throws CreateException, FinderException, NamingException{
		   InitialContext 	contexto = new 	InitialContext();
		   GrupoLocalHome grupoHome = (GrupoLocalHome)contexto.lookup("local/GrupoCMP2");
		 grupoHome.create(nuevoGrupoNombre, nuevoGrupoDescripcion, nuevoGrupoCategoria, nuevoGrupoAdministrador);
		 grupos.clear();
		 grupos.addAll(grupoHome.findAll());
		 nuevoGrupoNombre="";
		 nuevoGrupoDescripcion="";
		 nuevoGrupoCategoria="";
		 nuevoGrupoAdministrador="";
		 
		}
}
