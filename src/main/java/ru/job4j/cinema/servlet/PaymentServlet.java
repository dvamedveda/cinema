package ru.job4j.cinema.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.service.ServiceSettings;
import ru.job4j.cinema.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

public class PaymentServlet extends HttpServlet {
    private static UserService service = new UserService(ServiceSettings.DB_FILE);
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/payment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("username");
        String tel = req.getParameter("phone");
        int x = Integer.parseInt(req.getParameter("placex"));
        int y = Integer.parseInt(req.getParameter("placey"));
        if (service.doPayment(name, tel, x, y)) {
            LOGGER.info("Reserving place " + y + " in row " + x + " by user " + name + " with tel. number " + tel + ": SUCCESS");
            resp.sendRedirect("index.do");
        } else {
            LOGGER.info("Reserving place " + y + " in row " + x + " by user " + name + " with tel. number " + tel + ": FAILED");
            LOGGER.info("Redirect user " + name + " to index.do with result=failed");
            resp.sendRedirect("index.do" + "?result=failed");
        }
    }
}