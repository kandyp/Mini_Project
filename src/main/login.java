package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import init.DbConnect;


@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        String email = request.getParameter("email");  
        String password = request.getParameter("password");  
        ResultSet rs = null;
    	Connection connection = null;
    	Statement s = null;
    	
    	try {
    		connection = DbConnect.getConnection();
    		s = connection.createStatement();
			s.executeQuery("use "+DbConnect.dbName+";");
			rs = s.executeQuery("select * from users where EMAIL = '"+email+"'");
			if (!rs.next()) { 
				out.println("<font color=red>Email Id not available</font>");
	        	getServletContext().getRequestDispatcher("/home.html").include(request, response);
	        
			}
			else {
				if(password.equals(rs.getString("PASS"))){  
			       
			        HttpSession session=request.getSession(true);  
			        session.setAttribute("uid",rs.getInt("UID"));
			        session.setAttribute("name", rs.getString("NAME"));
		        	getServletContext().getRequestDispatcher("/reception").forward(request, response);
		        	System.out.println("user logged in "+rs.getInt("UID"));
			        
			        }  
			        else{
			        	out.println("<font color=red>Password is incorrect</font>");
			        	getServletContext().getRequestDispatcher("/home.html").include(request, response);
			        }  
				
			}
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        
        
        out.close();  
		
	}

}
