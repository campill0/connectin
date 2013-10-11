package ejb;

import interfaces.GrupoVO;
import interfaces.ValueObject;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;


public abstract class GrupoCMP2 implements EntityBean {
	
	
	private static final long serialVersionUID = 1L;
	
	// Parte I: Objeto de Negocio
	
	public abstract String getNombre();
	public abstract void setNombre(String nombre);
	
	public abstract String getDescripcion();
	public abstract void setDescripcion(String descripcion);
	
	public abstract String getAdministrador();
	public abstract void setAdministrador(String administrador);
	
	public abstract String getCategoria();
	public abstract void setCategoria(String categoria);
	// Parte II: Interface Home
	
	public String ejbCreate(String nombre, String descripcion, String categoria, String administrador) throws CreateException {
		setNombre(nombre);
		setDescripcion(descripcion);
		setAdministrador(administrador);
		setCategoria(categoria);
		return null;
	}

	public void ejbPostCreate(String nombre, String descripcion, String categoria, String administrador) {}
	
	@Override
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {}
	
	// Parte III: Métodos de Retrollamada
	
	private EntityContext context;
	
	@Override
	public void setEntityContext(EntityContext context) throws EJBException,
			RemoteException {
		this.context = context;		
	}

	@Override
	public void unsetEntityContext() throws EJBException, RemoteException {
		this.context = null;
	}
	
	@Override
	public void ejbActivate() throws EJBException, RemoteException {}

	@Override
	public void ejbLoad() throws EJBException, RemoteException {}

	@Override
	public void ejbPassivate() throws EJBException, RemoteException {}

	@Override
	public void ejbStore() throws EJBException, RemoteException {}
	
	
	// Implementación de Value Object
	
	public ValueObject getVO() {
		GrupoVO grupo = new GrupoVO();
		grupo.setDescripcion(getDescripcion());
		grupo.setNombre(getNombre());
		grupo.setAdministrador(getAdministrador());
		grupo.setCategoria(getCategoria());
		return grupo;
	}	
	
	public void setVO(ValueObject vo) {
		GrupoVO grupo = (GrupoVO)vo;
		setDescripcion(grupo.getDescripcion());
		setNombre(grupo.getNombre());
		setCategoria(grupo.getCategoria());
		setAdministrador(grupo.getAdministrador());
	}
	
}
