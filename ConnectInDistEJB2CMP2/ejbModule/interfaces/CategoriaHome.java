package interfaces;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface CategoriaHome extends EJBHome {
	public CategoriaRemote create(String nombre)throws CreateException, RemoteException;

	public CategoriaRemote findByPrimaryKey(String id) throws FinderException, RemoteException;

	public Collection findAll() throws FinderException, RemoteException;
}
 