package ru.job4j.cinema.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер главной страницы приложения cinema.
 */
public class IndexServlet extends HttpServlet {

    /**
     * Отдать представление главной страницы приложения.
     *
     * @param req  объект запроса.
     * @param resp объект ответа.
     * @throws ServletException исключение при работе сервлета.
     * @throws IOException      исключения ввода/вывода при работе сервлета.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}