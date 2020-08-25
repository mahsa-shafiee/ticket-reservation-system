package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "TicketIdFilter")
public class TicketIdFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;

        if (httpServletRequest.getMethod().equals("GET")) {

            String ticketId = req.getParameter("id");

            if (ticketId.chars().allMatch(Character::isDigit) && Integer.parseInt(ticketId) > 0)
                chain.doFilter(req, resp);
            else {
                writer.println("<center><h3>You entered invalid id!Try again...</h3></center>");
                RequestDispatcher rd = req.getRequestDispatcher("ticketSystem.html");
                rd.include(req, resp);
            }

        } else chain.doFilter(req, resp);
        writer.close();
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
