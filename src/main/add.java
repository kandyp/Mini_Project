package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import init.DbConnect;


@WebServlet("/add")
public class add extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public add() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int nid = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		int uid = (int) session.getAttribute("uid");
		String data = request.getParameter("data");
		
		if(nid == 0) {
			Statement s = null;
			Connection conn = DbConnect.getConnection();
			try {
				s = conn.createStatement();
				s.execute("use " + DbConnect.dbName);
				String query = "INSERT INTO NOTES (DATA) VALUES(?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
			    preparedStmt.setString (1, data);
			    preparedStmt.executeUpdate();
			    
			    ResultSet rs = conn.prepareStatement("Select top 1 * from NOTES order by CT").executeQuery();
			    
			    int newid = rs.getInt("N.ID");
			    query = "INSERT INTO USER_NOTES (USER_ID,NOTES_ID) VALUES(?,?)";
				preparedStmt = conn.prepareStatement(query);
			    preparedStmt.setInt (1, uid);
			    preparedStmt.setInt (2, newid);
			    preparedStmt.execute();
				
				s.close();
			}catch(Exception e) {
				e.printStackTrace();}
			
		}
		else {
			
			Statement s = null;
			Connection conn = DbConnect.getConnection();
			try {
				s = conn.createStatement();
				s.execute("use " + DbConnect.dbName);
			    String query = "UPDATE NOTES SET DATA=? WHERE ID=?";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt = conn.prepareStatement(query);
			    preparedStmt.setString (1, data);
			    preparedStmt.setInt (2, nid);
			    preparedStmt.execute();
				
				s.close();
			}catch(Exception e) {
				e.printStackTrace();}
			
			
		}
		}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
