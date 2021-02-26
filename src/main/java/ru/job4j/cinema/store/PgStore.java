package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PgStore implements Store {
    private static String configFile;
    private final BasicDataSource pool = new BasicDataSource();

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

    private static final class Lazy {
        private static final Store INST = new PgStore();
    }

    public static Store getInst(String filename) {
        configFile = filename;
        return Lazy.INST;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.pool.getConnection();
    }
}