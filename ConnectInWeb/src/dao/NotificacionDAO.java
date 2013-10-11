package dao;

import java.util.Date;
import java.util.List;

import beans.Mensaje;
import beans.Notificacion;
import beans.Peticion;
import beans.TipoNotificacion;
import beans.Usuario;
import dao.DAOException;


public interface NotificacionDAO {
	

	public List findAll()throws DAOException;
	public Notificacion findNotificacion(Long id)throws DAOException;
	public void update(Notificacion n)throws DAOException;
	
	public Notificacion createNotificacion(String descripcion, Date fecha,
			TipoNotificacion tipo, Usuario receptorNotificacion,
			Peticion peticion, Mensaje mensaje) throws DAOException;
	public List findAll(Usuario usuario) throws DAOException;
	
	

}
