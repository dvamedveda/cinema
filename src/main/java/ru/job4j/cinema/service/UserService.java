package ru.job4j.cinema.service;

import ru.job4j.cinema.model.UserDTO;
import ru.job4j.cinema.store.PgStore;
import ru.job4j.cinema.store.UserDAO;

public class UserService {
    private final UserDAO userDAO;
    private final HallService hallService;

    public UserService(String config) {
        userDAO = new UserDAO(PgStore.getInst(config));
        hallService = new HallService(config);
    }

    public UserDTO prepareUser(String name, String tel) {
        UserDTO result;
        if (userDAO.getUser(tel) != null) {
            result = userDAO.getUser(tel);
        } else {
            result = userDAO.saveUser(new UserDTO(name, tel));
        }
        return result;
    }

    public boolean doPayment(String name, String tel, int x, int y) {
        boolean result = false;
        if (!hallService.isChecked(x, y) && !hallService.isReserved(x, y)) {
            UserDTO user = prepareUser(name, tel);
            if (hallService.reservePlace(x, y, user.getId())) {
                result = true;
            }
        }
        return result;
    }

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