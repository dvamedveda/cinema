package ru.job4j.cinema.listener;

import ru.job4j.cinema.service.ServiceSettings;
import ru.job4j.cinema.store.DatabaseUpdater;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Слушатель контекста, выполнящий при запуске приложения проверку и обновление бд при необходимости.
 */
public class DatabasePreparationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseUpdater databaseUpdater = new DatabaseUpdater(ServiceSettings.DB_FILE);
        databaseUpdater.updateDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}