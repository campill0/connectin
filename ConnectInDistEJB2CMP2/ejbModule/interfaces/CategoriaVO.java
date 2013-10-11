package interfaces;


public class CategoriaVO implements ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

}
