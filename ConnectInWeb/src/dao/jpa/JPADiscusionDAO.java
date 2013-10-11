package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;




import dao.DAOException; 
import dao.DiscusionDAO;
import dao.PostDAO;
import dao.UsuarioDAO;


import beans.Categoria;
import beans.Discusion;
import beans.Grupo;
import beans.Post;
import beans.Usuario;

public class JPADiscusionDAO implements DiscusionDAO {

	@Override
	public Discusion createDiscusion(String texto, String titulo, Grupo grupo,
			Date fechaDeCreacion, Usuario autor) throws DAOException {
		// TODO Auto-generated method stub
		
		Discusion d=new Discusion();
		d.setAutor(autor);
		d.setFechaCreacion(fechaDeCreacion);
		d.setGrupo(grupo);
		d.setTexto(texto);
		d.setTituloDiscusion(titulo);
		Util.persist(d);
		return d;
	}
	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		ArrayList<Discusion> lista= new ArrayList<Discusion>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT d FROM Discusion d");
		List <Discusion> dl = query.getResultList();
	
		 return dl;
	}
	
	@Override
	public Discusion findDiscusion(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT d FROM Discusion d WHERE d.id = :id");
		query.setParameter("id", id); 
		Object result = query.getSingleResult();
		Discusion discusion = (Discusion)result;
		
		
		
		return discusion;
	}
	@Override
	public Post createPost(Discusion discusion,String texto, Date fecha, Usuario autor) throws DAOException {
		// TODO Auto-generated method stub
		Post p = new Post();
		p.setTexto(texto);
		p.setFecha(fecha);
		p.setAutor(autor);
		p.setDiscusion(discusion);
		//Util.persist(p);
		
		
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		//em.persist(p);
		Discusion d = em.find(Discusion.class, discusion.getId());
		d.getPosts().add(p);
		// tu codigo aqui
		em.getTransaction().commit();
		em.close();
		
		return p;
	}
	






	



	
	

}
