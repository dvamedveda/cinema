package ru.job4j.cinema.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.cinema.model.UserDTO;
import ru.job4j.cinema.store.DatabaseUpdater;

import static org.hamcrest.CoreMatchers.is;

public class HallServiceTest {
    @BeforeClass
    public static void setUp() {
        DatabaseUpdater updater = new DatabaseUpdater("test_db.properties");
        updater.updateDatabase();
    }

    @Test
    public void whenCreateHallServiceThenAllNotReserved() {
        HallService hallService = new HallService(ServiceSettings.TEST_DB_FILE);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Assert.assertFalse(hallService.isReserved(x, y));
            }
        }
    }

    @Test
    public void whenReserveThenSuccess() {
        HallService hallService = new HallService(ServiceSettings.TEST_DB_FILE);
        UserService userService = new UserService(ServiceSettings.TEST_DB_FILE);
        UserDTO user = userService.prepareUser("tester", "testers_tel");
        hallService.reservePlace(3,  3, user.getId());
        Assert.assertTrue(hallService.isReserved(3, 3));
        userService.removeUser("testers_tel");
        Assert.assertFalse(hallService.isReserved(3, 3));
    }

    @Test
    public void whenGetJsonAndAllEmptyThenCorrect() {
        HallService hallService = new HallService(ServiceSettings.TEST_DB_FILE);
        String result = hallService.getPlacesAsJson();
        String expected = emptyHallJson();
        Assert.assertThat(result, is(expected));
    }

    @Test
    public void whenGetJsonAndReservedThenCorrect() {
        HallService hallService = new HallService(ServiceSettings.TEST_DB_FILE);
        UserService userService = new UserService(ServiceSettings.TEST_DB_FILE);
        UserDTO user = userService.prepareUser("second_tester", "second_testers_tel");
        hallService.reservePlace(1,  1, user.getId());
        String resultReserved = hallService.getPlacesAsJson();
        String expectedReserved = new StringBuilder()
                .append("[")
                .append("[{\"x\":1,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":2,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":2,\"checked\":false,\"reserved\":true,\"reservedBy\":" + user.getId() + "},")
                .append("{\"x\":2,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":3,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":4,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":5,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}]")
                .append("]")
                .toString();
        Assert.assertThat(resultReserved, is(expectedReserved));
        userService.removeUser("second_testers_tel");
        String result = hallService.getPlacesAsJson();
        String expected = emptyHallJson();
        Assert.assertThat(result, is(expected));
    }

    private String emptyHallJson() {
        return new StringBuilder()
                .append("[")
                .append("[{\"x\":1,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":2,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":3,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":4,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":5,\"y\":1,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":2,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":3,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":4,\"checked\":false,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":5,\"checked\":false,\"reserved\":false,\"reservedBy\":0}]")
                .append("]")
                .toString();
    }
}