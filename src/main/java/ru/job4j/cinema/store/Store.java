package ru.job4j.cinema.store;

import java.sql.Connection;
import java.sql.SQLException;

public interface Store {
    Connection getConnection() throws SQLException;
}