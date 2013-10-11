package beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AADD_PETICION")
public class Peticion implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private EstadoPeticion estado;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	
	public Usuario getReceptor() {
		return receptor;
	}
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}

	private Usuario receptor;
	private Usuario peticionario;
	 
	private Grupo grupo;
	
	public Usuario getPeticionario() {
		return peticionario;
	}
	public void setPeticionario(Usuario peticionario) {
		this.peticionario = peticionario;
	}
	public EstadoPeticion getEstado() {
		return estado;
	}
	public void setEstado(EstadoPeticion estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isSolicitada(){
		return estado.equals(EstadoPeticion.SOLICITADO);
	}
	public String getFechaString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss a");
		return df.format(this.fecha);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	@Override
	 public boolean equals(Object obj) {
		 if (this == obj) return true;
		 if (obj == null) return false;
		 if (getClass() != obj.getClass()) return false;
		 final Peticion other = (Peticion)obj;
		 if (id != other.id) return false;
		 return true;
	 }
	
}
