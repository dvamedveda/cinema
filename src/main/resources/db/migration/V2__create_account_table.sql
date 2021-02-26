create table account (
    id serial primary key,
    name text,
    tel_number text
);

insert into account(id, name, tel_number)
values (0, 'default_user', 'default_tel')