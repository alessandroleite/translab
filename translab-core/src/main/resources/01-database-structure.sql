--
--     Copyright (C) 2015  the original author or authors.
--
--     This program is free software: you can redistribute it and/or modify
--     it under the terms of the GNU General Public License as published by
--     the Free Software Foundation, either version 3 of the License,
--     any later version.
--
--     This program is distributed in the hope that it will be useful,
--     but WITHOUT ANY WARRANTY; without even the implied warranty of
--     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--     GNU General Public License for more details.
--
--     You should have received a copy of the GNU General Public License
--     along with this program.  If not, see <http://www.gnu.org/licenses/>
--

create table continent
(
  id serial not null primary key,
  name varchar(20) not null,
  acronym varchar(8)
);

create unique index idx_continent_name on continent (name);

create table country
(
  id serial not null primary key,
  continent_id integer not null references continent(id),
  name varchar(30) not null,
  acronym varchar(8) not null
);

create unique index idx_country_name on country (name);
create unique index idx_country_acronym on country (acronym);
create index idx_country_continent_id on country (continent_id);

create table city
(
  id serial not null primary key,
  country_id integer not null references country(id),
  name varchar(100) not null
);

create unique index idx_city_name on city (name);
create index idx_city_country_id on city (country_id);

create table airport
(
  id serial not null primary key,
  city_id integer not null references city(id),
  name varchar(180) not null,
  acronym varchar(4) not null,
  description text
);

create index idx_aiport_city_id on airport (city_id);
create unique index idx_aiport_acronym on airport (acronym);
create unique index idx_aiport_name on airport (city_id, name);