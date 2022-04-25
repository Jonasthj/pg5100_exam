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