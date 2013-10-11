package interfaces;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface GrupoHome extends EJBHome {
	
	public GrupoRemote create(String nombre, String descripcion, String categoria, String administrador)
			throws CreateException, RemoteException;

	public GrupoRemote findByPrimaryKey(String nombre) throws FinderException, RemoteException;

	public Collection findAll() throws FinderException, RemoteException;
	
}
