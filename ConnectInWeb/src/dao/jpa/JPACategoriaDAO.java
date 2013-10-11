package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;



import dao.CategoriaDAO;
import dao.DAOException; 
import dao.CategoriaDAO;
import dao.DAOFactory;
import dao.exceptions.ChangePadreCategoriaException;
import dao.exceptions.RemoveEntidadUsuadaException;


import beans.Categoria;
import beans.Categoria;
import beans.Grupo;
import beans.Mensaje;
import beans.Post;

public class JPACategoriaDAO implements CategoriaDAO {

	@Override
	public Categoria createCategoria(String nombre,	List<Categoria> subcategorias) throws DAOException {
		// TODO Auto-generated method stub

		
		//c.setSubcategoria(subcategorias);
		
return createCategoria(nombre, subcategorias,null);
	}

	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		ArrayList<Categoria> lista= new ArrayList<Categoria>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT c FROM Categoria c");
		List <Categoria> cl = query.getResultList();
	
		 return cl;
	}
	@Override
	public List<Categoria> getCategoriasPrincipales() {
		List<Categoria> cat = null;
		EntityManager em = JPADAOFactory.getEntityManager();
		Query q = em.createNamedQuery("findCategoriasPrincipales");
		cat = q.getResultList();
		em.close();
		return cat;
	}

	@Override
	public Categoria findCategoria(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT c FROM Categoria c WHERE c.id = :id");
		query.setParameter("id", id); 
		Object result = query.getSingleResult();
		Categoria categoria = (Categoria)result;
		
		
		
		return categoria;
	}

	@Override
	public void update(Categoria c) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(c);
	}

	@Override
	public Categoria createCategoria(String nombre) throws DAOException {
		// TODO Auto-generated method stub
		return createCategoria(nombre, new ArrayList<Categoria>());
	}

	@Override
	public List<Grupo> getGrupos(Categoria c){
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Categoria cat= em.find(Categoria.class, c.getId());
		Query query = em.createQuery("SELECT g FROM Grupo g WHERE g.categoria = :cat");
		query.setParameter("cat", cat); 
		List <Grupo> grupos = query.getResultList();
		em.getTransaction().commit();
		em.close();
		return grupos;
	}
	@Override
	public void remove(Categoria c) throws RemoveEntidadUsuadaException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Categoria cat = em.find(Categoria.class, c.getId());
		
		// No borrar categorias que esten asociadas a una discusión o grupo
		List <Categoria> descendientes=cat.getDescendientes();
		descendientes.add(c);
		Query query;
		for (Categoria categoria : descendientes) {
			
			List <Grupo> grupos = getGrupos(categoria);
			int count=grupos.size();
			if(count>0){throw new RemoveEntidadUsuadaException("you_can_not_delete_a_category_that_is_being_used");}
		//	System.out.println(count);
			
		}
	
		
		em.remove(cat);
		// tu codigo aqui

		em.getTransaction().commit();
		em.close();
		
	}

	@Override
	public Categoria createCategoria(String nombre,
			List<Categoria> subcategorias, Categoria padre) throws DAOException {
		// TODO Auto-generated method stub
		
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Categoria c = new Categoria();
		c.setNombre(nombre);
		c.setPadre(padre);
		c.setSubcategoria(subcategorias);
		em.persist(c);
		
		List<Categoria> hijos= c.getSubcategoria();
		for (Categoria categoria : hijos) {
			Categoria cat=em.find(Categoria.class, categoria.getId());
			cat.setPadre(c);
			em.persist(cat);
		}
		
		em.getTransaction().commit();
		em.close();
		
		return c;
		
		
	}

	@Override
	public void changePadre(Categoria c1, Categoria c2) throws DAOException {
		if(c1 == null){
			throw new ChangePadreCategoriaException("source_category_null_in_change_parent_operation");
			
		}
		else if(c1.equals(c2)){
			throw new ChangePadreCategoriaException("category_can_not_be_a_parent_of_itself");
		
		}
		else if(c1.isDescendiente(c2))
		{
			throw new ChangePadreCategoriaException("category_can_not_be_a_parent_of_his_parent");
			
		}
		
		
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Categoria cat = em.find(Categoria.class, c1.getId());
		Categoria p;
		if(cat.getPadre()!=null)
		{
			p=em.find(Categoria.class, cat.getPadre().getId());
			p.getDescendientes().remove(cat);
			cat.setPadre(null);
		}
	
		Categoria catPadre=null;
		if(c2!=null){
			catPadre= em.find(Categoria.class, c2.getId());
			cat.setPadre(catPadre);
			List<Categoria> hijos = catPadre.getDescendientes();
			hijos.add(cat);
			catPadre.setSubcategoria(hijos);
		}
		em.getTransaction().commit();
	
		em.close();
		
		Util.syncronize();
	}

	@Override
	public void changeNombre(Categoria c, String nombre)
			throws ChangePadreCategoriaException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Categoria cat = em.find(Categoria.class, c.getId());
		cat.setNombre(nombre);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void synchronize() {
		// TODO Auto-generated method stub
	Util.syncronize();	
	}

	

}
