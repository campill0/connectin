package dao.exceptions;

import dao.DAOException;

/**
 *
 * @author Francisco Campillo Asensio
 */
public class AddPeticionDuplicadaException extends DAOException {

    /**
     * Creates a new instance of <code>DAOException</code> without detail message.
     */
    public AddPeticionDuplicadaException() {
    }


    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AddPeticionDuplicadaException(String msg) {
        super(msg);
    }
}