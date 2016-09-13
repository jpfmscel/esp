
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ESP8266
 */
@WebServlet("/servlet")
public class ESP8266 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ESP8266() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		if (status != null) {
			if (status.equalsIgnoreCase("on") || status.equalsIgnoreCase("off")) {
				try {
					ConexaoDB.insertRecordIntoDbUserTable(status);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		String id = request.getParameter("id");
		if (id != null) {

			System.out.println("ID : " + id + " payload back : ");
			String statusDB = null;

			try {
				statusDB = ConexaoDB.getLatest(id);
				response.getWriter().print("|"+statusDB+"|");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println(statusDB);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
