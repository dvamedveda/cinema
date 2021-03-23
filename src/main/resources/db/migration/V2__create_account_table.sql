-- Обновление, создающее таблицу для хранения пользователей кинотеатра.

-- Создание таблицы.
create table account
(
    id         serial primary key,
    name       text,
    tel_number text
);