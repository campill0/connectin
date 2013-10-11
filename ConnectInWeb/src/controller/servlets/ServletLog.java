package controller.servlets;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletLog
 */

@WebServlet("/ServletLog")
public class ServletLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String pathAlFicheroLog;
	public void init(ServletConfig config){
    	try {
			super.init(config);
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	//getServletConfig().getServletContext().getRealPath("/");
    	pathAlFicheroLog =getServletConfig().getServletContext().getRealPath("/")+"WEB-INF\\acceso.log";
    	System.out.println(">>>>>>>>>>>>>>> "+pathAlFicheroLog);
    }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLog() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String log = (String)request.getAttribute("log");
		BufferedWriter bw = null;
		System.out.println(pathAlFicheroLog);
		try {
			bw = new BufferedWriter(new FileWriter(pathAlFicheroLog, true));
			bw.write(log);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
