package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ocpsoft.prettytime.PrettyTime;



@Entity
@Table(name = "AADD_MENSAJE")
public class Mensaje implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Lob
	private String texto;
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@Basic
	private String asunto;
	
	public List<EstadoMensaje> getEstado() {
		return estado;
	}

	@ManyToOne
	private Mensaje mensajeRespondido; 

	
	public Mensaje getMensajeRespondido() {
		return mensajeRespondido;
	}

	public void setMensajeRespondido(Mensaje mensajeRespondido) {
		this.mensajeRespondido = mensajeRespondido;
	}

	@ManyToMany
	private List<Usuario> receptores;
	@ManyToOne
	private Usuario emisor;
	
	
	public Mensaje() {
		super();
		// TODO Auto-generated constructor stub
		receptores= new ArrayList<Usuario>();
		estado=new ArrayList<EstadoMensaje>();
	}

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<EstadoMensaje> estado;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTexto(String param) {
		this.texto = param;
	}
	public String fechaS(String locale){
		PrettyTime p = new PrettyTime(new Locale(locale));
		return p.format(this.fecha);
	}
	public String getTexto() {
		return texto;
	}
	
	public String getTextoSaltos() {
		return texto.replaceAll("\n", "<br/>");
	}

	public void setFecha(Date param) {
		this.fecha = param;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setAsunto(String param) {
		this.asunto = param;
	}

	public String getAsunto() {
		return asunto;
	}

	public Usuario getEmisor() {
	    return emisor;
	}

	public void setEmisor(Usuario param) {
	    this.emisor = param;
	}

	public List<Usuario> getReceptores() {
	    return receptores;
	}

	public void setReceptores(List<Usuario> param) {
	    this.receptores = param;
	}

	
	
	//0: no leido
	//1: leido
	//2: respondido
	public EstadoMensaje getEstadoMensaje(Usuario u){
		int index = this.receptores.indexOf(u);
		return this.estado.get(index);
	}
	
	
	public void setEstado(List<EstadoMensaje> estado) {
		this.estado = estado;
	}

	public String getFechaS(){
		PrettyTime p = new PrettyTime(new Locale("es"));
		return p.format(this.fecha);


	}
public String getDestinatariosString(){
	String destinatariosString="";
for (Usuario usuario : receptores) {
	destinatariosString += usuario.getLogin() + ", " ;

}
destinatariosString = destinatariosString.substring(0, destinatariosString.length()-2);
return destinatariosString;
}

	
	public boolean isNuevo(Usuario usu){
		return (this.estado.get(this.receptores.indexOf(usu)).getEstado() == EstadoMensaje.noLeido);
	}
	
	@Override
	 public boolean equals(Object obj) {
		 if (this == obj) return true;
		 if (obj == null) return false;
		 if (getClass() != obj.getClass()) return false;
		 final Mensaje other = (Mensaje)obj;
		 if (id != other.id) return false;
		 return true;
	 }


}