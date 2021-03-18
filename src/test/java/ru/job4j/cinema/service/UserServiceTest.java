package ru.job4j.cinema.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.store.DatabaseUpdater;

/**
 * Тесты класса UserService.
 */
public class UserServiceTest {

    /**
     * Подготовка базы данных для тестов.
     */
    @BeforeClass
    public static void setUp() {
        DatabaseUpdater databaseUpdater = new DatabaseUpdater(ServiceSettings.TEST_DB_FILE);
        databaseUpdater.updateDatabase();
    }

    /**
     * Проверка оплаты и резервирования места пользователем в кинозале.
     */
    @Test
    public void whenDoPaymentThenCorrect() {
        String userName = "some_user";
        String userTel = "some_tel";
        MainService mainService = MainService.getInstance(ServiceSettings.TEST_DB_FILE);
        UserService userService = mainService.getUserService();
        HallService hallService = mainService.getHallService();
        userService.doPayment(userName, userTel, 2, 2);
        Assert.assertTrue(hallService.isReserved(2, 2));
        userService.removeUser(userTel);
        Assert.assertFalse(hallService.isReserved(2, 2));
    }
}