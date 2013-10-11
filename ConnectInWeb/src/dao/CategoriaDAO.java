package dao;

import java.util.Date;
import java.util.List;

import beans.Categoria;
import beans.Grupo;
import dao.DAOException;
import dao.exceptions.ChangePadreCategoriaException;
import dao.exceptions.RemoveEntidadUsuadaException;



public interface CategoriaDAO {
	
	
	
	public Categoria createCategoria(String nombre,List<Categoria> subcategorias,Categoria padre) throws DAOException;
	public Categoria createCategoria(String nombre,List<Categoria> subcategorias) throws DAOException;
	public Categoria createCategoria(String nombre) throws DAOException;
	public List findAll()throws DAOException;
	public Categoria findCategoria(Long id)throws DAOException;
	public void update(Categoria c)throws DAOException;
	public List<Categoria> getCategoriasPrincipales();
	public void remove(Categoria c) throws RemoveEntidadUsuadaException;
	public List<Grupo> getGrupos(Categoria c);
	public void changePadre(Categoria c1, Categoria c2) throws ChangePadreCategoriaException, DAOException ;
	public void changeNombre(Categoria c, String nombre) throws ChangePadreCategoriaException ;
	public void synchronize();

}
