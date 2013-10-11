package controller.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.validator.routines.EmailValidator;

import controller.Util;

import dao.DAOException;
import dao.DAOFactory;
import dao.UsuarioDAO;


@FacesValidator("controller.LoginNameExistValidator")
public class LoginNameExistValidator implements Validator {
	private static UsuarioDAO usuarioDAO;
	private static DAOFactory df;
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub
		
	
		
		if(loginNameExistInDB(arg2.toString())){
			
		
			
			String message=Util.getLocaleString("Este nombre ya está registrado.");
			
			
			FacesMessage msg = new FacesMessage(message, message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}	
	}
public boolean loginNameExistInDB(String loginName){
		try {
			df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usuarioDAO = df.getUsuarioDAO();
	 	
	//	return usuarioDAO.emailExist();
		
		return true;
		//return false;
		
		
	}

}
