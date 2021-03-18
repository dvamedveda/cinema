package ru.job4j.cinema.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.service.MainService;
import ru.job4j.cinema.service.ServiceSettings;
import ru.job4j.cinema.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер страницы оплаты забронированного места.
 */
public class PaymentServlet extends HttpServlet {

    /**
     * Объект сервисного слоя для работы с пользователем.
     */
    private final UserService service = MainService.getInstance(ServiceSettings.DB_FILE).getUserService();

    /**
     * Логгер для вывода информации о работе приложения.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Отдать представление для оплаты забронированного места.
     *
     * @param req  объект запроса.
     * @param resp объект ответа.
     * @throws ServletException исключения при работе сервлета.
     * @throws IOException      исключения ввода и вывода при работе сервлета.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/payment.jsp").forward(req, resp);
    }

    /**
     * Обработка запроса на оплату забронированного места.
     * После обработки клиент перенаправляется на главную страницу.
     * Если обработка не завершилась бронированием - перенаправление происходит с добавлением параметра ошибки.
     *
     * @param req  объект запроса.
     * @param resp объект ответа.
     * @throws IOException исключения ввода и вывода при работе сервлета.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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