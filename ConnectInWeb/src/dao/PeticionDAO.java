package dao;

import java.util.Date;
import java.util.List;

import beans.EstadoPeticion;
import beans.Grupo;
import beans.Peticion;
import beans.Usuario;
import dao.DAOException;


public interface PeticionDAO {
	
	public void removePeticionGrupo(Peticion peticion) throws DAOException;
	public Peticion createPeticionGrupo(Date fecha, EstadoPeticion estado,			Grupo grupo, Usuario peticionario) throws DAOException;
	
	
	public void removePeticionAmistad(Usuario usuario, Peticion peticion)
			throws DAOException;
	
	public Peticion createPeticionAmistad(Usuario receptor, Usuario peticionario)
			throws DAOException;
	public void aceptarPeticionAmistad(Usuario receptor,Peticion peticion)
			throws DAOException;
	public void rechazarPeticionAmistad(Usuario receptor,Peticion peticion)
			throws DAOException;
	public void aceptarPeticionGrupo(Grupo grupo, Peticion peticion)
			throws DAOException;
	public void rechazarPeticionGrupo(Grupo grupo, Peticion peticion)
			throws DAOException;
	public Peticion findPeticion(Long id) throws DAOException;
	public List findAll() throws DAOException;
	
	public boolean isPeticionGrupoYaSolicitada(Usuario pet, Usuario rec, Grupo grupo)
			throws DAOException;
	public boolean isPeticionAmistadYaSolicitada(Usuario pet, Usuario rec)
			throws DAOException;
	
}
