-- DROP ALL ------------------------------------------------------------------------------------

drop table player_team;
drop table team;
drop table league;
drop table city;
drop table player;
drop table player_role;

-- TABLE CREATIONS -----------------------------------------------------------------------------

create table player_role(
    id serial not null primary key,
    name varchar not null
);

--

create table player(
   id serial primary key,
   fio varchar not null,
   age integer not null,
   player_role_id integer not null references player_role (id) ON UPDATE NO ACTION ON DELETE CASCADE,
   win_count integer default 0,
   loss_count integer default 0
);

--

create table city(
     id serial not null primary key,
     name varchar not null
);

--

create table league(
   id serial not null primary key,
   name varchar not null
);

--

create table team(
     id serial primary key,
     name varchar not null,
     league_id integer not null references league (id) ON UPDATE NO ACTION ON DELETE CASCADE,
     city_id integer not null references city (id) ON UPDATE NO ACTION ON DELETE CASCADE
);

--

create table player_team(
    player_id integer not null references player (id) ON UPDATE NO ACTION ON DELETE CASCADE,
    team_id integer not null references team (id) ON UPDATE NO ACTION ON DELETE CASCADE,
    --
    primary key(player_id, team_id)
);

-- DATA INSERTIONS ------------------------------------------------------------------------

insert into player_role (name)
values
    ('Вратарь'),
    ('Защитник'),
    ('Нападающий')
    returning *;

--

insert into player (fio, age, player_role_id, win_count, lose_count)
values
    ('Иванов Иван Иванович', 25, 3, 0, 300),
    ('Петров Петр Петрович', 25, 2, 300, 0),
    ('Александров Александр Александрович', 25, 1, 150, 150)
    returning *;

--

insert into city (name)
values
    ('Город победителей'),
    ('Город неудачников')
    returning *;

--

insert into league (name)
values
    ('Лига победителей'),
    ('Лига неудачников')
    returning *;

--

insert into team (name, league_id, city_id)
values
    ('Команда победителей', 1, 1),
    ('Команда неудачников', 2, 2)
    returning *;

--

insert into player_team (player_id, team_id)
values
    (1, 1),
    (2, 2),
    (3, 1),
    (3, 2)
    returning *;

-- DATA SELECTIONS ------------------------------------------------------------------------

select * from player_role;
select * from player;
select * from city;
select * from league;
select * from team;
select * from player_team;