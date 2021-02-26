create table hall (
    id serial primary key,
    row integer,
    col integer,
    reserved boolean,
    reserved_by integer references account(id) default 0
)