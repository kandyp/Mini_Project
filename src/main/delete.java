package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import init.DbConnect;




@WebServlet("/delete")
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int x = Integer.parseInt(request.getParameter("id"));
		Statement s = null;
		Connection conn = DbConnect.getConnection();
		try {
			s = conn.createStatement();
			s.execute("use " + DbConnect.dbName);
			String query = "DELETE FROM NOTES WHERE ID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setInt(1, x);
		    preparedStmt.execute();
		    query = "DELETE FROM USER_NOTES WHERE NOTES_ID = ?";
			preparedStmt = conn.prepareStatement(query);
		    preparedStmt.setInt(1, x);
		    preparedStmt.execute();
			s.close();
		}catch(Exception e) {
			e.printStackTrace();}
		
		RequestDispatcher rd = request.getRequestDispatcher("/reception");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
