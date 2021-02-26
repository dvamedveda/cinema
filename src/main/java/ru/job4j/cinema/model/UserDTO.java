package ru.job4j.cinema.model;

import java.util.Objects;

public class UserDTO {
    private int id;
    private String name;
    private String telNumber;
    private int reservedPlace;

    public UserDTO() {
        this.id = 0;
        this.name = "";
        this.telNumber = "";
        this.reservedPlace = 0;
    }

    public UserDTO(String name, String telNumber, int reservedPlace) {
        this.id = 0;
        this.name = name;
        this.telNumber = telNumber;
        this.reservedPlace = reservedPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getReservedPlace() {
        return reservedPlace;
    }

    public void setReservedPlace(int reservedPlace) {
        this.reservedPlace = reservedPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id
                && reservedPlace == userDTO.reservedPlace
                && Objects.equals(name, userDTO.name)
                && Objects.equals(telNumber, userDTO.telNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, telNumber, reservedPlace);
    }
}