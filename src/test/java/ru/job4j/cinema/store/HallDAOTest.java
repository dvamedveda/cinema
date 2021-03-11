package ru.job4j.cinema.store;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.model.HallDTO;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.service.ServiceSettings;

public class HallDAOTest {

    @BeforeClass
    public static void setUp() {
        DatabaseUpdater updater = new DatabaseUpdater(ServiceSettings.TEST_DB_FILE);
        updater.updateDatabase();
    }

    @Test
    public void whenSearchPlaceThenSuccess() {
        Store store = PgStore.getInst(ServiceSettings.TEST_DB_FILE);
        HallDAO hallDAO = new HallDAO(store);
        Assert.assertTrue(hallDAO.isHaveSpaces());
    }

    @Test
    public void whenGetPlacesThenAllEmplty() {
        Store store = PgStore.getInst(ServiceSettings.TEST_DB_FILE);
        HallDAO hallDAO = new HallDAO(store);
        HallDTO hallDTO = hallDAO.getPlaces();
        Place[][] places = hallDTO.getPlaceArray();
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[0].length; col++) {
                Assert.assertFalse(places[row][col].isReserved());
            }
        }
    }

    @Test
    public void whenSavePlacesThenSaveCorrect() {
        Store store = PgStore.getInst(ServiceSettings.TEST_DB_FILE);
        HallDAO hallDAO = new HallDAO(store);
        HallDTO hallDTO = hallDAO.getPlaces();
        Place reservedPlace = new Place(1, 1);
        reservedPlace.setReserved(true);
        hallDTO.updatePlace(reservedPlace);
        hallDAO.savePlaces(hallDTO);
        HallDTO result = hallDAO.getPlaces();
        Place[][] places = result.getPlaceArray();
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[0].length; col++) {
                if (row == 0 && col == 0) {
                    Assert.assertTrue(places[row][col].isReserved());
                } else {
                    Assert.assertFalse(places[row][col].isReserved());
                }
            }
        }
        reservedPlace.setReserved(false);
        hallDTO.updatePlace(reservedPlace);
        hallDAO.savePlaces(hallDTO);
        HallDTO rolledPlaceList = hallDAO.getPlaces();
        Place[][] rolledPlaces = rolledPlaceList.getPlaceArray();
        for (int row = 0; row < rolledPlaces.length; row++) {
            for (int col = 0; col < rolledPlaces[0].length; col++) {
                Assert.assertFalse(rolledPlaces[row][col].isReserved());
            }
        }
    }
}