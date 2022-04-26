create table users(
    user_id bigserial primary key,
    user_username varchar(100),
    user_password varchar(100),
    user_created timestamp,
    user_enabled bool
);

create table authorities(
    authority_id bigserial primary key,
    authority_name varchar(50)
);

create table users_authorities(
    user_id bigint,
    authority_id bigint,
    constraint user_id
    foreign key (user_id)
    references users(user_id),

    constraint authority_id
    foreign key (authority_id)
    references authorities(authority_id)
);

create table types(
    type_id bigserial primary key,
    type_name varchar(100)
);

create table breeds(
    breed_id bigserial primary key,
    breed_name varchar(100),
    breed_type_id integer,
    constraint breed_type_id
    foreign key (breed_type_id)
    references types(type_id)
);

create table animals(
    animal_id bigserial primary key,
    animal_name varchar(100),
    animal_age integer,
    animal_weight float,
    animal_type_id integer,
    animal_breed_id integer,
    constraint animal_type_id
    foreign key (animal_type_id)
    references types(type_id),
    constraint animal_breed_id
    foreign key (animal_breed_id)
    references breeds(breed_id)
);

