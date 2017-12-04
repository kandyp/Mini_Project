package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.DbConnect;

@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public login() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		    Cookie[] cookies = request.getCookies();
		    if (cookies != null)
		        for (Cookie cookie : cookies) {
		            cookie.setValue("");
		            cookie.setPath("/");
		            cookie.setMaxAge(0);
		            response.addCookie(cookie);
		        }
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		ResultSet rs = null;
		Connection connection = null;
		Statement s = null;

		try {
			connection = DbConnect.getConnection();
			s = connection.createStatement();
			s.executeQuery("use " + DbConnect.dbName + ";");
			rs = s.executeQuery("select * from users where EMAIL = '" + email + "'");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = rs.getString(i);
			        System.out.print(columnValue + " " + rsmd.getColumnName(i));
			    }
			    System.out.println("-------");
			}
			
			
			/*if (!rs.next()) {
				out.println("<font color=red>Email Id not available</font>");
				getServletContext().getRequestDispatcher("/home.html").include(request, response);

			} else {
				if (password.equals(rs.getString("PASS"))) {
					
					response.addCookie(new Cookie("uid", rs.getString("UID")));
					response.addCookie(new Cookie("name", rs.getString("NAME")));
					response.addCookie(new Cookie("email", rs.getString("EMAIL")));
					System.out.println("Login:user logged in " + rs.getInt("UID"));
					
					
					//test stub----------------
					System.out.println("in login"+rs.getString("UID")+rs.getString("NAME"));
					Cookie ckx[] = request.getCookies();
					Map<String,Object> m = new HashMap<String,Object>();
					for (int i = 0; i < ckx.length; i++) {
						String name = ckx[i].getName();
						Object value = ckx[i].getValue();
						System.out.println(name+"       "+value);
						m.put(name, value);
					}
					
					//-------------------------
					
					
					
					
					getServletContext().getRequestDispatcher("/reception").forward(request, response);
				} else {
					out.println("<font color=red>Password is incorrect</font>");
					getServletContext().getRequestDispatcher("/home.html").include(request, response);
				}

			}*/

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<font color=red>Error in LOGIN</font>");
        	getServletContext().getRequestDispatcher("/home.html").forward(request, response);
			
		}

	}

}
