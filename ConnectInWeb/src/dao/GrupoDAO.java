package dao;

import java.util.Date;
import java.util.List;

import beans.Categoria;
import beans.Discusion;
import beans.Grupo;
import beans.Peticion;
import beans.Usuario;
import dao.DAOException;


public interface GrupoDAO {
	

	
	public Grupo createGrupo(String nombre, String descripcion,Usuario administrador,Categoria categoria) throws DAOException;
	public List findAll()throws DAOException;
	public Grupo findGrupo(Long id)throws DAOException;
	
	
	
	public Grupo addUsuario(Grupo g, Usuario u) throws DAOException;
	public void addPeticion(Grupo g, Peticion p) throws DAOException;
	public void removeUsuario(Grupo g, Usuario u) throws DAOException;
	public Grupo addUsuario(Grupo g, Peticion p) throws DAOException;
	public List<Discusion> findDiscusiones(Long id) throws DAOException;
	
	

}
