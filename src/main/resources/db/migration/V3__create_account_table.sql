create table account (
    id serial primary key,
    name text,
    tel_number text,
    reserved_place integer references hall(id)
)