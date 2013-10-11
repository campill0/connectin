package dao.jpa;

import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mirmi.GestorMails;


import dao.DAOException; 
import dao.DAOFactory;
import dao.NotificacionDAO;
import dao.UsuarioDAO;


import beans.Categoria;
import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.TipoNotificacion;
import beans.Usuario;

public class JPANotificacionDAO implements NotificacionDAO {



	



	@Override
	public Notificacion createNotificacion(String descripcion, Date fecha, TipoNotificacion tipo,Usuario receptorNotificacion, Peticion peticion, Mensaje mensaje) throws DAOException {
		// TODO Auto-generated method stub
		Notificacion n = new Notificacion();
		n.setDescripcion(descripcion);
		n.setFecha(fecha);
		n.setTipo(tipo);
		n.setLeida(false);
		n.setPeticion(peticion);
		n.setMensaje(mensaje);
		
		Util.persist(n);
	DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
	UsuarioDAO usuarioDAO = df.getUsuarioDAO();
	//Usuario peticionario = peticion.getPeticionario();
	usuarioDAO.addNotificacion(receptorNotificacion, n);
	return n;
	}









	
	

	@Override
	public Notificacion findNotificacion(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Notificacion n=em.find(Notificacion.class,id);
	
		
		
		
		
		return n;
	}

	@Override
	public void update(Notificacion p) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(p);
	}
	
	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Notificacion> lista= new ArrayList<Notificacion>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT n FROM Notificacion n");
		List <Notificacion> nl = query.getResultList();
	
		 return nl;
		
	}


	@Override
	public List findAll(Usuario usuario) throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Notificacion> lista= new ArrayList<Notificacion>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT n FROM Notificacion n WHERE c.peticion IN :p");
		List<Peticion> peticiones = usuario.getPeticiones();
		query.setParameter("p", peticiones); 
		Object result = query.getSingleResult();
		Categoria categoria = (Categoria)result;
		List <Notificacion> nl = query.getResultList();
	
		 return nl;
		
	}
	

}
