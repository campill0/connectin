package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;



import dao.DAOException; 
import dao.PostDAO;
import dao.UsuarioDAO;


import beans.Post;
import beans.Usuario;

public class JPAPostDAO implements PostDAO {



	




	@Override
	public Post findPost(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT p FROM Post p WHERE p.id = :id");
		query.setParameter("id", id); 
		Object result = query.getSingleResult();
		Post post = (Post)result;
		
		
		
		return post;
	}

	@Override
	public void update(Post p) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(p);
	}
	
	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Post> lista= new ArrayList<Post>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT p FROM Post p");
		List <Post> pl = query.getResultList();
	
		 return pl;
		
	}


	
	

}
