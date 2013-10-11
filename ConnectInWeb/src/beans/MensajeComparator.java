package beans;

import java.util.Comparator;

public class MensajeComparator implements Comparator<Mensaje> {

	@Override
	public int compare(Mensaje o1, Mensaje o2) {
		return o2.getFecha().compareTo(o1.getFecha());
	}

}
