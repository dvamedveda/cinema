package ru.job4j.cinema.service;

/**
 * Главный сервисный класс, позволяющий получать связанные объекты сервисов.
 */
public class MainService {

    /**
     * Файл конфигурации базы данных.
     */
    private static String configFile;

    /**
     * Объект сервиса для работы с кинозалом.
     */
    private final HallService hallService;

    /**
     * Объект сервиса для работы пользователями.
     */
    private final UserService userService;

    /**
     * Создание синглтона.
     */
    private MainService() {
        hallService = new HallService(configFile);
        userService = new UserService(configFile, hallService);
    }

    /**
     * Класс реализующий шаблон синглтон для главного сервиса.
     */
    private static final class Lazy {
        private static final MainService INST = new MainService();
    }

    /**
     * Метод для получения инстанса главного сервиса.
     *
     * @param config конфигурация базы данных.
     * @return объект главного сервиса.
     */
    public static MainService getInstance(String config) {
        configFile = config;
        return Lazy.INST;
    }

    /**
     * Получить объект сервиса для работы с кинозалом.
     *
     * @return объект сервиса кинозала.
     */
    public HallService getHallService() {
        return hallService;
    }

    /**
     * Получить объект сервиса для работы с пользователями.
     *
     * @return объект сервиса пользователей
     */
    public UserService getUserService() {
        return userService;
    }
}