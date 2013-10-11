package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.*;

import org.ocpsoft.prettytime.PrettyTime;

@Entity
@Table(name = "AADD_POST")
public class Post  implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Basic
	private String texto;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	private Usuario autor;
	

	private Discusion discusion;

	public Discusion getDiscusion() {
		return discusion;
	}

	public void setDiscusion(Discusion discusion) {
		this.discusion = discusion;
	}
	public String fechaS(String locale){
		PrettyTime p = new PrettyTime(new Locale(locale));
		return p.format(this.fecha);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTexto(String param) {
		this.texto = param;
	}

	public String getTexto() {
		return texto;
	}

	public void setFecha(Date param) {
		this.fecha = param;
	}

	public Date getFecha() {
		return fecha;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	
	public String getFechaS(){
		PrettyTime p = new PrettyTime(new Locale("es"));
		return p.format(this.fecha);
		

	}
	
	@Override
		 public boolean equals(Object obj) {
			 if (this == obj) return true;
			 if (obj == null) return false;
			 if (getClass() != obj.getClass()) return false;
			 final Post other = (Post)obj;
			 if (id != other.id) return false;
			 return true;
		 }


}