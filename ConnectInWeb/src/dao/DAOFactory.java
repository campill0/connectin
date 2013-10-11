package dao;

import dao.jpa.JPADAOFactory;
import dao.jpa.JPAUsuarioDAO;

public abstract class DAOFactory {
	// Methods
	public enum Type {
		JPA, HIBERNATE, JDBC
	}

	// factory types constants
	public static DAOFactory getDAOFactory(Type type) throws DAOException {
		switch (type) {
		case JPA: {
			try {
				return new JPADAOFactory();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		default:
			return null;
		}
	}
	
	//DAO methods
		public abstract UsuarioDAO getUsuarioDAO();
		public abstract PostDAO getPostDAO();
		public abstract DiscusionDAO getDiscusionDAO();
		public abstract NotificacionDAO getNotificacionDAO();
		public abstract MensajeDAO getMensajeDAO();
		public abstract GrupoDAO getGrupoDAO();
		public abstract CategoriaDAO getCategoriaDAO();
		public abstract PeticionDAO getPeticionDAO();

}
