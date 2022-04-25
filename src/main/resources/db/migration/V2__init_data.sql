insert into users
values (
        nextval('users_user_id_seq'),
        'admin',
        '$2a$12$c8/FWJWGBWvoK3Acug3i1uwYHtyA5lziUTqNHBoBZmqbJqteDWCXu',
        now(),
        true
);

insert into authorities
values
(nextval('authorities_authority_id_seq'), 'ADMIN'),
(nextval('authorities_authority_id_seq'), 'USER');

insert into users_authorities
values (1, 1);

insert into users_authorities
values (1, 2);

