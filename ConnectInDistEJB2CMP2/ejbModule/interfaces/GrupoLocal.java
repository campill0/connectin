package interfaces;

import java.io.Serializable;

import javax.ejb.EJBLocalObject;
  
public interface GrupoLocal extends EntityValueObject2, EJBLocalObject, Serializable {
	
	public String getNombre();
	
	public void setNombre(String nombre);
	
	public String getDescripcion();
	
	public void setDescripcion(String descripcion);

	public String getAdministrador();
	
	public void setAdministrador(String administrador);
	
	public String getCategoria();
	
	public void setCategoria(String categoria);
	
}
