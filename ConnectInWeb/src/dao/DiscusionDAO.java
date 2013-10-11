package dao;

import java.util.Date;
import java.util.List;

import beans.Discusion;
import beans.Grupo;
import beans.Post;
import beans.Usuario;
import dao.DAOException;


public interface DiscusionDAO {
	
	public Discusion createDiscusion(String texto,String titulo,Grupo grupo, Date fechaDeCreacion, Usuario autor) throws DAOException;

	public Post createPost(Discusion discusion, String texto, Date fecha, Usuario autor)
			throws DAOException;


	public List findAll() throws DAOException;

	public Discusion findDiscusion(Long id) throws DAOException;
	
	

}
