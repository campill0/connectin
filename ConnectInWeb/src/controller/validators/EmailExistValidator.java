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


@FacesValidator("emailExistValidator")
public class EmailExistValidator implements Validator {
	private static UsuarioDAO usuarioDAO;
	private static DAOFactory df;
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		// TODO Auto-generated method stub
		
	
		EmailValidator ev= EmailValidator.getInstance();
		
		
		if(!ev.isValid(arg2.toString())){
			//<f:validateRegex pattern="[a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]{2,3}"></f:validateRegex>
		//	String message=MessageProvider.getValue("email_not_match_pattern");
			String message=Util.getLocaleString("the_email_address_is_wrong");
			
			FacesMessage msg = new FacesMessage(message, message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		if(emailExistInBD(arg2.toString())){
			
			
			String message=Util.getLocaleString("email_already_in_the_database");
			FacesMessage msg = new FacesMessage(message, message);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}	
	}
public boolean emailExistInBD(String mail){
		try {
			df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
		} catch (DAOException e) {
			// TODO emailExistInDB falta completar el metodo
			e.printStackTrace();
		}
		usuarioDAO = df.getUsuarioDAO();
	 	
	//	return usuarioDAO.emailExist();
		
		
		return false;
		
		
	}

}
