package ru.job4j.cinema.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.MainService;
import ru.job4j.cinema.service.ServiceSettings;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Класс контроллера для обработки AJAX запроса на получение состояния мест кинозала.
 */
public class PlacesServlet extends HttpServlet {

    /**
     * Объект сервисного слоя для работы с кинозалом.
     */
    private final HallService service = MainService.getInstance(ServiceSettings.DB_FILE).getHallService();

    /**
     * Логгер для вывода информации о работе приложения.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Обработка запроса на получение состояния мест кинозала.
     *
     * @param req  объект запроса.
     * @param resp объект ответа.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        String placesAsJson = service.getPlacesAsJson();
        try (PrintWriter out = resp.getWriter()) {
            out.println(placesAsJson);
            out.flush();
        } catch (IOException e) {
            LOGGER.warn(e, e);
        }
    }
}