package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;




import dao.DAOException; 
import dao.DAOFactory;
import dao.MensajeDAO;
import dao.NotificacionDAO;
import dao.UsuarioDAO;


import beans.EstadoMensaje;
import beans.Mensaje;
import beans.TipoNotificacion;

import beans.Usuario;

public class JPAMensajeDAO implements MensajeDAO {



	




	
	public  Mensaje createMensaje(Mensaje mensajeRespondido,boolean leido, String texto, Date fecha, String asunto, Usuario emisor, List<Usuario> receptores) throws DAOException {
	
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		
		Mensaje m = new Mensaje();
		m.setMensajeRespondido(mensajeRespondido);
		m.setAsunto(asunto);
		m.setFecha(fecha);
		m.setTexto(texto);
		m.setEmisor(emisor);
		m.setReceptores(receptores);
		
		m.setEstado(new ArrayList<EstadoMensaje>());
		for(int i=0 ; i<receptores.size();i++){
			m.getEstado().add(new EstadoMensaje(EstadoMensaje.noLeido));
		}
		
		
		em.persist(m);
		Usuario u=em.find(Usuario.class,emisor.getId());
		u.getMensajesEnviados().add(m);
		em.persist(u);
		
		
		for (Usuario receptor : receptores) {
			u=em.find(Usuario.class,receptor.getId());
			u.getMensajesRecibidos().add(m);
			em.persist(u);
		}
		em.getTransaction().commit();
		em.close();
		for (Usuario receptor : receptores) {
				
				notificacionDAO.createNotificacion("#{msg.new_message_from} " + m.getEmisor().getLogin(), new Date(), TipoNotificacion.NOTIFICACION_MENSAJE, receptor, null, m);
				rmi.Util.sendRmiMessage("Nuevo mensaje de " + m.getEmisor(),receptor.getMail());
			}
		return m;
	}

	@Override
	public Mensaje findMensaje(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT m FROM Mensaje m WHERE m.id = :id");
		query.setParameter("id", id); 
		Object result = query.getSingleResult();
		Mensaje mensaje = (Mensaje)result;
	
		return mensaje;
	}

	@Override
	public void update(Mensaje m) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(m);
	}
	
	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Mensaje> lista= new ArrayList<Mensaje>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT m FROM Mensaje m");
		List <Mensaje> ml = query.getResultList();
	
		 return ml;
		
	}
	
	@Override
	public Mensaje setEstadoMensaje(Mensaje m, Usuario u, Integer estado) throws DAOException {
		
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		
		Mensaje men = em.find(Mensaje.class, m.getId());
		men.getEstado().get(men.getReceptores().indexOf(u)).setEstado(estado);
		em.getTransaction().commit();
		em.close();
		
		
		return men;
		
	}
	
	@Override
	public Integer getEstadoMensaje(Mensaje m, Usuario u) throws DAOException {
		
		EntityManager em = JPADAOFactory.getEntityManager();
		Mensaje men = em.find(Mensaje.class, m.getId());
		List<Usuario> receptores = men.getReceptores();
		int indice = receptores.indexOf(u);
		 List<EstadoMensaje> estados = men.getEstado();
		 EstadoMensaje estado= estados.get(indice); 
		return estado.getEstado();
	
	}


	
	

}
