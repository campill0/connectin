package rmi;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import mirmi.GestorMails;

public class Util {

	public static void sendRmiMessage(String descripcion,String email) {
		System.setSecurityManager( new   RMISecurityManager()); 
	     
	    GestorMails gm =  null;  
	     
	    // Obtención de la referencia remota  
	    try   {  
	     gm = (GestorMails ) Naming.lookup ( "gestormails"); 
	    } catch  (Exception e) { 
	      System.out.println("Error al obtener la referencia remota" ); 
	     // System.exit(1 ); 
	    }  
	     
	    // Uso del objeto remoto. Puede lanzar una excepción remota  
	    try   {  
	     
	      System.out.println(gm.enviar(descripcion,email)); 
	  	    } catch   (Exception e) { 
	      System.out.println("Error al acceder al objeto remoto"); 
	    //  System.exit(1 );  
	    }
	}
	
}
