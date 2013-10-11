package interfaces;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import interfaces.EntityValueObject;

public interface GrupoRemote extends EJBObject, EntityValueObject {
	
	public String getNombre() throws RemoteException;
	
	public void setNombre(String nombre) throws RemoteException;
	
	public String getDescripcion() throws RemoteException;
	
	public void setDescripcion(String descripcion) throws RemoteException;
	
	public String getAdministrador() throws RemoteException;
	
	public void setAdministrador(String administrador) throws RemoteException;
	
	public String getCategoria() throws RemoteException;
	
	public void setCategoria(String categoria) throws RemoteException;
	
}
