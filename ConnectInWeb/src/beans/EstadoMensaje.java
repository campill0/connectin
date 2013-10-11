package beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AADD_ESTADO_MENSAJE")

public class EstadoMensaje implements Serializable{
	//0: no leido
	//1: leido
	//2: respondido

	/**
	 * 
	 */
	private static final long serialVersionUID = 2565937885720257694L;
	public static final Integer noLeido = 0;
	public static final Integer leido = 1;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer estado;
	
	public EstadoMensaje(Integer estado) {
		super();
		this.estado = estado;
	}
	public EstadoMensaje() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	public boolean isLeido(){
		return estado==leido;
	}
}
