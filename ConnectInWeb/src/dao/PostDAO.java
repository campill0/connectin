package dao;

import java.util.Date;
import java.util.List;

import beans.Post;
import beans.Usuario;
import dao.DAOException;


public interface PostDAO {
	
	// para crear post utilizar discusionDAO
	public List findAll()throws DAOException;
	public Post findPost(Long id)throws DAOException;
	public void update(Post p)throws DAOException;
	
	

}
