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
                       name varchar not null,
                       surname varchar not null,
                       age integer not null,
                       player_role_id integer not null references player_role (id) ON UPDATE NO ACTION ON DELETE CASCADE,
                       count_games integer not null,
                       count_points integer not null,
                       effectiveness integer not null,
                       player_number integer not null
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

insert into player (name, surname, age, player_role_id, count_games, count_points, effectiveness, player_number)
values
    ('Иванов', 'Иван', 25, 3, 5, 8, 10, 55),
    ('Петров', 'Петр', 25, 2, 2, 4, 9, 4),
    ('Александров', 'Александр', 25, 1, 7, 3, 9, 76)
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

------- SELECT LEAGUE

select
    l.id as league_id,
    l.name as league_name,
    array_to_string(array(select t.name from team t where t.league_id = l.id order by l.id), ', ') as league_teams
from
    league l
        inner join team t on l.id = t.league_id
order by
    l.id

-- SELECT TEAM


select
    t.id as team_id,
    t.name as team_name,
    l.id as league_id,
    l.name as league_name,
    c.id as city_id,
    c.name as city_name,
    array_to_string(array(select concat(p.name, ' ', p.surname) from player p inner join player_team pt on p.id = pt.player_id where pt.team_id = t.id order by p.name), ', ') as team_players
from
    team t
        inner join league l on t.league_id = l.id
        inner join city c on t.city_id = c.id
order by
    t.id

-- SELECT PLAYER

select
    p.id as id,
    p.name as name,
    p.surname as surname,
    p.age as age,
    pr.id as player_role_id,
    pr.name as player_role_name,
    p.count_games as count_games,
    p.count_points as count_points,
    p.effectiveness as effectiveness,
    p.player_number as player_number,
    array_to_string(array(select t.name from team t inner join player_team pt on t.id = pt.team_id where pt.player_id = p.id order by t.name), ', ')  as player_teams
from
    player p
        inner join player_role pr on p.player_role_id = pr.id
order by
    p.id

-------

