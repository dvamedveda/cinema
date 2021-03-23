-- Обновление, создающее таблицу для хранения состояния мест кинозала.

create table hall
(
    id          serial primary key,
    row         integer not null,
    col         integer not null,
    reserved    boolean not null,
    reserved_by integer references account (id),
    constraint check_place unique (row, col)
)