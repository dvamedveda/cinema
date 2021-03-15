-- Обновление, создающее таблицу для хранения пользователей кинотеатра.

-- Создание таблицы.
create table account
(
    id         serial primary key,
    name       text,
    tel_number text
);

-- Создание пользователя по умолчанию.
insert into account(id, name, tel_number)
values (0, 'default_user', 'default_tel')