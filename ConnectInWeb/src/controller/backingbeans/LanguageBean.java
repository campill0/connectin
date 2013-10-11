package controller.backingbeans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import dao.DAOException;
import dao.DAOFactory;
import dao.UsuarioDAO;
import ejb.LocaleRemote;



import beans.Usuario;
 
@ManagedBean(name ="languageBean")
@SessionScoped
public class LanguageBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static UsuarioDAO usuarioDAO;
	private static  DAOFactory df;
	//private String localeCode;

	/// ejb de sesión stateful que guarda el código de idioma
	private LocaleRemote localeRemote;
	private Usuario user;
	private static Map<String,Object> countries;
	static{
		countries = new LinkedHashMap<String,Object>();
		countries.put("English", new Locale("en")); //label, value
		countries.put("Spanish", new Locale("es"));
		countries.put("German", new Locale("de"));
		countries.put("Italian", new Locale("it"));
		countries.put("Chinese", new Locale("zh"));
		countries.put("French", new Locale("fr"));
		
		
	}
	
	public LanguageBean() throws DAOException, NamingException {
		super();
		//localeCode="es";
		
		InitialContext context = new InitialContext();
	//	context.list()
		     localeRemote =(LocaleRemote)context.lookup("java:global/ConnectInDistEJB3/Locale!ejb.LocaleRemote");
	//	localeRemote =(LocaleRemote)context.lookup("java:global/localeservice");
		  
		  df =	DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		  usuarioDAO = df.getUsuarioDAO();
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
	     user = (Usuario) req.getSession().getAttribute("loggedUser");
	   
	    //  String antes = localeRemote.getLocale();
	      
	     if(user==null){
	    	 setLocaleCode("en");
	    
	    	 }
	     else{
	    	 setLocaleCode(user.getLocale());
	    
	     }
	     
	  
		// TODO Auto-generated constructor stub
	}

public String cssClass(String locale){
	HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
    user = (Usuario) req.getSession().getAttribute("loggedUser");
  
	if(user==null){return "";}
	if(user.getLocale().equals(locale)){
		return "locale-highlight";
		}
	else{
		return "";
	}
	
}
	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	
	public String getLocaleCode() {
	
		if(localeRemote!=null){
			System.out.println("getLocaleCode():" + localeRemote.getLocale());
		return localeRemote.getLocale();}
		else{
			System.out.println("getLocaleCode(): localeRemote is null");
			
			return "es";
		}
		
		
		//return "es";
	}


	public void setLocaleCode(String localeCode) {
		
	if(localeRemote!=null){
		System.out.println("setLocaleCode():" + localeCode);
		localeRemote.setLocale(localeCode);
		}
	else{
		System.out.println("setLocaleCode(): localeRemote is null");
	}
	// devolvería "de"
	}
public void changeCountryLocaleCodeEs(){
	changeCountryLocaleCode("es");
}
public void changeCountryLocaleCodeEn(){
	changeCountryLocaleCode("en");
}
public void changeCountryLocaleCodeDe(){
	changeCountryLocaleCode("de");
}
public void changeCountryLocaleCodeIt(){
	changeCountryLocaleCode("it");
}
public void changeCountryLocaleCodeZh(){
	changeCountryLocaleCode("zh");
}
public void changeCountryLocaleCodeFr(){
	changeCountryLocaleCode("fr");
}


	public void changeCountryLocaleCode(String newLocaleValue){
System.out.println(newLocaleValue);
try {
	df =	DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
	usuarioDAO = df.getUsuarioDAO();
} catch (DAOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();   
 user = (Usuario) req.getSession().getAttribute("loggedUser");
 System.out.println("####" + usuarioDAO + "#####");
 System.out.println("####" + user + "#####");
 
 setLocaleCode(newLocaleValue);
user.setLocale(newLocaleValue);
req.getSession().setAttribute("loggedUser",user);

try {
	usuarioDAO.update(user);
} catch (DAOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	changeLocale(newLocaleValue);
	}
	
	
	
	private void changeLocale(String newLocaleValue)
	{
for (Map.Entry<String, Object> entry : countries.entrySet()) {
	        
        	if(entry.getValue().toString().equals(newLocaleValue)){
        		
        		FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());
        		setLocaleCode(newLocaleValue);
        	}
        }
	}
	public void countryLocaleCodeChanged(ValueChangeEvent e){
		
		String newLocaleValue = e.getNewValue().toString();
		
		//loop a map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
        
        	if(entry.getValue().toString().equals(newLocaleValue)){
        		
        		FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());
        		
        	}
        }

	}

}