package controller.backingbeans;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.primefaces.model.CroppedImage;

import controller.Util;



import beans.Filtro;
import beans.Usuario;

import dao.DAOException;
import dao.DAOFactory;
import dao.UsuarioDAO;

@ManagedBean(name="registroUsuarioBean")
@RequestScoped
public class RegistroUsuarioBean {
	private DAOFactory df;
	private UsuarioDAO usuarioDAO;  
	private String login;
	private String password;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String mail;
	private Filtro filtro;
	public Filtro getFiltro() {
		return filtro;
	}
	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}


	
	public void registrarUsuario(ActionEvent actionEvent) throws IOException{
		ImageCropperBean image= (ImageCropperBean) actionEvent.getComponent().getAttributes().get("imagen");
			 try {
			  df =	DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
			  usuarioDAO = df.getUsuarioDAO();
			  System.out.println(filtro.isAmigos()+"|" + filtro.isGrupos() + "|" + filtro.isMensajes());
			  System.out.println("create usuario");
			  Usuario a= new Usuario();
			  
			  usuarioDAO.createUsuario(login, password, nombre, apellidos, fechaNacimiento, mail, filtro,image.getCroppedImage2(),"es");
			  
			  Util.facesMessage(Util.getLocaleString("account_created") ,Util.getLocaleString("your_account_was_successfully_created"));  
			  FacesContext context = FacesContext.getCurrentInstance();  
	          ExternalContext extContext = context.getExternalContext();
	          String toUrl = "/login.faces";
	          String url = extContext.encodeActionURL(context.getApplication().
	          getViewHandler().getActionURL(context, toUrl));

	            extContext.redirect(url);
	          
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	}
	public RegistroUsuarioBean() {
		super();
		
		// TODO Auto-generated constructor stub
		filtro=new Filtro(true,true,true);
	}

}
