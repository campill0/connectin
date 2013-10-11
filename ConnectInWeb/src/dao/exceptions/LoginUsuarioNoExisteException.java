package dao.exceptions;

import dao.DAOException;

/**
 *
 * @author Francisco Campillo Asensio
 */
public class LoginUsuarioNoExisteException extends DAOException {

    /**
     * Creates a new instance of <code>DAOException</code> without detail message.
     */
    public LoginUsuarioNoExisteException() {
    }


    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public LoginUsuarioNoExisteException(String msg) {
        super(msg);
    }
}