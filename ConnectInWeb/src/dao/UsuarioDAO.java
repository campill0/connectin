package dao;

import java.util.Date;
import java.util.List;

import beans.Filtro;
import beans.Grupo;
import beans.Notificacion;
import beans.Peticion;
import beans.Usuario;
import dao.DAOException;


public interface UsuarioDAO {
	
	
	public List findAll()throws DAOException;
	public Usuario findUsuario(Long id)throws DAOException;
	public void update(Usuario u)throws DAOException;
	
	public void removeUsuario(Usuario usuario) throws DAOException;
	public void addAmigo(Usuario usuario, Usuario amigo) throws DAOException;
	public void removeAmigo(Usuario usuario, Usuario amigo) throws DAOException;
	public void addNotificacion(Usuario usuario, Notificacion notificacion) throws DAOException;
	public Usuario login (String login,String password)throws DAOException;
	public Usuario findUsuario(String login) throws DAOException;
	
	public Usuario createUsuario(String login, String password, String nombre,
			String apellidos, Date fecha, String mail, Filtro filtro,
			byte[] imagen, String locale) throws DAOException;
	public Usuario createUsuario(String login, String password, String nombre,
			String apellidos, Date fecha, String mail, Filtro filtro)
			throws DAOException;
	
	
	
	
	
	

}
