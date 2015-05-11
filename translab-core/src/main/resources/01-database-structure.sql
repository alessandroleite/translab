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
  acronym varchar(8)
);

create unique index idx_country_name on country (name);
--create unique index idx_country_acronym on country (acronym);
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

create table justification_type
(
   id serial not null primary key,
   acronym varchar(4) not null,
   description varchar(100) not null
);

create unique index idx_justification_acronym on justification_type (acronym);


create table airline
(
  id serial not null primary key,
  oaci varchar(3) not null,
  name varchar(180) not null
);

create unique index idx_airline_oaci on airline (oaci);
create unique index idx_airline_name on airline (name);

create table flight
(
   id bigserial not null primary key,
   airline_id integer not null references airline (id),
   airport_departure_id integer not null references airport(id),
   airport_arrival_id integer not null references airport(id),
   justification_type_id integer not null references justification_type(id),
   number varchar(8) not null,
   digit char(1) not null,
   type char(1) not null,
   planned_departure_time timestamp not null,
   real_departure_time timestamp,
   planned_arrival_time timestamp,
   real_arrival_time timestamp,
   status char(1) not null,
   check (airport_departure_id != airport_arrival_id),
   check (planned_departure_time < planned_arrival_time)
);

create index idx_flight_airline_id             on flight (airline_id);
create index idx_flight_airport_departure_id   on flight (airport_departure_id);
create index idx_flight_airport_arrival_id     on flight (airport_arrival_id);
create index idx_flight_justification_type_id  on flight (justification_type_id);
create index idx_flight_planned_dep_time       on flight (planned_departure_time);

create view vw_flight as 
SELECT
   f.id as flight_id, f.airline_id, f.airport_departure_id, f.airport_arrival_id, f.justification_type_id,
   f.number as flight_number, f.digit as flight_digit, f.type as flight_type, f.planned_departure_time,
   f.planned_arrival_time, f.real_departure_time, f.real_arrival_time, f.status as flight_status,
   a.name as airline_name, a.oaci as airline_oaci,   
   ad.acronym as airport_departure_acronym, ad.description as airport_departure_description,
   ad.name as airport_departure_name, cad.name as city_name_airport_departure,
   aa.acronym as airport_arrival_acronym, aa.description as airport_arrival_description,
   aa.name as airport_arrival_name, caa.name as city_name_airport_arrival, 
   j.acronym as justification_type_acronym, j.description as justification_type_description 
FROM flight f
  JOIN airline            a   on a.id  = f.airline_id
  JOIN airport            ad  on ad.id = f.airport_departure_id
  JOIN airport            aa  on aa.id = f.airport_arrival_id
  JOIN city               cad on cad.id = ad.city_id
  JOIN city               caa on caa.id = aa.city_id
  JOIN justification_type j   on j.id = justification_type_id 
order by f.airline_id, f.planned_departure_time asc;  