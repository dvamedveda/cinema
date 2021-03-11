package ru.job4j.cinema.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.model.HallDTO;
import ru.job4j.cinema.model.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HallDAO {
    private static final Logger LOGGER = LogManager.getLogger();
    private Store store;

    public HallDAO(Store store) {
        this.store = store;
    }

    public HallDTO getPlaces() {
        Place[][] array = new Place[this.getMaxRow()][this.getMaxCol()];
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
                        array[x - 1][y - 1] = nextPlace;
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return new HallDTO(array);
    }

    public void savePlaces(HallDTO hall) {
        String command = "update hall set reserved = ?, reserved_by = ? where row = ? and col = ?;";
        try (Connection connection = store.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(command)) {
//                Place[][] places = hall.getPlaceArray();
//                int maxX = places.length;
//                int maxY = places[0].length;
//                for (int row = 0; row < maxX; row++) {
//                    for (int col = 0; col < maxY; col++) {
//                        statement.setBoolean(1, places[row][col].isReserved());
//                        statement.setInt(2, places[row][col].getReservedBy());
//                        statement.setInt(3, places[row][col].getX());
//                        statement.setInt(4, places[row][col].getY());
//                        statement.addBatch();
//                    }
//                }
                List<Place> placeList = Arrays.stream(hall.getPlaceArray()).flatMap(Arrays::stream).collect(Collectors.toList());
                for (Place next : placeList) {
                    statement.setBoolean(1, next.isReserved());
                    statement.setInt(2, next.getReservedBy());
                    statement.setInt(3, next.getX());
                    statement.setInt(4, next.getY());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
    }

    public boolean isHaveSpaces() {
        String command = "select * from hall where reserved = false;";
        boolean result = false;
        try (Connection connection = store.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    result = true;
                }
            }

        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }

    private int getMaxRow() {
        int result = 0;
        String getMaxRow = "select max(\"row\") from hall;";
        try (Connection connection = store.getConnection()) {
            try (PreparedStatement getRow = connection.prepareStatement(getMaxRow)) {
                getRow.execute();
                try (ResultSet rowResult = getRow.getResultSet()) {
                    while (rowResult.next()) {
                        result = rowResult.getInt("max");
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }

    private int getMaxCol() {
        int result = 0;
        String getMaxCol = "select max(\"col\") from hall;";
        try (Connection connection = store.getConnection()) {
            try (PreparedStatement getCol = connection.prepareStatement(getMaxCol)) {
                getCol.execute();
                try (ResultSet colResult = getCol.getResultSet()) {
                    while (colResult.next()) {
                        result = colResult.getInt("max");
                    }
                }
            }

        } catch (SQLException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }
}