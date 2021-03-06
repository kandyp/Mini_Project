package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import init.DbConnect;


@WebServlet("/editor")
public class editor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public editor() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Cookie ck[] = request.getCookies();
		Map<String,Object> m = new HashMap<String,Object>();
		for (int i = 0; i < ck.length; i++) {
			String name = ck[i].getName();
			Object value = ck[i].getValue();
			m.put(name, value);
		}
		if (!m.containsKey("uid"))
			response.sendRedirect("index");
		else {
			ResultSet rs = null;
			String name = (String) m.get("name");
			String email = (String) m.get("email");
			Connection conn = DbConnect.getConnection();
			PrintWriter out = response.getWriter();
			int nid = Integer.parseInt(request.getParameter("id"));
			try {
				Statement s = conn.createStatement();
				s.execute("use " + DbConnect.dbName);
				rs = s.executeQuery("SELECT * FROM NOTES WHERE ID="+nid);
				request.setCharacterEncoding("utf8");
				response.setContentType("text/html"); 
				String data = "Edit here";
				while(rs.next()) {
					data = rs.getString("DATA");
				}
				out.print("<html>\r\n" + 
						"<head>\r\n" + 
						"<title>Editor</title>"
						+ "<script>"
						+ "var data ='"
						+  data
						+ "';"
						+ "var username = '"+name+"';"
						+ "var email = '"+email+"';"
						+ "var nid = "+nid+";"
						+ "</script>");
				s.close();
				rs.close();
				RequestDispatcher rd=request.getRequestDispatcher("/editor.html");  
		        rd.include(request, response);  
				
			}catch(Exception e) {
				e.printStackTrace();
				out.println("<font color=red>Error in Editor</font>");
	        	getServletContext().getRequestDispatcher("/editor.html").forward(request, response);
				}
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
