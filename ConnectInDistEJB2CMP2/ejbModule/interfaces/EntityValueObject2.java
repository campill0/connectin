package interfaces;


// en jboss 6 se queja de que la interfaz diga que lanza excepciones cuando no lo hace en el caso de las interfaces locales
// que no lanzan RemoteException
public interface EntityValueObject2 {
	
	public ValueObject getVO() ;
	
	public void setVO(ValueObject vo) ;
}
