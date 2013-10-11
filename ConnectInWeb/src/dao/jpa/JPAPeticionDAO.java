package dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;




import dao.DAOException; 
import dao.DAOFactory;
import dao.GrupoDAO;
import dao.NotificacionDAO;
import dao.PeticionDAO;
import dao.UsuarioDAO;
import dao.exceptions.AddPeticionDuplicadaException;


import beans.EstadoPeticion;
import beans.Grupo;
import beans.Notificacion;
import beans.Peticion;
import beans.TipoNotificacion;
import beans.Usuario;

public class JPAPeticionDAO implements PeticionDAO {




	@Override
	public void removePeticionGrupo(Peticion peticion) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		
		Grupo g = em.find(Grupo.class, peticion.getGrupo().getId());
		Peticion p = em.find(Peticion.class, peticion.getId());
		Usuario u = em.find(Usuario.class, p.getPeticionario().getId());
		
		g.getPeticiones().remove(p);
		u.getPeticiones().remove(p);
		em.remove(p);
		em.getTransaction().commit();
	}


	@Override
	public Peticion createPeticionGrupo(Date fecha, EstadoPeticion estado,
			Grupo grupo, Usuario peticionario) throws DAOException {
		
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();
		
		
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = new Peticion();
		p.setEstado(estado);
		p.setFecha(fecha);
		p.setReceptor(grupo.getAdministrador());
		
		if(isPeticionGrupoYaSolicitada(peticionario,grupo.getAdministrador(),grupo)){
		throw new AddPeticionDuplicadaException("already_was_requested_the_same_request_previously");
		}
		else{
		if(grupo!=null){
			Grupo g = em.find(Grupo.class, grupo.getId());
			p.setGrupo(g);
			em.persist(p);
			g.getPeticiones().add(p);
		}
		if(peticionario!=null){
			Usuario u = em.find(Usuario.class, peticionario.getId());
			p.setPeticionario(u);
			em.persist(p);
			u.getPeticiones().add(p);
		}
		em.getTransaction().commit();
		em.close();
		//Util.persist(p);
		notificacionDAO.createNotificacion("#{msg.new_request_of} "+ p.getPeticionario().getLogin() + " #{msg.for_the_group} " + grupo.getNombre(), new Date(), TipoNotificacion.NOTIFICACION_GRUPO_PETICION_SOLICITADA, grupo.getAdministrador(), p,null);
		rmi.Util.sendRmiMessage("Nueva petición de " + p.getPeticionario().getLogin() + " for the group " + grupo.getNombre(),grupo.getAdministrador().getMail());
		return p;
		}
		
	}


	@Override
	public void removePeticionAmistad(Usuario usuario,Peticion peticion) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		
		Peticion p = em.find(Peticion.class, peticion.getId());
		Usuario u = em.find(Usuario.class, usuario.getId());
		u.getPeticiones().remove(p);
		em.remove(p);
		em.getTransaction().commit();
	}

	@Override
	 public boolean isPeticionAmistadYaSolicitada(Usuario pet,Usuario rec) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println(pet+"|" +rec);
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT p FROM Peticion p WHERE p.peticionario = :peticionario AND p.estado = :estado AND p.receptor = :receptor AND p.grupo IS NULL");
		
		
		query.setParameter("peticionario", pet);  
		query.setParameter("estado", EstadoPeticion.SOLICITADO); 
		query.setParameter("receptor", rec); 
		List <Peticion> pl = query.getResultList();
		// si ya me han solicitado amistad pues la acepto en vez de solicitarla
		boolean peticionYaRecibida= (pl.size()>0);
		
		query = em.createQuery("SELECT p FROM Peticion p WHERE p.peticionario = :peticionario AND p.estado = :estado AND p.receptor = :receptor AND p.grupo IS NULL");
		query.setParameter("peticionario", rec);  
		query.setParameter("estado", EstadoPeticion.SOLICITADO); 
		query.setParameter("receptor", pet); 
		List <Peticion> pl2 = query.getResultList();
		
		boolean peticionYaEnviada= (pl2.size()>0);
		
		 return (peticionYaEnviada || peticionYaRecibida);
		 
	}
	@Override
	 public boolean isPeticionGrupoYaSolicitada(Usuario pet,Usuario rec,Grupo grupo) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT p FROM Peticion p WHERE p.peticionario = :peticionario AND p.estado = :estado AND p.receptor = :receptor AND p.grupo = :grupo");
		
		query.setParameter("grupo", grupo); 
		query.setParameter("peticionario", pet);  
		query.setParameter("estado", EstadoPeticion.SOLICITADO); 
		query.setParameter("receptor", rec); 
		List <Peticion> pl = query.getResultList();
		
		
		 return pl.size()>0;
		 
	}
	@Override
	public Peticion createPeticionAmistad(Usuario receptor,Usuario peticionario	) throws DAOException {
		EntityManager em =JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = new Peticion();
		p.setReceptor(receptor);
		p.setEstado(EstadoPeticion.SOLICITADO);
		p.setFecha(new Date());
		
		
		Usuario rec = em.find(Usuario.class, receptor.getId());
		Usuario pet = em.find(Usuario.class, peticionario.getId());
		p.setPeticionario(pet);
		
		if(isPeticionAmistadYaSolicitada(pet,rec)){
			throw new AddPeticionDuplicadaException("already_was_requested_the_same_request_previously");
			}	
		
		else{
		em.persist(p);
		rec.getPeticiones().add(p);
		
		em.getTransaction().commit();
		em.close();
		//Util.persist(p);
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();

		notificacionDAO.createNotificacion("#{msg.new_friendship_request_of} " + p.getPeticionario().getLogin(), new Date(), TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_SOLICITADA, receptor, p,null);
		rmi.Util.sendRmiMessage("Nueva petición de amistad de " + p.getPeticionario().getMail(),receptor.getMail());
		return p;
		}
	
	}


	@Override
	public void aceptarPeticionAmistad(Usuario receptor, Peticion peticion)
			throws DAOException {
		System.out.println("< " + receptor.getLogin() + " " + receptor.getId() + ">");
		System.out.println("< " + peticion.getId() + " " + peticion.getPeticionario().getLogin() + ">");
		// TODO Auto-generated method stub
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		UsuarioDAO usuarioDAO = df.getUsuarioDAO();
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = em.find(Peticion.class, peticion.getId());
		
		p.setEstado(EstadoPeticion.ACEPTADO);
		
		System.out.println(receptor + " acepta la peticion " + p.getId() + " " + p.getEstado() + " de " + p.getPeticionario() );
		em.getTransaction().commit();
		em.close();
		
		
		
		usuarioDAO.addAmigo(receptor,peticion.getPeticionario());
		notificacionDAO.createNotificacion(receptor.getNombre()+" #{msg.has_accepted_your_friend_request} ", new Date(), TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_ACEPTADA, p.getPeticionario(), p,null);
		rmi.Util.sendRmiMessage(receptor.getNombre()+" ha aceptado tu petición de amistad ", p.getPeticionario().getMail());
	}

	@Override
	public void aceptarPeticionGrupo(Grupo grupo, Peticion peticion)
			throws DAOException {
		// TODO Auto-generated method stub
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = em.find(Peticion.class, peticion.getId());
		
		p.setEstado(EstadoPeticion.ACEPTADO);
		
		em.getTransaction().commit();
		em.close();
		
		GrupoDAO grupoDAO = df.getGrupoDAO();
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();
		
		grupoDAO.addUsuario(grupo, peticion);
		notificacionDAO.createNotificacion(grupo.getAdministrador().getLogin()+ " #{msg.has_accepted_your_request_to_join_the_group} " + grupo.getNombre(), new Date(), TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_ACEPTADA, peticion.getPeticionario(), peticion,null);
		rmi.Util.sendRmiMessage(grupo.getAdministrador().getLogin()+ " ha aceptado tu petición de unirte al grupo " + grupo.getNombre(), p.getPeticionario().getMail());
	}
	
	@Override
	public void rechazarPeticionGrupo(Grupo grupo, Peticion peticion)
			throws DAOException {
		// TODO Auto-generated method stub
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = em.find(Peticion.class, peticion.getId());
		
		p.setEstado(EstadoPeticion.RECHAZADO);
		
		em.getTransaction().commit();
		em.close();
	//	GrupoDAO grupoDAO = df.getGrupoDAO();
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();
		
	//	grupoDAO.addUsuario(grupo, peticion);
		notificacionDAO.createNotificacion(grupo.getAdministrador().getNombre()+" #{msg.has_been_refused_entry_into_the_group} " + grupo.getNombre(), new Date(), TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_RECHAZADA, peticion.getPeticionario(), peticion,null);
		rmi.Util.sendRmiMessage(grupo.getAdministrador().getNombre() + " ha rechazado tu entrada en el grupo " + grupo.getNombre(),peticion.getPeticionario().getMail());
	}

	@Override
	public void rechazarPeticionAmistad(Usuario receptor, Peticion peticion)
			throws DAOException {
		DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		
		
		EntityManager em =  JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Peticion p = em.find(Peticion.class, peticion.getId());
		
		p.setEstado(EstadoPeticion.RECHAZADO);
		
		em.getTransaction().commit();
		em.close();
		
		NotificacionDAO notificacionDAO = df.getNotificacionDAO();
		notificacionDAO.createNotificacion(receptor.getNombre()+" #{msg.has_rejected_your_friend_request} ", new Date(), TipoNotificacion.NOTIFICACION_CONTACTO_PETICION_RECHAZADA, peticion.getPeticionario(), peticion,null);
		rmi.Util.sendRmiMessage(receptor.getNombre()+ " ha rechazado tu petición de amistad ", peticion.getReceptor().getMail());
		
		
	}

	

	@Override
	public Peticion findPeticion(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Peticion  p=em.find(Peticion.class,id);
	return p;
	}
	
	
	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		
		ArrayList<Peticion> lista= new ArrayList<Peticion>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT p FROM Peticion p");
		List <Peticion> pl = query.getResultList();
	
		 return pl;
		
	}

}
