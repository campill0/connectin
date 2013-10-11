package dao.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import beans.Categoria;
import beans.Discusion;
import beans.Grupo;
import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.Post;
import beans.Usuario;


import dao.DAOException;



public class Util {
	private enum Tipo {
		PERSIST, MERGE
	}
	
	public static Object persist(Object ob) throws DAOException {
		return persistGeneric(ob, Tipo.PERSIST);
	}

	public static void merge(Object ob) throws DAOException {
		persistGeneric(ob, Tipo.MERGE);
	}

	private static Object persistGeneric(Object ob, Tipo tipo)
			throws DAOException {
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			switch (tipo) {
			case MERGE:
				em.merge(ob); // Se guarda en la base de datos pero si ya existe se actualiza.
				break;

			case PERSIST:
				em.persist(ob); // Se guarda en la base de datos si ya existe se crea otro.
				break;
			default:
				break;
			}

			tx.commit();
		} catch (Exception e) {
			
			
			tx.rollback();
			em.close();
			
		//	throw new DAOException( 		"Hubo un problema en el update de " + ob.getClass().getCanonicalName());
		}
		em.close();
		return ob;
	}
	public  static void syncronize(){
		EntityManager em=JPADAOFactory.getEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
	}
	public static void updateQuery(String queryStr) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		
		Query query = em.createQuery(queryStr);
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		query.executeUpdate();
		
		try {
			
						tx.commit();
		} catch (Exception e) {
			
			
			tx.rollback();
			em.close();
			
			throw new DAOException( 		"Hubo un problema en el delete/update" );
		}
		em.close();
		
	}
	public static void resetTable(String table) throws DAOException {
		// TODO Auto-generated method stub
		
		updateQuery("DELETE FROM " + table + " table");
		
	}
	public static void resetDb() throws DAOException {
	EntityManager em = JPADAOFactory.getEntityManager();
	em.getTransaction().begin();
	// tu codigo aqui
	Query query = em.createQuery("SELECT g FROM Grupo g");
	List <Grupo> grupos = query.getResultList();
	
for (Grupo grupo : grupos) {
	grupo.setCategoria(null);
	grupo.getUsuarios().clear();
	grupo.getDiscusiones().clear();
	grupo.getPeticiones().clear();
	
}

query = em.createQuery("SELECT c FROM Categoria c");
List <Categoria> categorias = query.getResultList();
for (Categoria categoria : categorias) {
	categoria.setPadre(null);
	categoria.getSubcategoria().clear();
}

query = em.createQuery("SELECT d FROM Discusion d");
List <Discusion> discusiones = query.getResultList();
for (Discusion discusion : discusiones) {
	discusion.setGrupo(null);
	discusion.getPosts().clear();
	discusion.setAutor(null);
}

query = em.createQuery("SELECT p FROM Post p");
List <Post> posts = query.getResultList();
for (Post post : posts) {
	post.setAutor(null);
	post.setDiscusion(null);
}

 query = em.createQuery("SELECT u FROM Usuario u");
List <Usuario> usuarios = query.getResultList();

for (Usuario usuario : usuarios) {
usuario.getGrupos().clear();
usuario.getGruposAdministrados().clear();
usuario.getMensajesEnviados().clear();
usuario.getMensajesRecibidos().clear();
usuario.getNotificaciones().clear();
usuario.getAmigos().clear();
}


query = em.createQuery("SELECT p FROM Peticion p");
List <Peticion> peticiones = query.getResultList();

for (Peticion peticion : peticiones) {
peticion.setEstado(null);
peticion.setGrupo(null);
peticion.setPeticionario(null);
}



query = em.createQuery("SELECT n FROM Notificacion n");
List <Notificacion> notificaciones = query.getResultList();

for (Notificacion notificacion : notificaciones) {
notificacion.setMensaje(null);
notificacion.setPeticion(null);
notificacion.setTipo(null);

}
query = em.createQuery("SELECT m FROM Mensaje m");
List <Mensaje> mensajes = query.getResultList();

for (Mensaje mensaje : mensajes) {
mensaje.setEmisor(null);
mensaje.getReceptores().clear();
mensaje.getEstado().clear();
mensaje.setMensajeRespondido(null);

}
 

	em.getTransaction().commit();
	em.close();

	Util.resetTable("Categoria");
	Util.resetTable("Mensaje");
	Util.resetTable("Grupo");
	Util.resetTable("Discusion");
	Util.resetTable("Usuario");
	Util.resetTable("Peticion");
	Util.resetTable("Filtro");
	Util.resetTable("Notificacion");
	Util.resetTable("EstadoMensaje");
	Util.resetTable("Post");
}
	public static void resetDb2() throws DAOException {
		// TODO Auto-generated method stub
		 resetTable("Grupo");
		resetTable("Categoria");
		 resetTable("EstadoMensaje");
		 resetTable("Filtro");
	
		 resetTable("Mensaje");
		 resetTable("Notificacion");
		 resetTable("Peticion");
		 resetTable("Post");
		 resetTable("Usuario");
		
	
		// tu codigo aqui
		
		
		
	}
	
	
	public static Object refresh(Object ob){
		 
			Long id=0L;
			
	//			id = ob.getClass().getDeclaredField("id").getLong(ob);
				try {
					Method method = ob.getClass().getMethod("getId");
					id=(Long) method.invoke(ob);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			
			EntityManager em = JPADAOFactory.getEntityManager();
			
			ob=em.find(ob.getClass(), id);
			em.close();
			return ob;
	}
	public static Object refresh2(Object ob)
			throws DAOException {
		EntityManager em = JPADAOFactory.getEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			
				em.refresh(ob); // Se guarda en la base de datos pero si ya existe se actualiza.

			tx.commit();
		} catch (Exception e) {
			
			
			tx.rollback();
			if(em.isOpen()){		em.close();}
			
		//	throw new DAOException( 		"Hubo un problema en el update de " + ob.getClass().getCanonicalName());
		}
		if(em.isOpen()){		em.close();}
		return ob;
	}
	
	
}
