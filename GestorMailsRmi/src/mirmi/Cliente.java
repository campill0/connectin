package mirmi;
import   java.rmi.*;  
 
public   class  Cliente { 
   
  public  static   void  main(String[] args) {  
         
    // Instalación del Gestor de Seguridad RMI 
    System.setSecurityManager( new   RMISecurityManager()); 
     
    GestorMails gm =  null;  
     
    // Obtención de la referencia remota  
    try   {  
     gm = (GestorMails ) Naming.lookup ( "gestormails"); 
    } catch  (Exception e) { 
      System.err.println("Error al obtener la referencia remota" ); 
      System.exit(1 ); 
    }  
     
    // Uso del objeto remoto. Puede lanzar una excepción remota  
    try   {  
     
      System.out.println(gm.enviar("1 Solicitud de grupo aceptada","francisco.campillo@um.es")); 
      System.out.println(gm.enviar("2 Solicitud de amistad aceptada","pepe.campillo@um.es")); 
      System.out.println(gm.enviar("3 Solicitud de amistad aceptada","juan.campillo@um.es")); 
      System.out.println(gm.enviar("4 Solicitud de amistad aceptada","lope.campillo@um.es")); 
      System.out.println(gm.enviar("5 Solicitud de grupo aceptada","lorena.campillo@um.es")); 
      System.out.println(gm.enviar("6 Solicitud de amistad aceptada","ased.campillo@um.es")); 
      System.out.println(gm.enviar("7 Solicitud de grupo aceptada","aaron@msn.com")); 
    } catch   (Exception e) { 
      System.err.println("Error al acceder al objeto remoto"); 
      System.exit(1 );  
    }      
  }  
   
}