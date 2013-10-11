package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;



import dao.DAOException; 
import dao.GrupoDAO;
import dao.PostDAO;
import dao.UsuarioDAO;
import dao.exceptions.AddEntidadDuplicadaException;


import beans.Categoria;
import beans.Discusion;
import beans.EstadoPeticion;
import beans.Grupo;
import beans.Peticion;
import beans.Post;
import beans.Usuario;

public class JPAGrupoDAO implements GrupoDAO {

	@Override
	public Grupo createGrupo(String nombre, String descripcion,Usuario administrador, Categoria categoria)
			throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		
		
		Grupo g = new Grupo();
		g.setAdministrador(administrador);
		g.setCategoria(categoria);
		g.setDescripcion(descripcion);
		g.setNombre(nombre);
		//g.getUsuarios().add(administrador);
		
		
		Util.persist(g);
		
		return addUsuario(g,administrador);
		//return g;
		
	}
	@Override
	public void addPeticion(Grupo grupo, Peticion peticion) throws DAOException {
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Grupo g = em.find(Grupo.class, grupo.getId());
		g.getPeticiones().add(peticion);
		em.getTransaction().commit();
		em.close();
		
	}
	


	@Override
	public Grupo addUsuario(Grupo g, Usuario u) throws DAOException {
		
		if (g.getUsuarios().contains(u)){
			throw new AddEntidadDuplicadaException("the_group_includes_that_user");
		}
		else{
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Grupo g2 = em.find(Grupo.class, g.getId());
		Usuario u2 = em.find(Usuario.class, u.getId());
		g2.getUsuarios().add(u2);
		u2.getGrupos().add(g2);	
		u2.getGruposAdministrados().add(g2);
		em.getTransaction().commit();
		em.close();
		return g2;
		}
	
	}
	
	
	@Override
	public Grupo addUsuario(Grupo grupo,  Peticion peticion) throws DAOException {
		
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = em.find(Peticion.class, peticion.getId());
		
		p.setEstado(EstadoPeticion.ACEPTADO);
		em.getTransaction().commit();
		em.close();
		
		
		
		return this.addUsuario(grupo, peticion.getPeticionario());
	
	}
	
	
	@Override
	public void removeUsuario(Grupo grupo, Usuario usuario) throws DAOException {
		
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Grupo g = em.find(Grupo.class, grupo.getId());
		Usuario u = em.find(Usuario.class, usuario.getId());
		//Usuario u = em.find(Usuario.class, usuario.getId());
		
		if (g != null && u != null){
		
		Iterator<Usuario> itUsuarios = g.getUsuarios().iterator();
		while(itUsuarios.hasNext()){
			Usuario utmp = itUsuarios.next();
			if(utmp.getId().equals(usuario.getId())){
				itUsuarios.remove();
				break;
			}
		}
		
		Iterator<Grupo> itGrupos = u.getGrupos().iterator();
		while(itGrupos.hasNext()){
			Grupo gtmp = itGrupos.next();
			if(gtmp.getId().equals(grupo.getId())){
				itGrupos.remove();
				break;
			}
		}
		
		//g.getPeticiones().remove(u);
		//em.remove(u);
		em.getTransaction().commit();
		em.close();
		
		}
		
	}

	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Grupo> lista= new ArrayList<Grupo>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT g FROM Grupo g");
		List <Grupo> gl = query.getResultList();
	
		 return gl;
		
	}
	
	@Override
	public List<Discusion> findDiscusiones(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT g.discusiones FROM Grupo g WHERE g.id = :id");
		query.setParameter("id", id); 
		List <Discusion> dl = query.getResultList();
		return dl;
	}
	

	@Override
	public Grupo findGrupo(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Grupo g = em.find(Grupo.class, id);
		em.close();
		return g;
	}




	


	
	

}
