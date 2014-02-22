package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet(
		urlPatterns = { 
				"/MyServlet", 
				"/servlet"
		}, 
		initParams = { 
				@WebInitParam(name = "param", value = "")
		})
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramString = request.getParameter("param");
		
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter writer = response.getWriter();
		
		writer.write("Menta "+ paramString);
		writer.flush();
		writer.close();
	}

}
