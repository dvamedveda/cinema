package ru.job4j.cinema.store;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.model.UserDTO;
import ru.job4j.cinema.service.ServiceSettings;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Тесты класса для работы с кинозалом персистентного слоя.
 */
public class HallDAOTest {

    /**
     * Подготовка базы данных для тестов.
     */
    @BeforeClass
    public static void setUp() {
        DatabaseUpdater updater = new DatabaseUpdater(ServiceSettings.TEST_DB_FILE);
        updater.updateDatabase();
    }

    /**
     * Проверка сохранения мест в кинозале.
     */
    @Test
    public void whenSavePlaceThenSaveCorrect() throws SQLException {
        Store store = PgStore.getInst(ServiceSettings.TEST_DB_FILE);
        HallDAO hallDAO = new HallDAO(store);
        UserDAO userDAO = new UserDAO(store);
        UserDTO newUser = userDAO.saveUser(new UserDTO("test", "123"));
        List<Place> emptyPlaces = hallDAO.getReservedPlaces();
        Assert.assertThat(emptyPlaces.size(), is(0));
        Place reservedPlace = new Place(1, 1);
        reservedPlace.setReserved(true);
        reservedPlace.setReservedBy(newUser.getId());
        hallDAO.savePlace(reservedPlace);
        List<Place> reservedPlaces = hallDAO.getReservedPlaces();
        Assert.assertThat(reservedPlaces.size(), is(1));
        Assert.assertEquals(reservedPlaces.get(0).getX(), reservedPlace.getX());
        Assert.assertEquals(reservedPlaces.get(0).getY(), reservedPlace.getY());
        Assert.assertEquals(reservedPlaces.get(0).getReservedBy(), reservedPlace.getReservedBy());
        Assert.assertEquals(reservedPlaces.get(0).isReserved(), reservedPlace.isReserved());
        hallDAO.unreservePlaces(newUser.getId());
        userDAO.deleteUser(newUser.getId());
    }
}