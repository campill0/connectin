package controller.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.backingbeans.LanguageBean;




import dao.DAOException;

import dao.DAOFactory;
import dao.DAOFactory.Type;
import dao.exceptions.LoginClaveIncorrectaException;
import dao.exceptions.LoginUsuarioNoExisteException;

import beans.Usuario;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException{
    	
    	try {
			super.init(config);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	ServletContext app = config.getServletContext();
    	String pathApp = getServletConfig().getServletContext().getRealPath("/");
    	app.setAttribute("path", pathApp);
		
		
		
    }
    
    public void log(HttpServletRequest request, HttpServletResponse response, String texto){
    	
    	System.out.println("log...");
    	Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E d MMM, HH:mm:ss");
        String entradaLog = sdf.format(cal.getTime())+" : "+texto;
        //Se lo enviamos al log para que lo escriba en el fichero
		ServletContext app = getServletConfig().getServletContext();
		RequestDispatcher rd = app.getNamedDispatcher("ServletLog");
		try{
			request.setAttribute("log", entradaLog);
			rd.include(request, response);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sesion = request.getSession(true);
		Integer numeroIntentos = (Integer)sesion.getAttribute("numeroIntentos");
		String redirect="";
		int max=3;
		if(numeroIntentos == null){
			numeroIntentos = 0;
		}
		if(numeroIntentos >= max){
			//System.out.println("intentos");
			log(request, response, "Intento de login desde "+request.getRemoteAddr()+". Ha excedido el número de intenos.");
			
			redirect="login_error_max_attempts.faces";
			response.sendRedirect(response.encodeRedirectURL(redirect));
		
		
		//	response.sendRedirect(response.encodeRedirectURL("intentosExcedido.jsp"));
			return;
		}
	/*	FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "MessageResources");
		String message = bundle.getString("logout");
		System.out.println(message);
		*/
		
		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");
		Usuario u = null;
		try{
			u = DAOFactory.getDAOFactory(Type.JPA).getUsuarioDAO().login(usuario, password);
			
		}
		catch(LoginClaveIncorrectaException e){
			numeroIntentos++;
			log(request, response, "Login incorrecto: usuario "+usuario+" contraseña incorrecta desde "+request.getRemoteAddr()+".");
		
			
			sesion.setAttribute("numeroIntentos", numeroIntentos);
			
			if(numeroIntentos==max){
				redirect="login_error_max_attempts.faces";
			}else{
			redirect="login_error.faces";
			}
			response.sendRedirect(response.encodeRedirectURL(redirect));
			
			// usar crowl
			//response.sendRedirect(response.encodeRedirectURL("errorLogin.jsp"));
			return;
		}
		catch(LoginUsuarioNoExisteException e){
			numeroIntentos++;
			log(request, response, "Login incorrecto: el usuario "+usuario+" no existe desde "+request.getRemoteAddr()+".");	
			sesion.setAttribute("numeroIntentos", numeroIntentos);
			
			if(numeroIntentos==max){
				redirect="login_error_max_attempts.faces";
			}else{
			redirect="login_error.faces";
			}
			response.sendRedirect(response.encodeRedirectURL(redirect));
			return;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log(request, response, "Login correcto del usuario "+u.getLogin()+" desde "+request.getRemoteAddr()+".");
	
	
		sesion.setAttribute("loggedUser", u);
		
		
	     LanguageBean languageBean = (LanguageBean) request.getSession().getAttribute("languageBean");
	     languageBean.setLocaleCode(u.getLocale());
		redirect="home.faces";
		response.sendRedirect(response.encodeRedirectURL(redirect));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


}
