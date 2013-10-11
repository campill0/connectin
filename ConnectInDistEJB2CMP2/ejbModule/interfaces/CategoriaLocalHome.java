package interfaces;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface CategoriaLocalHome extends EJBLocalHome {
	
	public CategoriaLocal create( String nombre) throws CreateException;

	public CategoriaLocal findByPrimaryKey(String id) throws FinderException;

	public Collection findAll() throws FinderException;
}
