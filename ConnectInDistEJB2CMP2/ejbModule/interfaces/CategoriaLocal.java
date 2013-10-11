package interfaces;

import java.io.Serializable;

import javax.ejb.EJBLocalObject;

public interface CategoriaLocal extends EJBLocalObject, EntityValueObject2, Serializable {
	
	public String getNombre();

	public void setNombre(String nombre);

	

	
}
