package ru.job4j.cinema.model;

public class HallDTO {
    private volatile Place[][] placeList;

    public HallDTO(Place[][] places) {
        this.placeList = places;
    }

    public Place getPlace(int x, int y) {
        return placeList[x][y];
    }

    public void updatePlace(Place place) {
        this.placeList[place.getX() - 1][place.getY() - 1] = place;

    }

    public Place[][] getPlaceArray() {
        return this.placeList;
    }
}