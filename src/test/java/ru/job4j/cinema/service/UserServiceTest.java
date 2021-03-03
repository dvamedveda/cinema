package ru.job4j.cinema.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.store.DatabaseUpdater;

public class UserServiceTest {
    @BeforeClass
    public static void setUp() {
        DatabaseUpdater databaseUpdater = new DatabaseUpdater(ServiceSettings.TEST_DB_FILE);
        databaseUpdater.updateDatabase();
    }

    @Test
    public void whenDoPaymentThenCorrect() {
        String userName = "some_user";
        String userTel = "some_tel";
        UserService userService = new UserService(ServiceSettings.TEST_DB_FILE);
        HallService hallService = new HallService(ServiceSettings.TEST_DB_FILE);
        userService.doPayment(userName, userTel, 2, 2);
        Assert.assertTrue(hallService.isReserved(2, 2));
        userService.removeUser(userTel);
        Assert.assertFalse(hallService.isReserved(2, 2));
    }
}