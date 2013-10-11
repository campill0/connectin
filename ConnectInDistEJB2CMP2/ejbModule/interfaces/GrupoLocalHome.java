package interfaces;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface GrupoLocalHome extends EJBLocalHome {
	
	public GrupoLocal create(String nombre, String descripcion, String categoria, String administrador) throws CreateException;

	public GrupoLocal findByPrimaryKey(String nombre) throws FinderException;

	public Collection findAll() throws FinderException;
	
}
