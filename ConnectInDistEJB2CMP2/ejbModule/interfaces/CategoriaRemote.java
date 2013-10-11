package interfaces;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface CategoriaRemote extends EJBObject, EntityValueObject {
	
	public String getNombre() throws RemoteException;

	public void setNombre(String nombre) throws RemoteException;


	
}
