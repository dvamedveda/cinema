package ru.job4j.cinema.store;

/**
 * Класс для демонстрации обновления базы данных.
 */
public class Demo {
    public static void main(String[] args) {
        DatabaseUpdater updater = new DatabaseUpdater("db.properties");
        updater.updateDatabase();
    }
}