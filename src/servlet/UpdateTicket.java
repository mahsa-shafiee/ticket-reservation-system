package servlet;

import model.dao.TicketDao;
import model.entity.Ticket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@WebServlet(name = "UpdateTicket")
public class UpdateTicket extends HttpServlet {
    TicketDao ticketDao = new TicketDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<head>" +
                "<title>Update Ticket</title>" +
                "<link rel=\"stylesheet\" href=\"styles.css\">" +
                "</head>");

        HttpSession session = request.getSession(false);
        if (Objects.nonNull(session)) {

            int id = (int) session.getAttribute("id");
            String owner = request.getParameter("owner");
            String origin = request.getParameter("origin");
            String destination = request.getParameter("destination");
            String dateStr = request.getParameter("date");
            int flight_number = Integer.parseInt(request.getParameter("flight_number"));

            try {
                Date date = new SimpleDateFormat("yyyy/MM/dd").parse(dateStr);
                Ticket ticket = new Ticket(owner, origin, destination, date, flight_number);
                ticket.setId(id);

                ticketDao.update(ticket);
                writer.println("<br><br><br><br><br>" +
                        "<center><h1>SUCCESSFULLY UPDATED TICKET WITH ID " + id + "</h1>" +
                        "<br><br><a href=\"ticketSystem.html\">Back to home</a>");

            } catch (ParseException | CloneNotSupportedException e) {
                e.printStackTrace();
            }

        } else writer.println("session is killed!");
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<head>" +
                "<title>Update Ticket</title>" +
                "<link rel=\"stylesheet\" href=\"styles.css\">" +
                "</head>");

        int ticketId = Integer.parseInt(request.getParameter("id"));
        Ticket ticket = ticketDao.findById(ticketId);

        if (ticket != null) {
            HttpSession session = request.getSession();
            session.setAttribute("id", ticketId);

            writer.println("<form method=\"POST\">\n" +
                    "       Owner:<br><input type=\"text\" name=\"owner\" required><br>\n" +
                    "       Origin:<br><input type=\"text\" name=\"origin\" required><br>\n" +
                    "       Destination:<br><input type=\"text\" name=\"destination\" required><br>\n" +
                    "       Date:<br><input type=\"text\" name=\"date\" required><br>\n" +
                    "       Flight Number:<br><input type=\"number\" name=\"flight_number\" required><br>\n" +
                    "       <center><br><input type=\"submit\" value=\"Update Ticket\" formaction=\"UpdateTicket\">" +
                    "       <center><br><input type=\"submit\" value=\"Cancel\" formaction=\"ticketSystem.html\" formnovalidate>" +
                    "       </center></form>");
        } else {

            writer.println("<center><h3>There is no such ticket ID!</h3>");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ticketSystem.html");
            requestDispatcher.include(request, response);
        }
        writer.close();
    }
}