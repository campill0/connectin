package beans;

import javax.persistence.*;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity
@Table(name = "AADD_DISCUSION")
public class Discusion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String tituloDiscusion;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Post> posts;
	@ManyToOne
	private Grupo grupo;
	private Usuario autor;
	private String texto;

	public Discusion(){
		this.posts = new ArrayList<Post>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String fechaS(String locale){
		PrettyTime p = new PrettyTime(new Locale(locale));
		return p.format(this.fechaCreacion);
	}

	public List<Post> getPosts() {
		return posts;
	}

	public String getUsuariosParticipantes(){
	if(posts.size()==0){return "none";}
		String usuarios="";
		Map<Long,Usuario> participantes= new HashMap<Long,Usuario>();
		participantes.put(autor.getId(), autor);
		for (Post post : posts) {
			participantes.put(post.getAutor().getId(), post.getAutor());
		}
		for (Usuario usuario : participantes.values()) {
			usuarios+=usuario.getLogin()+" , ";	
		}
	
		
		usuarios=usuarios.substring(0,usuarios.length()-3);
		return usuarios;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void setTexto(String param) {
		this.texto = param;
	}

	public String getTexto() {
		return texto;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getTituloDiscusion() {
		return tituloDiscusion;
	}

	public void setTituloDiscusion(String tituloDiscusion) {
		this.tituloDiscusion = tituloDiscusion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaCreacionS() {
		PrettyTime p = new PrettyTime(new Locale("es"));
		return p.format(this.fechaCreacion);
		
	
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

}