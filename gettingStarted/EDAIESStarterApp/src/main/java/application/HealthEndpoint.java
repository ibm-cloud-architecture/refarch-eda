package application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/health")
public class HealthEndpoint extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        /*
        if (!healthy) {
            response.getWriter().append("{\"status\":\"DOWN\"}");
            response.setStatus(response.SC_SERVICE_UNAVAILABLE);
        }
        */
        response.getWriter().append("{\"status\":\"UP\"}");
        response.setStatus(response.SC_OK);
    }

}