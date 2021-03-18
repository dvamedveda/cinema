package ru.job4j.cinema.service;

import ru.job4j.cinema.model.UserDTO;
import ru.job4j.cinema.store.PgStore;
import ru.job4j.cinema.store.UserDAO;

/**
 * Сервисный класс для работы с пользователем кинотеатра.
 */
public class UserService {

    /**
     * Объект персистентного слоя для сохранения и получения данных о пользователе.
     */
    private final UserDAO userDAO;

    /**
     * Объект сервисного слоя для работы с кинозалом.
     */
    private final HallService hallService;

    /**
     * Инициализация сервисного класса пользователей.
     * @param config файл конфигурации базы данных.
     * @param hallService объект сервиса кинозала, данные которого используются.
     */
    public UserService(String config, HallService hallService) {
        userDAO = new UserDAO(PgStore.getInst(config));
        this.hallService = hallService;
    }

    /**
     * Получить объект пользователя по имени и телефону.
     * Если такого пользователя не существует, он будет создан.
     *
     * @param name имя пользователя.
     * @param tel  телефон пользователя.
     * @return объект пользователя.
     */
    public UserDTO prepareUser(String name, String tel) {
        UserDTO result;
        if (userDAO.getUser(tel) != null) {
            result = userDAO.getUser(tel);
        } else {
            result = userDAO.saveUser(new UserDTO(name, tel));
        }
        return result;
    }

    /**
     * Выполнить бронирование места за пользователем.
     *
     * @param name имя пользователя.
     * @param tel  телефон пользователя.
     * @param x    ряд в кинозале.
     * @param y    место в ряду в кинозале.
     * @return результат бронирования.
     */
    public boolean doPayment(String name, String tel, int x, int y) {
        boolean result = false;
        if (!hallService.isReserved(x, y)) {
            UserDTO user = prepareUser(name, tel);
            if (hallService.reservePlace(x, y, user.getId())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Удалить пользователя.
     * При удалении выполняется разбронирование мест пользователя.
     *
     * @param tel номер телефона пользователя.
     * @return результат удаления.
     */
    public boolean removeUser(String tel) {
        boolean result = false;
        if (userDAO.getUser(tel) != null) {
            UserDTO user = userDAO.getUser(tel);
            hallService.unreservePlaces(user.getId());
            userDAO.deleteUser(user.getId());
            result = true;
        }
        return result;
    }
}