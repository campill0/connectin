package mirmi;
import   java.rmi.*;  
import   java.rmi.server.*;  
 
public   class  Servidor { 
   
  public   static   void  main (String[] args) { 
   
    // Instalación del Gestor de Seguridad RMI 
	//  System.setProperty("java.rmi.activation.port", "2222");
	  System.setSecurityManager( new   RMISecurityManager ()); 
    
    // Creación del Objeto Distribuido  
    GestorMails   gm = new   GestorMailsImpl(); 
     
    // Activación del objeto. Puede lanzar una excepción Remota. 
    try   {  
    	
        UnicastRemoteObject.exportObject(gm); 
    } catch  (Exception e) { 
      System.err.println("Error al activar el objeto distribuido"); 
      System.exit(1); 
    }  
	
	// Registro del objeto 
    try   {  
    	
      Naming.rebind( "gestormails", gm);   
    } catch  (Exception e) { 
      System.out.println("Error al registrar el objeto distribuido" + e); 
      System.exit(1 ); 
    }  
     
    System.out.println("Objeto distribuido listo ...");   
  }  
   
} 