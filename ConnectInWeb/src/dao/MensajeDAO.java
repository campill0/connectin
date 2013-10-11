package dao;

import java.util.Date;
import java.util.List;

import beans.EstadoMensaje;
import beans.Mensaje;

import beans.Usuario;
import dao.DAOException;


public interface MensajeDAO {
	
	public  Mensaje createMensaje(Mensaje mensajeRespondido,boolean leido, String texto, Date fecha, String asunto, Usuario emisor, List<Usuario> receptores) throws DAOException ;
	public List findAll()throws DAOException;
	public Mensaje findMensaje(Long id)throws DAOException;
	public void update(Mensaje m)throws DAOException;
	public Mensaje setEstadoMensaje(Mensaje m, Usuario u, Integer estado)	throws DAOException;
	public Integer getEstadoMensaje(Mensaje m, Usuario u)	throws DAOException;
	
	

}
