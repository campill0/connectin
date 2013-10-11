package controller.servlets.filters;


import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Usuario;



@WebFilter( urlPatterns={"/login.faces","/login_error_max_attempts.faces","/login_error.faces","/register.faces"})
public class LoggedUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {    
        System.out.println("filtro");
    	System.out.println("entré logged");
    	HttpServletRequest req = (HttpServletRequest) request;
        Usuario auth = (Usuario) req.getSession().getAttribute("loggedUser");

    //	log(request.getServletContext(),request, response, "algo");
    	
        if (auth == null) {
        	System.out.println("entré");
            // User is logged in, so just continue request.
            chain.doFilter(request, response);
        } else {
            // User is not logged in, so redirect to index.
        	System.out.println("no entré");
        	HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(req.getContextPath() + "/home.faces");
        }
        
    }
 

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}