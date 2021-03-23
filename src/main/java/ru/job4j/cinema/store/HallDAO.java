package ru.job4j.cinema.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.model.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий взаимодействия приложения с кинозалом в базе данных.
 */
public class HallDAO {

    /**
     * Логгер для вывода информации о работе приложения.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Объект хранилища.
     */
    private Store store;

    /**
     * Инициализация класса выбранным хранилищем.
     *
     * @param store объект хранилища.
     */
    public HallDAO(Store store) {
        this.store = store;
    }

    /**
     * Загрузить из базы данных состояние всех мест в кинозале.
     *
     * @return список занятых мест кинозала.
     */
    public List<Place> getReservedPlaces() {
        List<Place> result = new ArrayList<>();
        String command = "select * from hall;";
        try (Connection connection = store.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                preparedStatement.execute();
                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        int x = resultSet.getInt("row");
                        int y = resultSet.getInt("col");
                        Place nextPlace = new Place(x, y);
                        boolean isReserved = resultSet.getBoolean("reserved");
                        nextPlace.setReserved(isReserved);
                        int reservedBy = resultSet.getInt("reserved_by");
                        nextPlace.setReservedBy(reservedBy);
                        result.add(nextPlace);
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }

    /**
     * Сохранить место в базе данных.
     * @param place модель места кинозала.
     * @throws SQLIntegrityConstraintViolationException бросается одному из потоков в случае одновременного бронирования.
     */
    public void savePlace(Place place) throws SQLIntegrityConstraintViolationException {
        String command = "insert into hall(row, col, reserved, reserved_by) values (?, ?, ?, ?)";
        try (Connection connection = store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                statement.setInt(1, place.getX());
                statement.setInt(2, place.getY());
                statement.setBoolean(3, place.isReserved());
                statement.setInt(4, place.getReservedBy());
                statement.executeUpdate();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
    }

    /**
     * Освободить места в кинозале, занятые определенным пользователем.
     * @param id идентификатор пользователя.
     */
    public void unreservePlaces(int id) {
        String command = "delete from hall where reserved_by = ?;";
        try (Connection connection = store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
    }
}