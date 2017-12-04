package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

@WebServlet("/add")
public class add extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public add() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int nid = Integer.parseInt(request.getParameter("id"));
		Cookie ck[] = request.getCookies();
		Map<String,Object> m = new HashMap<String,Object>();
		for (int i = 0; i < ck.length; i++) {
			String name = ck[i].getName();
			Object value = ck[i].getValue();
			m.put(name, value);
		}
		
		int uid = Integer.parseInt((String) m.get("uid"));
		String data = request.getParameter("data");

		if (nid == 0) {
			Statement s = null;
			Connection conn = DbConnect.getConnection();
			try {
				s = conn.createStatement();
				s.execute("use " + DbConnect.dbName);
				String query = "INSERT INTO NOTES (DATA) VALUES(?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, data);
				preparedStmt.executeUpdate();

				ResultSet rs = conn.prepareStatement("Select * from NOTES order by CT DESC Limit 1").executeQuery();
				while(rs.next()) {
				nid = rs.getInt("ID");
				}
				query = "INSERT INTO USER_NOTES (USER_ID,NOTES_ID) VALUES(?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, uid);
				preparedStmt.setInt(2, nid);
				preparedStmt.execute();

				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				RequestDispatcher rd = request.getRequestDispatcher("/reception");
				rd.forward(request, response);
			}

		} else {

			Statement s = null;
			Connection conn = DbConnect.getConnection();
			try {
				s = conn.createStatement();
				s.execute("use " + DbConnect.dbName);
				String query = "UPDATE NOTES SET DATA=? WHERE ID=?";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, data);
				preparedStmt.setInt(2, nid);
				preparedStmt.execute();

				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			finally {
				RequestDispatcher rd = request.getRequestDispatcher("/reception");
				rd.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
