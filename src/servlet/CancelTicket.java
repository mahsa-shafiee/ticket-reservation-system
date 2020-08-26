package servlet;

import model.dao.TicketDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CancelTicket")
public class CancelTicket extends HttpServlet {
    TicketDao ticketDao = new TicketDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<head>" +
                "<title>Cancel Ticket</title>" +
                "<link rel=\"stylesheet\" href=\"styles.css\">" +
                "</head>");

        int id = Integer.parseInt(request.getParameter("id"));

        boolean isSuccessFull = ticketDao.delete(id);
        if (isSuccessFull) {
            writer.println("<br><br><br><br><br>" +
                    "<center><h1>SUCCESSFULLY CANCELED TICKET WITH ID " + id + ".</h1>" +
                    "<br><br><a href=\"ticketSystem.html\">Back to home</a></center>");
        } else {

            writer.println("<center><h3>There is no such ticket ID!</h3>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ticketSystem.html");
            requestDispatcher.include(request, response);
        }

        writer.close();
    }
}