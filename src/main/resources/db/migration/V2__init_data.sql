insert into users
values (nextval('users_user_id_seq'),
        'admin',
        '$2a$12$c8/FWJWGBWvoK3Acug3i1uwYHtyA5lziUTqNHBoBZmqbJqteDWCXu',
        now(),
        true);

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN'),
       (nextval('authorities_authority_id_seq'), 'USER');

insert into users_authorities
values (1, 1),
       (1, 2);

insert into types
values (nextval('types_type_id_seq'), 'Mammal'),
       (nextval('types_type_id_seq'), 'Amphibian'),
       (nextval('types_type_id_seq'), 'Bird'),
       (nextval('types_type_id_seq'), 'Reptile'),
       (nextval('types_type_id_seq'), 'Invertebrate'),
       (nextval('types_type_id_seq'), 'Fish');

insert into breeds
values (nextval('breeds_breed_id_seq'), 'Cat', 1),
       (nextval('breeds_breed_id_seq'), 'Dog', 1),
       (nextval('breeds_breed_id_seq'), 'Monkey', 1),

       (nextval('breeds_breed_id_seq'), 'Frog', 2),
       (nextval('breeds_breed_id_seq'), 'Toad', 2),
       (nextval('breeds_breed_id_seq'), 'Salamander', 2),

       (nextval('breeds_breed_id_seq'), 'Eagle', 3),
       (nextval('breeds_breed_id_seq'), 'Pigeon', 3),
       (nextval('breeds_breed_id_seq'), 'Owl', 3),

       (nextval('breeds_breed_id_seq'), 'Turtle', 4),
       (nextval('breeds_breed_id_seq'), 'Lizard', 4),
       (nextval('breeds_breed_id_seq'), 'Snake', 4),

       (nextval('breeds_breed_id_seq'), 'Centipede', 5),
       (nextval('breeds_breed_id_seq'), 'Freshwater Sponge', 5),
       (nextval('breeds_breed_id_seq'), 'Earthworm', 5),

       (nextval('breeds_breed_id_seq'), 'Carp', 6),
       (nextval('breeds_breed_id_seq'), 'Salmon', 6),
       (nextval('breeds_breed_id_seq'), 'Pike', 6);

insert into animals
values (nextval('animals_animal_id_seq'), 'Max', 4, 7.3, 1, 1),
       (nextval('animals_animal_id_seq'), 'Tom', 1, 1.1, 1, 2),
       (nextval('animals_animal_id_seq'), 'Peter', 9, 6.3, 1, 3),

       (nextval('animals_animal_id_seq'), 'Mary', 1, 0.2, 2, 4),
       (nextval('animals_animal_id_seq'), 'Greta', 9, 0.4, 2, 5),
       (nextval('animals_animal_id_seq'), 'Mia', 9, 0.3, 2, 6),

       (nextval('animals_animal_id_seq'), 'Matt', 9, 2, 3, 7),
       (nextval('animals_animal_id_seq'), 'Michael', 9, 1, 3, 8),
       (nextval('animals_animal_id_seq'), 'Cole', 9, 3, 3, 9),

       (nextval('animals_animal_id_seq'), 'Thea', 9, 20, 4, 10),
       (nextval('animals_animal_id_seq'), 'Charlotte', 9, 6, 4, 11),
       (nextval('animals_animal_id_seq'), 'Pia', 9, 2, 4, 12),

       (nextval('animals_animal_id_seq'), 'Lars', 9, 0.1, 5, 13),
       (nextval('animals_animal_id_seq'), 'Jo', 9, 0.2, 5, 14),
       (nextval('animals_animal_id_seq'), 'Jon', 9, 0.05, 5, 15),

       (nextval('animals_animal_id_seq'), 'Maggie', 9, 0.6, 6, 16),
       (nextval('animals_animal_id_seq'), 'Marita', 9, 0.9, 6, 17),
       (nextval('animals_animal_id_seq'), 'Julie', 9, 1, 6, 18);


