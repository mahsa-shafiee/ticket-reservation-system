package servlet;

import model.dao.TicketDao;
import model.entity.Ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "BookTicket")
public class BookTicket extends HttpServlet {
    TicketDao ticketDao = new TicketDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<head>" +
                "<title>Book Ticket</title>" +
                "<link rel=\"stylesheet\" href=\"styles.css\">" +
                "</head>");

        String owner = request.getParameter("owner");
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String dateStr = request.getParameter("date");
        int flight_number = Integer.parseInt(request.getParameter("flight_number"));

        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
            Ticket ticket = new Ticket(owner, origin, destination, date, flight_number);
            int id = ticketDao.insert(ticket);
            writer.println("<br><br><br><br><br>" +
                    "<center><h1>TICKET ID = " + id + "</h1>" +
                    "<br><br><a href=\"ticketSystem.html\">Back to home</a>");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
