package ru.job4j.cinema.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.model.UserDTO;

import java.sql.*;

/**
 * Класс для взаимодействия с пользователями в базе данных.
 */
public class UserDAO {

    /**
     * Логгер для вывода информации о работе приложения.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Объект хранилища.
     */
    private Store store;

    /**
     * Инициализация класса объектом хранилища.
     *
     * @param store объект хранилища.
     */
    public UserDAO(Store store) {
        this.store = store;
    }

    /**
     * Получить пользователя по номеру телефона.
     *
     * @param number номер телефона.
     * @return объект пользователя или null
     */
    public UserDTO getUser(String number) {
        UserDTO result = null;
        String command = "select * from account where tel_number = ?";
        try (Connection connection = this.store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                statement.setString(1, number);
                statement.execute();
                try (ResultSet resultSet = statement.getResultSet()) {
                    if (resultSet.next()) {
                        UserDTO newUser = new UserDTO();
                        newUser.setId(resultSet.getInt("id"));
                        newUser.setName(resultSet.getString("name"));
                        newUser.setTelNumber(resultSet.getString("tel_number"));
                        result = newUser;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }

    /**
     * Сохранить или обновить пользователя в базе данных.
     *
     * @param user модель данных пользователя.
     * @return объект сохраненного пользователя.
     */
    public UserDTO saveUser(UserDTO user) {
        if (this.getUser(user.getTelNumber()) != null) {
            return this.updateUser(user);
        } else {
            return this.saveNewUser(user);
        }
    }

    /**
     * Обновление существующего пользователя в базе данных.
     *
     * @param user объект пользователя.
     * @return объект сохраненного пользователя.
     */
    private UserDTO updateUser(UserDTO user) {
        String command = "update account set name = ?, tel_number = ? where id = ?";
        try (Connection connection = this.store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getTelNumber());
                statement.setInt(3, user.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return user;
    }

    /**
     * Сохранение нового пользователя в базе данных.
     *
     * @param user объект пользователя.
     * @return объект сохраненного пользователя с новым индентификатором.
     */
    private UserDTO saveNewUser(UserDTO user) {
        String command = "insert into account(name, tel_number) values (?, ?)";
        try (Connection connection = this.store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getTelNumber());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        user.setId(resultSet.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return user;
    }

    /**
     * Удалить пользователя из базы данных.
     *
     * @param id идентификатор пользователя.
     */
    public void deleteUser(int id) {
        String command = "delete from account where id = ?";
        try (Connection connection = this.store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
    }
}