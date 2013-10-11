package interfaces;


public class GrupoVO implements ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String descripcion;
	private String administrador;
	private String categoria;

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; } 
	
	public String getDescripcion() { return descripcion; } 
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; } 
	
	public String getAdministrador() { return administrador; } 
	public void setAdministrador(String administrador) { this.administrador = administrador; }

}
