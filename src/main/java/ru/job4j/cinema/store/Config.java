package ru.job4j.cinema.store;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger LOGGER = LogManager.getLogger();
    private static Properties properties = new Properties();

    public static Properties getConfig(String configFile) {
        loadConfig(configFile);
        return properties;
    }

    private static void loadConfig(String filename) {
        try (InputStream configFile = Config.class.getClassLoader().getResourceAsStream(filename)) {
            properties.load(configFile);
            LOGGER.info("Config loaded.");
            LOGGER.info("JDBC url is: " + properties.getProperty("jdbc.url"));
            LOGGER.info("JDBC username is: " + properties.getProperty("jdbc.username"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}