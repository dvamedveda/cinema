package ru.job4j.cinema.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.util.Properties;

public class DatabaseUpdater {
    private static final Logger LOGGER = LogManager.getLogger();
    private Properties config;

    public DatabaseUpdater() {
        this.config = Config.getConfig();
    }

    public void updateDatabase() {
        String url = this.config.getProperty("jdbc.url");
        String user = this.config.getProperty("jdbc.username");
        String pass = this.config.getProperty("jdbc.password");
        LOGGER.info("Prepare to update database.");
        Flyway flyway = Flyway.configure().dataSource(url, user, pass).load();
        flyway.migrate();
        LOGGER.info("Database update completed.");
    }
}