package ru.job4j.cinema.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.model.HallDTO;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.store.HallDAO;
import ru.job4j.cinema.store.PgStore;

import java.util.ArrayList;
import java.util.List;

public class HallService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HallDAO hallDAO;
    private static HallDTO places;

    public HallService(String config) {
        hallDAO = new HallDAO(PgStore.getInst(config));
        refreshPlaces();
    }

    private void refreshPlaces() {
        if (places == null) {
            places = this.hallDAO.getPlaces();
            LOGGER.info("Places updated in service.");
        }
    }

    public boolean isReserved(int x, int y) {
        Place place = places.getPlace(x, y);
        return place.isReserved();
    }

    public boolean isChecked(int x, int y) {
        Place place = places.getPlace(x, y);
        return place.isChecked();
    }

    public boolean checkPlace(int x, int y) {
        boolean result = false;
        Place place = places.getPlace(x, y);
        if (!place.isChecked() && !place.isReserved()) {
            place.setChecked(true);
            places.updatePlace(place);
            result = true;
        } else {
            LOGGER.info("Cannot check this place - is checked or reserved!");
        }
        return result;
    }

    public boolean reservePlace(int x, int y, int id) {
        boolean result = false;
        Place place = places.getPlace(x, y);
        if (!place.isReserved()) {
            place.setReserved(true);
            place.setReservedBy(id);
            places.updatePlace(place);
            hallDAO.savePlaces(places);
            result = true;
        }
        return result;
    }

    public void unreservePlaces(int id) {
        Place[][] placeList = places.getPlaceList();
        for (int x = 0; x < placeList.length; x++) {
            for (int y = 0; y < placeList[0].length; y++) {
                Place next = places.getPlace(x, y);
                if (next.getReservedBy() == id) {
                    next.setReservedBy(0);
                    next.setReserved(false);
                    places.updatePlace(next);
                }
            }
        }
        hallDAO.savePlaces(places);
    }

    public String getPlacesAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        List<Place> placesAsList = new ArrayList<>();
        Place[][] placeList = places.getPlaceList();
        for (int i = 0; i < placeList.length; i++) {
            for (int j = 0; j < placeList[0].length; j++) {
                placesAsList.add(placeList[i][j]);
            }
        }
        try {
            result = mapper.writeValueAsString(placesAsList);
        } catch (JsonProcessingException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }
}