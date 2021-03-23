package ru.job4j.cinema.model;

/**
 * Модель для зала кинотеатра.
 * Служит для передачи состояния мест в кинотеатре.
 */
public class HallDTO {
    /**
     * Двумерный массив мест кинотеатра.
     */
    private volatile Place[][] placeList;

    /**
     * Конструктор модели.
     *
     * @param places двумерный массив мест кинотеатра.
     */
    public HallDTO(Place[][] places) {
        for (int row = 0; row < places.length; row++) {
            for (int col = 0; col < places[row].length; col++) {
                places[row][col] = new Place(row + 1, col + 1);
            }
        }
        this.placeList = places;
    }

    /**
     * Получить объект места в кинотеатре по координатам.
     *
     * @param x ряд в зале кинотеатра.
     * @param y место в ряду в зале кинотеатра.
     * @return объект места.
     */
    public Place getPlace(int x, int y) {
        return placeList[x][y];
    }

    /**
     * Обновить состояние месте в кинотеатре.
     *
     * @param place объект обновляемого места.
     */
    public void updatePlace(Place place) {
        this.placeList[place.getX() - 1][place.getY() - 1] = place;

    }

    /**
     * Получить массив мест в кинотеатре.
     *
     * @return двумерный массив мест.
     */
    public Place[][] getPlaceArray() {
        return this.placeList;
    }
}