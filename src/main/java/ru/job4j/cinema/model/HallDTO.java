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
        this.placeList[place.getX()][place.getY()] = place;

    }

    public Place[][] getPlaceList() {
        return this.placeList;
    }
}