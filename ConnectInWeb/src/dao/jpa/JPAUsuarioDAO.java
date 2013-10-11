package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;



import dao.DAOException; 
import dao.DAOFactory;
import dao.GrupoDAO;
import dao.UsuarioDAO;
import dao.exceptions.LoginClaveIncorrectaException;
import dao.exceptions.LoginUsuarioNoExisteException;


import beans.Filtro;
import beans.Grupo;
import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.Post;
import beans.Usuario;

public class JPAUsuarioDAO implements UsuarioDAO {

	@Override
	public Usuario createUsuario(String login, String password, String nombre,
			String apellidos, Date fecha, String mail, Filtro filtro) throws DAOException {
		
	
	return createUsuario(login,password,nombre,apellidos,fecha,mail,filtro,null,"es");
		
	}
	
	@Override
	public Usuario createUsuario(String login, String password, String nombre,
			String apellidos, Date fecha, String mail, Filtro filtro,
			byte[] imagen, String locale) throws DAOException {
		// TODO Auto-generated method stub
		Usuario u = new Usuario();
		u.setImagen(imagen);  
		u.setLogin(login);
		u.setNombre(nombre);
		u.setPassword(password);
		u.setLocale(locale);
		u.setApellidos(apellidos);
		u.setMail(mail);
		u.setFechaNacimiento(fecha);
		u.setFiltro(filtro);
		Util.persist(u);
		return u;
	}
	@Override
	public void removeUsuario(Usuario usuario) throws DAOException {
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		GrupoDAO grupoDAO = df.getGrupoDAO();
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Usuario u = em.find(Usuario.class, usuario.getId());
		List<Grupo> grupos =u.getGrupos();
		for (Grupo grupo : grupos) {
			grupoDAO.removeUsuario(grupo, u);
		}
		
		em.remove(u);
		em.getTransaction().commit();
		em.close();
		
	}
	@Override
	public void removeAmigo(Usuario usuario,Usuario amigo) throws DAOException {
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Usuario u = em.find(Usuario.class, usuario.getId());
		Usuario a = em.find(Usuario.class, amigo.getId());
		
		Iterator<Usuario> itAmigos = u.getAmigos().iterator();
		while(itAmigos.hasNext()){
			Usuario utmp = itAmigos.next();
			if(utmp.getId().equals(amigo.getId())){
				itAmigos.remove();
				break;
			}
		}
		
		Iterator<Usuario> itAmigosDelAmigo = a.getAmigos().iterator();
		while(itAmigosDelAmigo.hasNext()){
			Usuario utmp = itAmigosDelAmigo.next();
			if(utmp.getId().equals(usuario.getId())){
				itAmigosDelAmigo.remove();
				break;
			}
		}
		
		
		em.getTransaction().commit();
		em.close();
		
	}
	@Override
	public void addAmigo(Usuario usuario,Usuario amigo) throws DAOException {
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Usuario u=em.find(Usuario.class,usuario.getId());
		Usuario a=em.find(Usuario.class,amigo.getId());
		u.getAmigos().add(a);
		a.getAmigos().add(u);
		em.getTransaction().commit();
		em.close();
		
	}
	
	@Override
	public void addNotificacion(Usuario usuario,Notificacion notificacion) throws DAOException {
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Usuario u=em.find(Usuario.class,usuario.getId());
		Notificacion n=em.find(Notificacion.class,notificacion.getId());
		u.getNotificaciones().add(n);
		
		em.getTransaction().commit();
		em.close();
		
	}
	


	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Usuario> lista= new ArrayList<Usuario>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT u FROM Usuario u");
		List <Usuario> ul = query.getResultList();
	
		 return ul;
		
	}

	@Override
	public void update(Usuario u) throws DAOException {
		// TODO Auto-generated method stub
	//	System.out.println("eee");
		Util.merge(u);
	}
	



	@Override
	public Usuario findUsuario(Long id) throws DAOException {
		EntityManager em=JPADAOFactory.getEntityManager();
		Usuario u = em.find(Usuario.class, id);
		em.close();
		return u;
	}
	@Override
	public Usuario login(String login, String password) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		// Mostramos el mismo mensaje para los dos errores para no dar pistas sobre si un usuario existe o no (Ocultamos la pertenencia de un usuario a un sitio, protegiendo así su privacidad)
		String mensaje_error = "the_user" + login + "not_exist_or_the_password_is_incorrect";
		
		Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
		query.setParameter("login", login); 
		
		try{
			Object result = query.getSingleResult();
			Usuario usuario = (Usuario)result;
			if (!usuario.getPassword().equals(password)){throw new LoginClaveIncorrectaException(mensaje_error);}
			em.close();
			return usuario;
		}
		catch(NoResultException e){
			em.close();
			throw new LoginUsuarioNoExisteException(mensaje_error);
		}
		
	

	
		
	}
	

	@Override
	public Usuario findUsuario(String login) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
		query.setParameter("login", login); 
		Object result = query.getSingleResult();
		Usuario usuario = (Usuario)result;
		
		return usuario;
	}




	

	
	

}
