package ru.job4j.cinema.store;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Интерфейс хранилища.
 */
public interface Store {

    /**
     * Получить соединение с хранилищем.
     *
     * @return объект соединения.
     * @throws SQLException ошибки при получении соединения.
     */
    Connection getConnection() throws SQLException;
}