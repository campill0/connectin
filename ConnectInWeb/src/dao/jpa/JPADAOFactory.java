package dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.BasicConfigurator;

import dao.CategoriaDAO;
import dao.DAOFactory;
import dao.DiscusionDAO;
import dao.GrupoDAO;
import dao.MensajeDAO;
import dao.NotificacionDAO; 
import dao.PeticionDAO;
import dao.PostDAO;
import dao.UsuarioDAO;

public class JPADAOFactory extends DAOFactory {

	public JPADAOFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static EntityManager getEntityManager(){
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("connectintest1");
		return emf.createEntityManager();
		
	}
	
	
	//DAO methods
	public UsuarioDAO getUsuarioDAO(){
		return (UsuarioDAO) new JPAUsuarioDAO();
	}
	@Override
	public PostDAO getPostDAO() {
		// TODO Auto-generated method stub
		return (PostDAO) new JPAPostDAO();
	}
	@Override
	public NotificacionDAO getNotificacionDAO() {
		// TODO Auto-generated method stub
		return (NotificacionDAO) new JPANotificacionDAO();
	}
	@Override
	public MensajeDAO getMensajeDAO() {
		// TODO Auto-generated method stub
		return (MensajeDAO) new JPAMensajeDAO();
	}
	@Override
	public GrupoDAO getGrupoDAO() {
		// TODO Auto-generated method stub
		return (GrupoDAO) new JPAGrupoDAO();
	}
	
	@Override
	public CategoriaDAO getCategoriaDAO() {
		// TODO Auto-generated method stub
		return (CategoriaDAO) new JPACategoriaDAO();
	}
	@Override
	public PeticionDAO getPeticionDAO() {
		// TODO Auto-generated method stub
		return (PeticionDAO) new JPAPeticionDAO();
	}
	@Override
	public DiscusionDAO getDiscusionDAO() {
		// TODO Auto-generated method stub
		return (DiscusionDAO) new JPADiscusionDAO();
	}

}
