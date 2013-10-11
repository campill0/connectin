package controller.backingbeans;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;

import controller.Util;



import beans.Filtro;
import beans.Usuario;

import dao.DAOException;
import dao.DAOFactory;
import dao.UsuarioDAO;

@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3280944106167299514L;
	private Usuario user;

	private String login;
	private String password;
	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String mail;
	private Filtro filtro;
	private boolean avatarChanged=false;
	
	// imagen
	private CroppedImage croppedImage;
	private String uploadedImage = "nophoto.jpg";
	private String newImageName = "blank";
	private String extension = "png";
	private static final int DEFAULT_WIDTH = 500;
	private int width, height, widthResized, heightResized, widthPreview,			heightPreview;
	byte[] croppedImage2;
	String fakename_resized, fakename;
	String basepath;
	// imagen
	
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
	
	public void saveUsuarioImagen(ActionEvent actionEvent) throws IOException{
		 try {
				 DAOFactory df;
				
				 UsuarioDAO usuarioDAO;  
		  df =	DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		  usuarioDAO = df.getUsuarioDAO();
		  
		  Usuario yo=usuarioDAO.findUsuario(user.getId());
		
		  
	
		  if(avatarChanged){
			  yo.setImagen(croppedImage2);
		  }
		  usuarioDAO.update(yo);
		  user=usuarioDAO.findUsuario(user.getId());
		  //usuarioDAO.createUsuario(login, password, nombre, apellidos, fechaNacimiento, mail, filtro,getCroppedImage2());
		 
		  
		  	Util.facesMessage(Util.getLocaleString("data_saved") ,Util.getLocaleString("account_settings_saved"));
         
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
	
}
	
	public void saveUsuario(ActionEvent actionEvent) throws IOException{
			 try {
					 DAOFactory df;
					
					 UsuarioDAO usuarioDAO;  
			  df =	DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
			  usuarioDAO = df.getUsuarioDAO();
			  
			  Usuario yo=usuarioDAO.findUsuario(user.getId());
			  yo.setApellidos(apellidos);
			  yo.setLogin(login);
			  yo.setFechaNacimiento(fechaNacimiento);
			  if(!password.equals("")){
			  yo.setPassword(password);}
			  
			  yo.setNombre(nombre);
			  yo.setMail(mail);
			  if(avatarChanged){
				  yo.setImagen(croppedImage2);
			  }
			  usuarioDAO.update(yo);
			  user=usuarioDAO.findUsuario(user.getId());
			  //usuarioDAO.createUsuario(login, password, nombre, apellidos, fechaNacimiento, mail, filtro,getCroppedImage2());
			 
			    	Util.facesMessage(Util.getLocaleString("data_saved") ,Util.getLocaleString("account_settings_saved"));
			  
			
	          
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	}
	
	
	
	public void reset(ActionEvent actionEvent) throws IOException{
		 try {
			 DAOFactory df;
				
			 UsuarioDAO usuarioDAO;  
			
			df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
			usuarioDAO= df.getUsuarioDAO();
			
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
		    user = (Usuario) req.getSession().getAttribute("loggedUser");
		    
		    login = user.getLogin();
		    password = user.getPassword();
		    System.out.println("El password del  usuario es " + password);
		    nombre = user.getNombre();
		    apellidos = user.getApellidos();
		    fechaNacimiento = user.getFechaNacimiento();
		    mail = user.getMail();
		    filtro = user.getFiltro();
		    
		    
		    width = height = widthPreview = heightPreview = 128;
		  
		   	Util.facesMessage(Util.getLocaleString("form_reset") ,Util.getLocaleString("the_form_has_been_reset")); 
		
         
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
	
}
	
	
	public void changePassword(ActionEvent actionEvent) throws IOException{
		 try {
				 DAOFactory df;
				
				 UsuarioDAO usuarioDAO;  
		  df =	DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		  usuarioDAO = df.getUsuarioDAO();
		  
		  Usuario yo=usuarioDAO.findUsuario(user.getId());
		  yo.setPassword(password);
		  usuarioDAO.update(yo);
		  user=usuarioDAO.findUsuario(user.getId());
		  Util.facesMessage(Util.getLocaleString("password_changed") ,Util.getLocaleString("password_changed"));  
		
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
	
}
	public UsuarioBean() throws DAOException {
		super();
		
		// TODO Auto-generated constructor stub
		 DAOFactory df;
		
		 UsuarioDAO usuarioDAO;  
		
		df= DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		usuarioDAO= df.getUsuarioDAO();
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
	    user = (Usuario) req.getSession().getAttribute("loggedUser");
	    
	    login = user.getLogin();
	    password = user.getPassword();
	    System.out.println("El password del  usuario es " + password);
	    nombre = user.getNombre();
	    apellidos = user.getApellidos();
	    fechaNacimiento = user.getFechaNacimiento();
	    mail = user.getMail();
	    filtro = user.getFiltro();
	    
	    
	    width = height = widthPreview = heightPreview = 128;
	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

		basepath = servletContext.getRealPath("") + File.separator+ "resources" + File.separator + "tmp" + File.separator + "profileimages" ;
	    
		
	}
	// imagen
	public boolean isRendered() {
		System.out.println(uploadedImage
				+ "//////////////////////////////////////");
		return (!uploadedImage.equals("nophoto.jpg"));
	}
	public boolean isSaveImageRendered() {
		return (croppedImage != null);
		
	}
	
	
	public float getRatio() {

		return (((float) widthPreview) / ((float) widthPreview));
	}
	
	
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		try {

			File targetFolder = new File(basepath);

			InputStream inputStream = event.getFile().getInputstream();
			BufferedImage originalImage = ImageIO.read(event.getFile()	.getInputstream());

			width = originalImage.getWidth();
			height = originalImage.getHeight();
			InputStream inputStreamResized = resize(event.getFile()
					.getInputstream(), Mode.FIT_TO_WIDTH, DEFAULT_WIDTH);
			BufferedImage resizedImage = ImageIO.read(resize(event.getFile()
					.getInputstream(), Mode.FIT_TO_WIDTH, DEFAULT_WIDTH));
			widthResized = resizedImage.getWidth();
			heightResized = resizedImage.getHeight();
			// BufferedImage bi=new BufferedImage(100, 100, 1);

			// extraer parte de la ruta
			String filename = event.getFile().getFileName();
			int dot = filename.lastIndexOf(".");
			int sep = filename.lastIndexOf("/");
			extension = filename.substring(dot + 1);
			String name = filename.substring(sep + 1, dot);
			System.out.println("name:" + name);
			System.out.println("extension:" + extension);
			System.out.println("filename:" + filename);
			//String randomName = getRandomImageName();
			fakename_resized = user.getId() + "_resized." + extension;
			fakename = user.getId() + "." + extension;
			System.out.println("fakename:" + fakename);
			uploadedImage = fakename_resized;

			/*
			 * transformamos el InputStream en BufferedImage para poder usar
			 * Scalr y redimensionar
			 */

			createFile(targetFolder, fakename_resized, inputStreamResized);
			createFile(targetFolder, fakename, inputStream);
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private InputStream resize(InputStream inputStream, Mode mode,
			Integer... ops) throws IOException {

		BufferedImage miimage = ImageIO.read(inputStream);
		BufferedImage bimage;
		switch (mode) {
		case FIT_TO_WIDTH:
			bimage = Scalr
					.resize(miimage, Method.QUALITY, mode, ops[0]/* width */);
			break;

		case FIT_TO_HEIGHT:
			bimage = Scalr
					.resize(miimage, Method.QUALITY, mode, ops[0]/* height */);
			break;
		case FIT_EXACT:
			bimage = Scalr.resize(miimage, Method.QUALITY, mode,
					ops[0]/* width */, ops[1]/* height */);
			break;
		case AUTOMATIC:
			bimage = Scalr.resize(miimage, Method.QUALITY, mode, 100);
			break;
		default:
			bimage = Scalr.resize(miimage, Method.QUALITY, mode, 100);
			break;
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bimage, extension, os);
		InputStream inputStreamResized = new ByteArrayInputStream(
				os.toByteArray());
		return inputStreamResized;
	}
	public void createFile(File targetFolder, String fakename,
			InputStream inputStream) throws IOException {
		OutputStream out = new FileOutputStream(
				new File(targetFolder, fakename));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		inputStream.close();
		out.flush();
		out.close();
	}
	public String getExtension() {
		if (newImageName.equals("blank")) {
			return "png";
		} else {
			return extension;
		}
	}
	private String getRandomImageName() {
		int i = (int) (Math.random() * 100000);

		return String.valueOf(i);
	}
	public String crop() throws IOException {
		avatarChanged=true;
		System.out.println("crop");
		if (croppedImage == null)
		{
			return null;
		}
		// getRandomImageName()
		setNewImageName(user.getId()+ "_croped");
		String newFileName = basepath + File.separator + getNewImageName()
				+ "." + extension;

		System.out.println("###############################################");
		System.out.println(croppedImage.getHeight() + "\n"
				+ croppedImage.getWidth() + "\n" + croppedImage.getLeft()
				+ "\n" + croppedImage.getTop() + "\n"
				+ croppedImage.getOriginalFilename() + "\n" + newFileName
				+ "\n");
		System.out.println("###############################################");
		// CroppedImage croppedImage2 = new CroppedImage("", bytes, left, top,
		// DEFAULT_WIDTH, height)

		BufferedImage originalImage = ImageIO.read(new File(basepath
				+ File.separator + fakename));
		float widthRatio = ((float) widthResized / (float) width);
		float heightRatio = ((float) heightResized / (float) height);
		// traducir parametros x y w h
		int w, h, x, y;
		w = (int) (((float) croppedImage.getWidth()) / widthRatio);
		x = (int) (((float) croppedImage.getLeft()) / widthRatio);
		y = (int) (((float) croppedImage.getTop()) / heightRatio);
		h = (int) (((float) croppedImage.getHeight()) / heightRatio);

		BufferedImage croppedImageTmp = originalImage.getSubimage(x, y, w, h);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(croppedImageTmp, extension, os);
		// para poder guardarlo en bd
		croppedImage2 = os.toByteArray();

		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream(new File(newFileName));

			imageOutput.write(os.toByteArray(), 0, os.toByteArray().length);
			imageOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * FileImageOutputStream imageOutput; try { imageOutput = new
		 * FileImageOutputStream(new File(newFileName));
		 * 
		 * imageOutput.write(croppedImage.getBytes(), 0,
		 * croppedImage.getBytes().length); imageOutput.close(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */

		return null;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public CroppedImage getCroppedImage() {
		return croppedImage;
	}
	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}
	public String getUploadedImage() {
		return uploadedImage;
	}
	public void setUploadedImage(String uploadedImage) {
		this.uploadedImage = uploadedImage;
	}
	public String getNewImageName() {
		return newImageName;
	}
	public void setNewImageName(String newImageName) {
		this.newImageName = newImageName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidthResized() {
		return widthResized;
	}
	public void setWidthResized(int widthResized) {
		this.widthResized = widthResized;
	}
	public int getHeightResized() {
		return heightResized;
	}
	public void setHeightResized(int heightResized) {
		this.heightResized = heightResized;
	}
	public int getWidthPreview() {
		return widthPreview;
	}
	public void setWidthPreview(int widthPreview) {
		this.widthPreview = widthPreview;
	}
	public int getHeightPreview() {
		return heightPreview;
	}
	public void setHeightPreview(int heightPreview) {
		this.heightPreview = heightPreview;
	}
	public byte[] getCroppedImage2() {
		return croppedImage2;
	}
	public void setCroppedImage2(byte[] croppedImage2) {
		this.croppedImage2 = croppedImage2;
	}
	public String getFakename_resized() {
		return fakename_resized;
	}
	public void setFakename_resized(String fakename_resized) {
		this.fakename_resized = fakename_resized;
	}
	public String getFakename() {
		return fakename;
	}
	public void setFakename(String fakename) {
		this.fakename = fakename;
	}
	public String getBasepath() {
		return basepath;
	}
	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}


}
