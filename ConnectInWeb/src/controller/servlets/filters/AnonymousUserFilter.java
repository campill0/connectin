package controller.servlets.filters;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Usuario;



//@WebFilter("/*.faces")
@WebFilter(urlPatterns={"/home.faces","/myaccount.faces","/auxiliary.faces","/discussiondetail.faces","/groupdetail.faces","/groups.faces","/messages.faces","/ejb2cmp2.faces"})
public class AnonymousUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {    
        System.out.println("filtro");
    	HttpServletRequest req = (HttpServletRequest) request;
    	 String path = req.getServletPath();

        	 
        	 
        	 Usuario auth = (Usuario) req.getSession().getAttribute("loggedUser");
        	 System.out.println(auth);
             if (auth != null) {
             	System.out.println("entré 1");
                 // User is logged in, so just continue request.
                 chain.doFilter(request, response);
             } else {
                 // User is not logged in, so redirect to index.
             	System.out.println("no entré 1");
             	HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(req.getContextPath() + "/login.faces");
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