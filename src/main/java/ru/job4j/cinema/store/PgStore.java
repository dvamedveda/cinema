package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Реализация хранилища с использованием PostgreSQL.
 */
public class PgStore implements Store {

    /**
     * Название файла конфигурации базы данных.
     */
    private static String configFile;

    /**
     * Пул соединений к базе данных.
     */
    private final BasicDataSource pool = new BasicDataSource();

    /**
     * Инициализация объекта хранилища.
     */
    private PgStore() {
        Properties cfg = Config.getConfig(configFile);
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    /**
     * Вспомогательный класс, реализующий для хранилищац паттерн синглтон.
     */
    private static final class Lazy {
        private static final Store INST = new PgStore();
    }

    /**
     * Получить объект хранилища.
     *
     * @param filename название файла конфига.
     * @return объект хранилища.
     */
    public static Store getInst(String filename) {
        configFile = filename;
        return Lazy.INST;
    }

    /**
     * Получить объект соединения с хранилищем.
     *
     * @return объект соединения.
     * @throws SQLException ошибки при работе с пулом соединений.
     */
    @Override
    public Connection getConnection() throws SQLException {
        return this.pool.getConnection();
    }
}