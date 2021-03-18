package ru.job4j.cinema.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.model.UserDTO;
import ru.job4j.cinema.store.DatabaseUpdater;

import static org.hamcrest.CoreMatchers.is;


/**
 * Тесты класса HallService.
 */
public class HallServiceTest {

    /**
     * Подготовка базы данных для тестов.
     */
    @BeforeClass
    public static void setUp() {
        DatabaseUpdater updater = new DatabaseUpdater("test_db.properties");
        updater.updateDatabase();
    }

    /**
     * Проверка того, что при создании кинозала все места свободные.
     */
    @Test
    public void whenCreateHallServiceThenAllNotReserved() {
        MainService mainService = MainService.getInstance(ServiceSettings.TEST_DB_FILE);
        HallService hallService = mainService.getHallService();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Assert.assertFalse(hallService.isReserved(x, y));
            }
        }
    }

    /**
     * Проверка резервирования места в кинозале.
     */
    @Test
    public void whenReserveThenSuccess() {
        MainService mainService = MainService.getInstance(ServiceSettings.TEST_DB_FILE);
        HallService hallService = mainService.getHallService();
        UserService userService = mainService.getUserService();
        UserDTO user = userService.prepareUser("tester", "testers_tel");
        hallService.reservePlace(3, 3, user.getId());
        Assert.assertTrue(hallService.isReserved(3, 3));
        userService.removeUser("testers_tel");
        Assert.assertFalse(hallService.isReserved(3, 3));
    }

    /**
     * Проверка получения корректных данных о кинозале, когда он пуст.
     */
    @Test
    public void whenGetJsonAndAllEmptyThenCorrect() {
        MainService mainService = MainService.getInstance(ServiceSettings.TEST_DB_FILE);
        HallService hallService = mainService.getHallService();
        String result = hallService.getPlacesAsJson();
        String expected = emptyHallJson();
        Assert.assertThat(result, is(expected));
    }

    /**
     * Проверка получения корректных данных о кинозале, когда есть занятые места.
     */
    @Test
    public void whenGetJsonAndReservedThenCorrect() {
        MainService mainService = MainService.getInstance(ServiceSettings.TEST_DB_FILE);
        HallService hallService = mainService.getHallService();
        UserService userService = mainService.getUserService();
        UserDTO user = userService.prepareUser("second_tester", "second_testers_tel");
        hallService.reservePlace(1, 1, user.getId());
        String resultReserved = hallService.getPlacesAsJson();
        String expectedReserved = new StringBuilder()
                .append("[")
                .append("[{\"x\":1,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":2,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":2,\"reserved\":true,\"reservedBy\":" + user.getId() + "},")
                .append("{\"x\":2,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":3,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":4,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":5,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":5,\"reserved\":false,\"reservedBy\":0}]")
                .append("]")
                .toString();
        Assert.assertThat(resultReserved, is(expectedReserved));
        userService.removeUser("second_testers_tel");
        String result = hallService.getPlacesAsJson();
        String expected = emptyHallJson();
        Assert.assertThat(result, is(expected));
    }

    /**
     * Вспомогательный метод, содержащий строку с данными пустого кинозала.
     *
     * @return строка в формате JSON
     */
    private String emptyHallJson() {
        return new StringBuilder()
                .append("[")
                .append("[{\"x\":1,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":1,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":2,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":2,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":3,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":3,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":4,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":4,\"y\":5,\"reserved\":false,\"reservedBy\":0}],")
                .append("[{\"x\":5,\"y\":1,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":2,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":3,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":4,\"reserved\":false,\"reservedBy\":0},")
                .append("{\"x\":5,\"y\":5,\"reserved\":false,\"reservedBy\":0}]")
                .append("]")
                .toString();
    }
}