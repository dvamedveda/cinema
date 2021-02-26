package ru.job4j.cinema.model;

public class Place {
    private int x;
    private int y;
    private volatile boolean checked;
    private volatile boolean reserved;
    private int reservedBy = 0;

    public Place(int x, int y) {
        this.x = x;
        this.y = y;
        this.checked = false;
        this.reserved = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(int reservedBy) {
        this.reservedBy = reservedBy;
    }
}