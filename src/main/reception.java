package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import init.DbConnect;

@WebServlet("/reception")
public class reception extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public reception() {
		super();

	}

	@SuppressWarnings({ "unchecked", "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		PrintWriter out = response.getWriter();
		Cookie ck[] = request.getCookies();
		Map<String, Object> m = new HashMap<String, Object>();
		for (int i = 0; i < ck.length; i++) {
			String name = ck[i].getName();
			Object value = ck[i].getValue();
			System.out.println(name + "       " + value);
			m.put(name, value);
		}

		if (m.containsKey("uid")) {

			ResultSet rs = null;
			int uid = Integer.parseInt((String) m.get("uid"));
			String name = (String) m.get("name");
			String email = (String) m.get("email");
			Connection conn = DbConnect.getConnection();

			try {
				Statement s = conn.createStatement();
				s.execute("use " + DbConnect.dbName);
				rs = s.executeQuery("SELECT N.ID,N.DATA,N.CT,N.MT FROM NOTES AS N , USER_NOTES AS U\r\n"
						+ "WHERE N.ID = U.NOTES_ID\r\n" + "AND U.USER_ID = " + uid + "\r\n" + "ORDER BY N.MT DESC");
				request.setCharacterEncoding("utf8");

				response.setContentType("text/html");

				JSONArray ja = new JSONArray();
				while (rs.next()) {

					JSONObject jo = new JSONObject();
					jo.put("id", rs.getInt("N.ID"));
					jo.put("data", rs.getString("N.DATA"));
					SimpleDateFormat sdf = new SimpleDateFormat("E HH:mm:ss dd MMM yyyy");
					jo.put("ct", sdf.format(rs.getTimestamp("N.CT")));
					jo.put("mt", sdf.format(rs.getTimestamp("N.MT")));
					ja.add(jo);

				}

				out.print("<html>\r\n" + "<head>\r\n" + "<title>Jnaapti Web App Building in EE</title>" + "<script>"
						+ "var data = " + ja.toJSONString() + ";" + "var username = '" + name + "';" + "var email = '"
						+ email + "';" + "</script>");
				s.close();
				rs.close();
				RequestDispatcher rd = request.getRequestDispatcher("/reception.html");
				rd.include(request, response);

			} catch (Exception e) {
				e.printStackTrace();
				out.println("<font color=red>Error in Reception</font>");
				getServletContext().getRequestDispatcher("/home.html").include(request, response);

			}

		} else {
			response.sendRedirect("index");

		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

}
