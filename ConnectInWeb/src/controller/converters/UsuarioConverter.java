package controller.converters;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import beans.Usuario;

import dao.DAOException;
import dao.DAOFactory;
import dao.UsuarioDAO;



public class UsuarioConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		Usuario  u;
		try {
			DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
			UsuarioDAO usuarioDAO = df.getUsuarioDAO();
			u= usuarioDAO.findUsuario(Long.parseLong(arg2));
					
			return u;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		Usuario u=(Usuario) arg2;
			return u.getId()+"";
		
		
	}

}
