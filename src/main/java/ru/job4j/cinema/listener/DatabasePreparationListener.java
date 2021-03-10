package ru.job4j.cinema.listener;

import ru.job4j.cinema.service.ServiceSettings;
import ru.job4j.cinema.store.DatabaseUpdater;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabasePreparationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseUpdater databaseUpdater = new DatabaseUpdater(ServiceSettings.DB_FILE);
        databaseUpdater.updateDatabase();
    }
}