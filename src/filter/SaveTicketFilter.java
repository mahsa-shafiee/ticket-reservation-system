package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "SaveTicketFilter")
public class SaveTicketFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;

        if (httpServletRequest.getMethod().equals("POST")) {

            String owner = req.getParameter("owner");
            String origin = req.getParameter("origin");
            String destination = req.getParameter("destination");
            String dateStr = req.getParameter("date");
            String flight_number = req.getParameter("flight_number");

            if (validateInput(flight_number, owner, origin, destination, dateStr))
                chain.doFilter(req, resp);
            else {
                writer.println("<center><h3>You entered invalid input!Check flight number and date again...</h3></center>");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("ticketSystem.html");
                requestDispatcher.include(req, resp);
            }

        } else chain.doFilter(req, resp);
        writer.close();
    }

    public void init(FilterConfig config) throws ServletException {
    }

    private boolean validateInput(String flight_number, String owner, String origin, String destination, String dateStr) {
        return validateFlightNumber(flight_number) &&
                validateOwner(owner) &&
                validateLocation(origin) &&
                validateLocation(destination) &&
                validateDate(dateStr);
    }

    private boolean validateDate(String dateStr) {
        return dateStr.matches("^((139|140)\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])$");
    }

    private boolean validateLocation(String origin) {
        return origin.length() < 20 && origin.chars().allMatch(Character::isLetter);
    }

    private boolean validateOwner(String owner) {
        return owner.length() < 30 && owner.chars().allMatch(Character::isLetter);
    }

    private boolean validateFlightNumber(String flight_number) {
        return flight_number.length() == 3 &&
                flight_number.chars().allMatch(Character::isDigit) &&
                flight_number.charAt(0) != '0';
    }
}
