package ru.job4j.cinema.model;

/**
 * Модель данных для места в кинотеатре.
 */
public class Place {
    /**
     * Ряд в зале кинотеатра.
     */
    private int x;

    /**
     * Место в ряду в зале кинотеатра.
     */
    private int y;

    /**
     * Признак, забронировано ли место.
     */
    private volatile boolean reserved;

    /**
     * Идентификатор пользователя, кем забронировано место.
     */
    private int reservedBy = 0;

    /**
     * Конструктор объекта места.
     *
     * @param x ряд в кинозале.
     * @param y место в ряду в кинозале.
     */
    public Place(int x, int y) {
        this.x = x;
        this.y = y;
        this.reserved = false;
    }

    /**
     * Получить ряд места в кинозале.
     *
     * @return номер ряда в кинозале.
     */
    public int getX() {
        return x;
    }

    /**
     * Получить номер места в ряду в кинозале.
     *
     * @return номер места в ряду в кинозале.
     */
    public int getY() {
        return y;
    }

    /**
     * Узнать, забронировано ли место.
     *
     * @return да или нет.
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Изменить состояние бронирования места.
     *
     * @param reserved новое состояние.
     */
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /**
     * Узнать, кем забронировано место.
     *
     * @return идентификатор пользователя.
     */
    public int getReservedBy() {
        return reservedBy;
    }

    /**
     * Изменить идентификатор пользователя, за кем теперь забронировано место.
     *
     * @param reservedBy идентификатор пользователя.
     */
    public void setReservedBy(int reservedBy) {
        this.reservedBy = reservedBy;
    }
}