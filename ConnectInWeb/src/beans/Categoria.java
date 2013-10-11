package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "AADD_CATEGORIA")
@NamedQueries({
	@NamedQuery(name="findCategoriasPrincipales",	query="SELECT c FROM Categoria c WHERE c.padre IS NULL")
})

public class Categoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1943714046976659001L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	@OneToMany(mappedBy="padre" , fetch=FetchType.EAGER )
	private List<Categoria> subcategoria;
	
	@ManyToOne ( fetch=FetchType.EAGER )
	private Categoria padre;
	
	public Categoria(){
		this.subcategoria = new ArrayList<Categoria>();
	}
	
	@Override
	public String toString(){
		return this.nombre;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Categoria> getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(List<Categoria> param) {
		this.subcategoria = param;
	}

	public void setNombre(String param) {
		this.nombre = param;
	}

	public String getNombre() {
		return nombre;
	}

	public Categoria getPadre() {
		return padre;
	}

	public void setPadre(Categoria padre) {
		this.padre = padre;
	}
	public List getDescendientes(){
		List<Categoria> descendientes= new ArrayList<Categoria>();
		for (Categoria categoria : subcategoria) {
			descendientes.add(categoria);
			descendientes.addAll(categoria.getDescendientes());
		}
		return descendientes;
		
	}
	public boolean isDescendiente(Categoria c){
		
		for (Categoria cat : subcategoria) {
			if(cat.equals(c)){
				return true;
			}
			else if (cat.isDescendiente(c))
				{
			return true;		
				}
			
		}
		return false;
		
	}
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null) return false;
	        if (getClass() != obj.getClass()) return false;
	        final Categoria other = (Categoria)obj;
	        if (id != other.id) return false;
	        return true;
	    }

}