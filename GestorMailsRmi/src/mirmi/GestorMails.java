package mirmi;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GestorMails  extends Remote {
	public boolean enviar(String texto,String correo) throws RemoteException;
}
