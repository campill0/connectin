package mirmi;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
public class GestorMailsImpl implements GestorMails {
private List<Mail> mails;
	@Override
	public boolean enviar(String texto, String correo) throws RemoteException {
		// TODO Auto-generated method stub
		
		Mail mail=new Mail();
		mail.setTexto(texto);
		mail.setCorreo(correo);
		mails.add(mail);  
		if(mails.size()==3){
		
			for (Mail m : mails) {
				System.out.println("Enviando correo a "+ m.getCorreo() + " ...");
				System.out.println(m.getTexto());
			}
			mails.clear();
		}
		return true; // todo ha ido bien;
	
	}
	public GestorMailsImpl(){
		super();
		mails= new ArrayList<Mail>();
		
	}

}
