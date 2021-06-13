create table brand
(
    id   serial primary key,
    name varchar(50) not null
);

create table model
(
    id       serial primary key,
    name     varchar(50 )               not null,
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

create table i_user
(
    id       serial primary key,
    name     varchar(50)        not null,
    email    varchar(50) unique not null,
    password varchar(50)        not null
);

--truncate table i_user restart identity cascade;
