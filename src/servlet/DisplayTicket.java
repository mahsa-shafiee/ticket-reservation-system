package servlet;

import model.dao.TicketDao;
import model.entity.Ticket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "DisplayTicket")
public class DisplayTicket extends HttpServlet {
    TicketDao ticketDao = new TicketDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<head>" +
                "<title>Display Ticket</title>" +
                "<link rel=\"stylesheet\" href=\"styles.css\">" +
                "</head>");

        int ticketId = Integer.parseInt(request.getParameter("id"));
        Ticket ticket = ticketDao.findById(ticketId);

        if (ticket != null) {

            writer.println("<center><h1>Ticket details</h1>");
            writer.println("<h2>Ticket id: " + ticket.getId());
            writer.println("<br><br>Ticket owner: " + ticket.getOwner());
            writer.println("<br><br>Ticket origin: " + ticket.getOrigin());
            writer.println("<br><br>Ticket destination: " + ticket.getDestination());
            writer.println("<br><br>Ticket date: " + formatDate(ticket.getDate()));
            writer.println("<br><br>Ticket flight number: " + ticket.getFlight_number() + "</h2>");
            writer.println("<a href=\"ticketSystem.html\">Back to home</a>");
        } else {

            writer.println("<center><h3>There is no such ticket ID!</h3>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ticketSystem.html");
            requestDispatcher.include(request, response);
        }

        writer.close();
    }

    private String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
    }
}