-- Обновление создающее таблицу с описание кинотеатра.

create table cinema
(
    id     serial primary key,
    name   text,
    places integer
)