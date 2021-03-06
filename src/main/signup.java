package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.DbConnect;


@WebServlet("/signup")
public class signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public signup() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        String email = request.getParameter("email");  
        String password = request.getParameter("password");  
        String name=request.getParameter("name");
        ResultSet rs = null;
    	Connection connection = null;
    	Statement s = null;
    	
    	
    	Cookie[] cookies = request.getCookies();
	    if (cookies != null)
	        for (Cookie cookie : cookies) {
	            cookie.setValue("");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
    	
    	try {
    		connection = DbConnect.getConnection();
    		s = connection.createStatement();
			s.executeQuery("use "+DbConnect.dbName+";");
			String query = "INSERT INTO USERS (NAME,PASS,EMAIL) VALUES(?,?,?)";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setString (1, name);
		    preparedStmt.setString (2, password);
		    preparedStmt.setString (3, email);
		    int x = preparedStmt.executeUpdate();
			if (x == 0) { 
				out.println("<font color=red>Email Id Already exist</font>");
	        	getServletContext().getRequestDispatcher("/home.html").include(request, response);
			}
			else {
				
				rs = s.executeQuery("select * from USERS where EMAIL = '"+email+"'");
		        while(rs.next()) {
		        	
		        	
		        
		        response.addCookie(new Cookie("uid",rs.getString("UID")));
		        response.addCookie(new Cookie("name",rs.getString("NAME")));
		        response.addCookie(new Cookie("email",rs.getString("EMAIL")));

				
		        }
		        System.out.println("User Created");
		        response.sendRedirect("reception");

			}
    		
    	}
    	
    	catch(SQLIntegrityConstraintViolationException e) {
    		out.println("<font color=red>EMAIL already Exists</font>");
        	getServletContext().getRequestDispatcher("/home.html").forward(request, response);
			
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		out.println("<font color=red>Error in signup</font>");
    		getServletContext().getRequestDispatcher("/home.html").include(request, response);
    	}
    	
        
        
        out.close();  
		
		
		
	}

}
