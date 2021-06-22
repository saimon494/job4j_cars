create table brand
(
    id   serial primary key,
    name varchar(50) not null
);

create table model
(
    id       serial primary key,
    name     varchar(50)               not null,
    brand_id int references brand (id) not null
);

create table body
(
    id   serial primary key,
    name varchar(50) not null
);

create table model_body
(
    id       serial primary key,
    model_id int references model (id) not null,
    body_id  int references body (id)  not null
);

create table color
(
    id   serial primary key,
    name varchar(50) not null
);

create table photo
(
    id   SERIAL primary key,
    name varchar(50) not null
);

create table i_user
(
    id       serial primary key,
    name     varchar(50)        not null,
    email    varchar(50) unique not null,
    password varchar(50)        not null
);

create table post
(
    id       serial primary key,
    created  timestamp                  not null,
    user_id  int references i_user (id) not null,
    brand_id int references brand (id)  not null,
    model_id int references model (id)  not null,
    body_id  int references body (id)   not null,
    color_id int references color (id)  not null,
    photo_id int references photo (id),
    mileage  int                        not null,
    status   boolean                    not null
);

insert into brand(name)
values ('Audi'),
       ('Kia'),
       ('Toyota');

insert into model(name, brand_id)
values ('A4', 1),
       ('A6', 1),
       ('Q5', 1),
       ('Rio', 2),
       ('Sorento', 2),
       ('Optima', 2),
       ('Corolla', 3),
       ('Camry', 3),
       ('Rav4', 3);

insert into body(name)
values ('Седан'),
       ('Хэтчбек'),
       ('Универсал'),
       ('Внедорожник');

insert into model_body(model_id, body_id)
values (1, 1),
       (2, 1),
       (2, 3),
       (3, 4),
       (4, 1),
       (4, 2),
       (5, 4),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 4);

insert into color(name)
values ('Белый'),
       ('Черный'),
       ('Серебристый');

--truncate table i_user restart identity cascade;
