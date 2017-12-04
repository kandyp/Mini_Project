package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Index() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie ck[] = request.getCookies();
		if (ck != null) {
			Map<String, Object> m = new HashMap<String, Object>();
			for (int i = 0; i < ck.length; i++) {
				String name = ck[i].getName();
				Object value = ck[i].getValue();
				m.put(name, value);
			}

			if (m.containsKey("uid")) {
				getServletContext().getRequestDispatcher("/reception").forward(request, response);

			} else {
				getServletContext().getRequestDispatcher("/home.html").forward(request, response);
			}
		}
		else getServletContext().getRequestDispatcher("/home.html").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
