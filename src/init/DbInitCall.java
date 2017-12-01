package init;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DbInitCall
 */
@WebServlet("/DbInitCall")
public class DbInitCall extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public DbInitCall() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		new DbInit();
		System.out.println("----------------------------------------------------Init called");
	}

	
	public void destroy() {
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
