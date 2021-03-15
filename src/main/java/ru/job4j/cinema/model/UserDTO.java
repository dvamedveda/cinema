package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных пользователя.
 * Служит для передачи пользователя в приложении cinema.
 */
public class UserDTO {

    /**
     * Идентификатор пользователя.
     */
    private int id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Телефонный номер пользователя.
     */
    private String telNumber;

    /**
     * Конструктор пустого пользователя.
     */
    public UserDTO() {
        this.id = 0;
        this.name = "";
        this.telNumber = "";
    }

    /**
     * Конструктор для сохранения нового пользователя.
     *
     * @param name      имя нового пользователя.
     * @param telNumber телефон нового пользователя.
     */
    public UserDTO(String name, String telNumber) {
        this.id = 0;
        this.name = name;
        this.telNumber = telNumber;
    }

    /**
     * Получить идентификатор пользователя.
     *
     * @return идентификатор пользователя в бд.
     */
    public int getId() {
        return id;
    }

    /**
     * Задать идентификатор пользователя.
     *
     * @param id идентификатор.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получить имя пользователя.
     *
     * @return имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Задать новое имя пользователя.
     *
     * @param name имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить телефон пользователя.
     *
     * @return номер телефона.
     */
    public String getTelNumber() {
        return telNumber;
    }

    /**
     * Задать новый телефон пользователя.
     *
     * @param telNumber новый номер телефона.
     */
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    /**
     * Сравнить пользователя с другим объектом.
     *
     * @param o другой объект.
     * @return результат сравнения.
     */
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
                && Objects.equals(name, userDTO.name)
                && Objects.equals(telNumber, userDTO.telNumber);
    }

    /**
     * Вычислить хеш-код пользователя.
     *
     * @return хеш-код пользователя.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, telNumber);
    }
}