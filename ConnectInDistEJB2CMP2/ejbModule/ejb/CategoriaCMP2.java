package ejb;

import interfaces.CategoriaVO;
import interfaces.ValueObject;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;


public abstract class CategoriaCMP2 implements EntityBean {
	
	private static final long serialVersionUID = 1L;
	
	// Parte I: Objeto de Negocio
	
	public abstract String getNombre();
	public abstract void setNombre(String nombre);


	
	// Parte II: Interface Home
	
	public String ejbCreate(String nombre) throws CreateException {
		setNombre(nombre);
		return null;
	}

	public void ejbPostCreate( String nombre) {}
	
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
		CategoriaVO categoria = new CategoriaVO();
		categoria.setNombre(getNombre());
		return categoria;
	}	
	
	public void setVO(ValueObject vo) {
		CategoriaVO categoria = (CategoriaVO )vo;
		setNombre(categoria.getNombre());
	}
	
}
