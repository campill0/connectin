package beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AADD_FILTRO")
public class Filtro implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean mensajes;
	private boolean grupos;
	private boolean amigos;

	public Filtro(){
		this.mensajes = false;
		this.amigos = false;
		this.grupos = false;
	}
	
	public Filtro(boolean mensajes, boolean grupos, boolean amigos) {
		super();
		this.mensajes = mensajes;
		this.grupos = grupos;
		this.amigos = amigos;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isMensajes() {
		return mensajes;
	}
	public void setMensajes(boolean mensajes) {
		this.mensajes = mensajes;
	}
	public boolean isGrupos() {
		return grupos;
	}
	public void setGrupos(boolean grupos) {
		this.grupos = grupos;
	}
	public boolean isAmigos() {
		return amigos;
	}
	public void setAmigos(boolean amigos) {
		this.amigos = amigos;
	}

}
