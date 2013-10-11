package beans;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AADD_GRUPO")
@NamedQueries({
	@NamedQuery(name="getTodosGrupos",
	query="SELECT g FROM Grupo g"),
	@NamedQuery(name="getGrupoPorNombre",
	query="SELECT g FROM Grupo g WHERE lower(g.nombre) LIKE :nombreGrupo")

})

public class Grupo implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nombre;
	@Column(length=250)
	private String descripcion;
	@ManyToOne
	private Usuario administrador;
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Usuario> usuarios;
	
	@OneToMany(cascade=CascadeType.MERGE)
	private List<Peticion> peticiones;
	
	@JoinColumn(name = "GRUPO_ID", referencedColumnName = "id")
	@OneToMany(mappedBy="grupo")
	private List<Discusion> discusiones;
	@OneToOne
	private Categoria categoria;

	public Grupo(){
		this.usuarios = new ArrayList<Usuario>();
		this.peticiones = new ArrayList<Peticion>();
		this.discusiones = new ArrayList<Discusion>();
		
	}
	
	public void removeUsuario(Usuario u){
		if (!administrador.equals(u)){
			usuarios.remove(u);
			u.getGrupos().remove(this);
		}
	}
	public boolean removable(Usuario u){
		return !administrador.equals(u) && usuarios.contains(u);
		
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria param) {
		this.categoria = param;
	}


	public void setNombre(String param) {
		this.nombre = param;
	}

	public String getNombre() {
		return nombre;
	}

	public void setDescripcion(String param) {
		this.descripcion = param;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Usuario getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Discusion> getDiscusiones() {
		return discusiones;
	}

	public void setDiscusiones(List<Discusion> discusiones) {
		this.discusiones = discusiones;
	}

	public List<Peticion> getPeticiones() {
		return peticiones;
	}

	public void setPeticiones(List<Peticion> peticiones) {
		this.peticiones = peticiones;
	}

	//1: peticion enviada sin aceptar
	//2: sin peticion enviada o sin aceptar
	//3: ya es miembro del grupo
	public Integer solicitudEnviada(Usuario usu){
		if(this.administrador.equals(usu) || this.usuarios.contains(usu)){
			return 3;
		}
		for(Peticion p:this.peticiones){
			if((p.getEstado() == EstadoPeticion.SOLICITADO) && (p.getPeticionario().equals(usu))){
				return 1;
			}
		}
		return 2;
	}

	public boolean solicitudEnviadaPendiente(Usuario usu){
	
		for(Peticion p:this.peticiones){
			if((p.getEstado() == EstadoPeticion.SOLICITADO) && (p.getPeticionario().equals(usu))){
				return true;
			}
		}
		return false;
	}
	public boolean isAdministradorGrupo(Usuario usu){
		if(this.administrador.getId().equals(usu.getId())){
			return true;
		}
		return false;
	}
	@Override
		 public boolean equals(Object obj) {
			 if (this == obj) return true;
			 if (obj == null) return false;
			 if (getClass() != obj.getClass()) return false;
			 final Grupo other = (Grupo)obj;
			 if (id != other.id) return false;
			 return true;
		 }
	
}