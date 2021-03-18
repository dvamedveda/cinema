package ru.job4j.cinema.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.model.HallDTO;
import ru.job4j.cinema.model.Place;
import ru.job4j.cinema.store.HallDAO;
import ru.job4j.cinema.store.PgStore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс сервисного слоя для работы с кинозалом.
 */
public class HallService {

    /**
     * Логгер для вывода сообщений о работе приложения.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Объект персистентного слоя для сохранения и получения данных о кинозале.
     */
    private final HallDAO hallDAO;

    /**
     * Объект содержащий состояние кинозала.
     */
    private final HallDTO places;

    /**
     * Инициализация сервисного класса для работы с кинозалом.
     *
     * @param config используемая база данных.
     */
    public HallService(String config) {
        hallDAO = new HallDAO(PgStore.getInst(config));
        places = this.hallDAO.getPlaces();
    }

    /**
     * Проверить - забронировано ли место.
     *
     * @param x ряд в кинозале.
     * @param y место в ряду в кинозале.
     * @return забронировано или нет.
     */
    public boolean isReserved(int x, int y) {
        Place place = places.getPlace(x, y);
        return place.isReserved();
    }

    /**
     * Забронировать конкретное место за конкретным пользователем.
     *
     * @param x  ряд в кинозале.
     * @param y  место в ряду.
     * @param id кем бронируется.
     * @return удалось ли забронировать.
     */
    public boolean reservePlace(int x, int y, int id) {
        boolean result = false;
        synchronized (HallService.class) {
            Place place = places.getPlace(x, y);
            if (!place.isReserved()) {
                place.setReserved(true);
                place.setReservedBy(id);
                places.updatePlace(place);
                hallDAO.savePlaces(places);
                result = true;
            }
        }
        return result;
    }

    /**
     * Освободить места, забронированные пользователем.
     *
     * @param id идентификатор пользователя.
     */
    public void unreservePlaces(int id) {
        synchronized (HallService.class) {
            List<Place> placeList = Arrays.stream(places.getPlaceArray()).flatMap(Arrays::stream).collect(Collectors.toList());
            for (Place next : placeList) {
                Place current = places.getPlace(next.getX() - 1, next.getY() - 1);
                if (current.getReservedBy() == id) {
                    current.setReservedBy(0);
                    current.setReserved(false);
                    places.updatePlace(current);
                }
            }
            hallDAO.savePlaces(places);
        }
    }

    /**
     * Получить массив всех мест в кинозале.
     *
     * @return строка в формате JSON.
     */
    public String getPlacesAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        Place[][] placeList = places.getPlaceArray();
        try {
            result = mapper.writeValueAsString(placeList);
        } catch (JsonProcessingException e) {
            LOGGER.warn(e, e);
        }
        return result;
    }
}