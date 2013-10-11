package controller.backingbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import beans.Categoria;

import dao.CategoriaDAO;
import dao.DAOException;
import dao.DAOFactory;
import dao.exceptions.ChangePadreCategoriaException;
import dao.jpa.Util;
@ManagedBean(name="auxiliaryBean")
@ViewScoped
public class AuxiliaryBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1982711027519470364L;
	private static DAOFactory df;
	private static CategoriaDAO categoriaDAO;
	Map<Long,TreeNode> mapCategorias;
	  private TreeNode root;
	  private TreeNode rootPadre;
	  private List<Categoria> categoriasTodas;
	  private List<Categoria> categoriasPadre;
	  private boolean raiz;
	

		public boolean isRaiz() {
		return raiz;
	}

	public void setRaiz(boolean raiz) {
		this.raiz = raiz;
	}

		public List<Categoria> getCategoriasPadre() {
		return categoriasPadre;
	}

	public void setCategoriasPadre(List<Categoria> categoriasPadre) {
		this.categoriasPadre = categoriasPadre;
	}

		public List<Categoria> getCategoriasTodas() {
		return categoriasTodas;
	}

	public void setCategoriasTodas(List<Categoria> categoriasTodas) {
		this.categoriasTodas = categoriasTodas;
	}

		private TreeNode selectedNode;  
		private TreeNode selectedNodePadre;  
	  private Categoria selectedCategoria;
	  private Categoria selectedCategoriaPadre;
	  public Categoria getSelectedCategoriaPadre() {
		return selectedCategoriaPadre;
	}

	public void setSelectedCategoriaPadre(Categoria selectedCategoriaPadre) {
		this.selectedCategoriaPadre = selectedCategoriaPadre;
	}

	private String selectedCategoriaNombre;
	
	
	public String getSelectedCategoriaNombre() {
		return selectedCategoriaNombre;
	}

	public void setSelectedCategoriaNombre(String selectedCategoriaNombre) {
		this.selectedCategoriaNombre = selectedCategoriaNombre;
	}

	private String newCategoria;
	  
	 

	    public String getNewCategoria() {
		return newCategoria;
	}

	public void setNewCategoria(String newCategoria) {
		this.newCategoria = newCategoria;
	}

		public Categoria getSelectedCategoria() {
		return selectedCategoria;
	}

	public void setSelectedCategoria(Categoria selectedCategoria) {
		this.selectedCategoria = selectedCategoria;
	}
	
	public void addCategoria() throws DAOException{
		df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		categoriaDAO= df.getCategoriaDAO();
		
		
		categoriaDAO.createCategoria(newCategoria, new ArrayList<Categoria>());
		System.out.println("addCategoria");
		System.out.println("antes");
		cargarCategorias();
		System.out.println("despues");
    }

		public void saveCategoria() throws DAOException{
			df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
			categoriaDAO= df.getCategoriaDAO();
			try{
				if(raiz){
					categoriaDAO.changePadre(selectedCategoria, null);
				}
				else
				{
					categoriaDAO.changePadre(selectedCategoria, selectedCategoriaPadre);
				}
			}
			catch(ChangePadreCategoriaException e){
				
				
				controller.Util.facesMessage(controller.Util.getLocaleString("selected_parent_error"), controller.Util.getLocaleString(e.getMessage()));
			}
			selectedCategoria = categoriaDAO.findCategoria(selectedCategoria.getId());
			categoriaDAO.changeNombre(selectedCategoria, selectedCategoriaNombre);
			cargarCategorias();
			
			 
	    }
	
		public void expand(TreeNode tn){
			tn.setExpanded(true);
			List<TreeNode> childrens = tn.getChildren();
			for (TreeNode treeNode : childrens) {
				expand(treeNode);
				
			}
			
		}
		public TreeNode getRoot() {  
	        return root;  
	    }  
	  
	    public TreeNode getSelectedNode() {  
	        return selectedNode;  
	    }  
	  
	    public void setSelectedNode(TreeNode selectedNode) {  
	        this.selectedNode = selectedNode;  
	    }  
	    public void onNodeSelect(NodeSelectEvent event) {  
	    	selectedCategoria=  (Categoria) event.getTreeNode().getData();
	    	selectedCategoriaNombre = selectedCategoria.getNombre();
	    	selectedNodePadre = rootPadre.getChildren().get(0);
	    	selectedCategoriaPadre=selectedCategoria.getPadre();
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, controller.Util.getLocaleString("selected"), event.getTreeNode().toString());  
	  
	        FacesContext.getCurrentInstance().addMessage(null, message);  
	    } 
	    
	    public void onNodeSelectRoot(NodeSelectEvent event) {  
	    	selectedCategoriaPadre=  (Categoria) event.getTreeNode().getData();
	    	
	    	
		
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, controller.Util.getLocaleString("selected"), event.getTreeNode().toString());  
	  
	        FacesContext.getCurrentInstance().addMessage(null, message);  
	    }
	    /*
	    public void onNodeSelectRoot(NodeSelectEvent event) {  
	    	selectedCategoriaPadre=  (Categoria) event.getTreeNode().getData();
	    	selectedCategoriaPadreNombre = selectedCategoria.getNombre();
	    	
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());  
	  
	        FacesContext.getCurrentInstance().addMessage(null, message);  
	    }
	    */
	    public void displaySelectedSingle() {  
	     System.out.println(selectedNode);
	     System.out.println("hola");
	    	if(selectedNode != null) {  
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, controller.Util.getLocaleString("selected"), selectedNode.getData().toString());  
	  
	            FacesContext.getCurrentInstance().addMessage(null, message);  
	        }  
	    }  
	    
	    public void displaySelectedSinglePadre() {  
		     System.out.println(selectedNode);
		     System.out.println("hola");
		    	if(selectedNode != null) {  
		            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, controller.Util.getLocaleString("selected"), selectedNode.getData().toString());  
		  
		            FacesContext.getCurrentInstance().addMessage(null, message);  
		        }  
		    } 
	  
	    public void deleteNode() throws DAOException {  
	    	System.out.println("delete");
	      /*  selectedNode.getChildren().clear();  
	        selectedNode.getParent().getChildren().remove(selectedNode);  
	        selectedNode.setParent(null);  
	          
	        selectedNode = null;*/  
	    	
	    	
	    	
	    }  
	    
	    public void editNode() {  
	    	System.out.println("edit");
	       /*selectedNode.getChildren().clear();  
	        selectedNode.getParent().getChildren().remove(selectedNode);  
	        selectedNode.setParent(null);  
	        selectedNode = null;*/  
	    }  
	    
	    
	public TreeNode getSelectedNodePadre() {
			return selectedNodePadre;
		}

		public void setSelectedNodePadre(TreeNode selectedNodePadre) {
			this.selectedNodePadre = selectedNodePadre;
		}

	public AuxiliaryBean() throws DAOException{
		
		
		System.out.println("CONSTRUCTOR");
		cargarCategorias();
	}
	public void refresh() throws DAOException{
		System.out.println("refresh");
		cargarCategorias();
	}
	public void cargarCategorias() throws DAOException{
		mapCategorias= new HashMap<Long,TreeNode>();
		
		if(root!=null){		 root.getChildren().clear();		 }
		else{			   root = new DefaultTreeNode("Root", null);		 }
		if(rootPadre!=null){ 			 rootPadre.getChildren().clear();			 }
		else{				 rootPadre = new DefaultTreeNode("Root", null);			 }
		 
		
		df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		categoriaDAO= df.getCategoriaDAO();
		categoriaDAO.synchronize();
		List<Categoria> categorias = categoriaDAO.getCategoriasPrincipales();
		categoriasTodas = categoriaDAO.findAll();
		categoriasPadre = categoriaDAO.findAll();
		if(categoriasTodas.size()>0){
		selectedCategoria = categoriasTodas.get(0);
		selectedCategoriaPadre = categoriasPadre.get(0);
		}
		else{
			selectedCategoria=null;
			selectedCategoriaPadre=null;
		}
		fillTree(categorias,root);
		fillTree(categorias,rootPadre);
		expand(root);
		expand(rootPadre);
	}
	
	public TreeNode getRootPadre() {
		return rootPadre;
	}

	public void setRootPadre(TreeNode rootPadre) {
		this.rootPadre = rootPadre;
	}

	public void fillTree(List<Categoria> categorias, TreeNode padre) throws DAOException{
	
		for (Categoria cat : categorias) {
			// caso base
		if(cat.getPadre()== null){
				TreeNode node=  new DefaultTreeNode(cat, padre); 
				mapCategorias.put(cat.getId(),node);
			}
			
			else{
		/*		System.out.println("##################################");
				System.out.println("#######" + cat.getNombre()+"########" + cat.getPadre() + "###################");
				System.out.println("##################################");
			*/	TreeNode parentNode = mapCategorias.get(cat.getPadre().getId());
			
				TreeNode node=  new DefaultTreeNode(cat, parentNode);
				mapCategorias.put(cat.getId(),node);
				
			}
	
			List<Categoria> hijos= cat.getSubcategoria();
			if (hijos.size()>0)
			{
				fillTree(hijos,padre);
			}
			
		}
		
		
	}
	
	
}
