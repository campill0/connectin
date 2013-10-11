package interfaces;

import java.rmi.RemoteException;

public interface EntityValueObject {
	
	public ValueObject getVO() throws RemoteException;
	
	public void setVO(ValueObject vo) throws RemoteException;
}
