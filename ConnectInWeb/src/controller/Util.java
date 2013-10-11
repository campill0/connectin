package controller;



import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Util {
	public String INTERNATIONALIZATION_TAG_START= "<i18n>";
	public String INTERNATIONALIZATION_TAG_END=   "<i18n/>";
	public static String baseUrl() {
		 
		 return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		 
		
		}

	/*
	 * devuelve el outcome correcto dependiendo de donde nos encontremos
	 * */
	public String outcome(String outcome,String section){
		String baseUrl=baseUrl();
		boolean matchHome=baseUrl.matches("/[a-z]*");
		boolean matchSection=baseUrl.matches("/[a-z]*/"+section);
		
		return "admin/newHotel";
	}
	public static String getLocaleString(String resourceName,String key, String defaultValue){
		FacesContext context = FacesContext.getCurrentInstance();
	//	System.out.println(context);
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, resourceName);
		String message="";
		try  {
			message= bundle.getString(key);
		}
		catch(java.util.MissingResourceException mre){
			message=defaultValue;	
		}
		return message;
		
	}
	// vamos a añadir unas marcas al texto para identificarlo como internacionalizable
	// ya que vamos a almacenar este texto mezclado con otra información
	// debemos procurar no utilizar los caracteres especiales de marcado en el resto del texto

	public String addInternationalizedStringTags(String str)
	{
		return  INTERNATIONALIZATION_TAG_START +str+INTERNATIONALIZATION_TAG_END; 
	}
	public String extractInternationalizedStringTokens(String str)
	{
		Pattern faceLetsPattern = Pattern.compile(INTERNATIONALIZATION_TAG_START +"([a-z_]+)"+INTERNATIONALIZATION_TAG_END); 
		SortedSet<String> faceletsMessages= extractPattern(faceLetsPattern,str);
		return ""; 
	}
	public static SortedSet<String> extractPattern(Pattern pattern,String fileStr){
		SortedSet<String> messages=new TreeSet<String>();
		Matcher dateMatcher = pattern.matcher(fileStr);
		while (dateMatcher.find())		    {
		     messages.add(dateMatcher.group(1));		    }
		   
		   return messages;
	}
	public static void facesMessage(String title, String text){
		 FacesContext context = FacesContext.getCurrentInstance();
		    context.addMessage(null, new FacesMessage(title,text));
			 
	}
	// internacionaliza un string teniendo en cuenta unicamente las cadenas internacionalizables delimitadas por #{msg.xxx}
	public static String internationalizedStringTokens(String str){
		Pattern pattern = Pattern.compile("#\\{\\s*msg.{1}([a-z_]+)\\s*\\}");
		List<String> messages=new ArrayList<String>();
		String result="";
		int index=0;
		Matcher matcher = pattern.matcher(str);
		while (matcher.find())		    {
		//	first=matcher.first;
			
			String palabraAnterior = str.substring(index,matcher.start());
			 messages.add(palabraAnterior);
			 result+=palabraAnterior;
			 String g=matcher.group(1);
		     messages.add(controller.Util.getLocaleString( g));
			 result+=controller.Util.getLocaleString( g);
			// messages.add(g);
		     index=matcher.end();
		     }
		String palabraAnterior = str.substring(index,str.length());
		  messages.add(palabraAnterior);
		  result+=palabraAnterior;
		  System.out.println(result);
		   return result;
	}
	public static String getLocaleString(String key)
	{
		return getLocaleString("resource.MessageResources",key);
	}
	public static String getLocaleString(String resourceName,String key)
	{
		 FacesContext context = FacesContext.getCurrentInstance();
		    ResourceBundle text = ResourceBundle.getBundle("resource.MessageResources", context.getViewRoot().getLocale());
		    String result;
		   
		    try{
		    	result = text.getString(key);}
		    catch(MissingResourceException e){
		    	result = "???" + key + "???";
		    }
		    return result;
	}
	public static long random(long min,long max){
		return  (min + (int)(Math.random() * ((max - min) + 1)));
	}
	public  String calendarToString(Calendar date){
		return date.get(Calendar.YEAR)+"/"+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.DATE);
	}
	public long daysBetween(Calendar startDate, Calendar endDate) {  
		  Calendar date = (Calendar) startDate.clone();  
		  long daysBetween = 0;  
		  while (date.before(endDate)) {  
		    date.add(Calendar.DAY_OF_MONTH, 1);  
		    daysBetween++;  
		  }  
		  return daysBetween;  
		} 
	
	public  static String md5(String s){
	       MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	       m.update(s.getBytes(),0,s.length());
	return new BigInteger(1,m.digest()).toString(16);
	      
}
	public static String capitalize(String s){
		if (s.length() == 0) return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	public static Object accessBeanFromFacesContext(final String theBeanName, final FacesContext theFacesContext) {
		final Object returnObject = theFacesContext.getELContext().getELResolver().getValue(theFacesContext.getELContext(), null, theBeanName);
		if (returnObject == null) {
		System.err.println("Bean with name " + theBeanName + " was not found. Check the faces-config.xml file if the given bean name is ok."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return returnObject;
		}
}
