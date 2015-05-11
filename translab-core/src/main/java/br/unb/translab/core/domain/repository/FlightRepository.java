/**
 *     Copyright (C) 2015  the original author or authors.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License,
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package br.unb.translab.core.domain.repository;

import io.dohko.jdbi.args.JodaDateTimeMapper;
import io.dohko.jdbi.binders.BindBean;
import io.dohko.jdbi.util.BigIntegerMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Nonnull;

import org.joda.time.Interval;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import br.unb.translab.core.components.Repository;
import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.DigitType;
import br.unb.translab.core.domain.Flight;
import br.unb.translab.core.domain.FlightType;
import br.unb.translab.core.domain.repository.AirlineRepository.AirlineRowMapper;
import br.unb.translab.core.domain.repository.FlightRepository.FlightMapper;
import br.unb.translab.core.domain.repository.JustificationRepository.JustificationRowMapper;

import com.google.common.base.Optional;

@Repository
@RegisterMapper({FlightMapper.class, BigIntegerMapper.class})
public interface FlightRepository
{
    String SQL_INSERT_FLIGHT = "INSERT INTO flight (airline_id, airport_departure_id, airport_arrival_id, justification_type_id, number, digit,\n" + 
                               "type, planned_departure_time, real_departure_time, planned_arrival_time, real_arrival_time, status)\n"             + 
                               "VALUES (:airline.id, :from.id, :to.id, :justification.id, :number, :digit.id,\n"                                   +
                               "        :type.acronym, :plannedDepartureTime, :realDepartureTime, :plannedArrivalTime," + 
                               "        :realArrivalTime, :status)";
    
    
    String SQL_SELECT_ALL_FLIGHTS = "SELECT \n"                                                          +
             "flight_id, airline_id, airport_departure_id, airport_arrival_id, justification_type_id,\n" +
             "flight_number, flight_digit, flight_type, planned_departure_time,\n"                       +
             "planned_arrival_time, real_departure_time, real_arrival_time, flight_status,\n"            +
             "airline_name, airline_oaci,\n"                                                             +
             "airport_departure_acronym, airport_departure_description,\n"                               +
             "airport_departure_name, city_name_airport_departure,\n"                                    +
             "airport_arrival_acronym, airport_arrival_description,\n"                                   +
             "airport_arrival_name, city_name_airport_arrival,\n"                                        +
             "justification_type_acronym, justification_type_description\n"                              +
           "FROM vw_flight\n";

    @SqlUpdate(SQL_INSERT_FLIGHT)
    @GetGeneratedKeys(value = BigIntegerMapper.class)
    BigInteger insert(@BindBean @Nonnull Flight flight);
    
    @SqlBatch(SQL_INSERT_FLIGHT)
    @BatchChunkSize(1000)
    void insert(@BindBean @Nonnull Iterable<Flight> flight);
    
    @SqlQuery(SQL_SELECT_ALL_FLIGHTS + "WHERE flight_id = :id")
    @SingleValueResult
    Optional<Flight> findById(@Bind("id") BigInteger id);
    
    @SqlQuery(SQL_SELECT_ALL_FLIGHTS + "WHERE lower(flight_number) = lower(:number) ORDER BY airline_id, planned_departure_time")
    List<Flight> findByFlightNumber(@Bind("number") String number);
    
    @SqlQuery("SELECT f.id as flight_id, f.airline_id, f.airport_departure_id as ")
    List<Flight> listAllFightsOnInterval(@Nonnull Interval interval);
    
    class FlightMapper implements ResultSetMapper<Flight>
    {
        @Override
        public Flight map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            final Flight flight = new Flight();
            
            final Airport departureAirport = new Airport()
                    .setAcronym(r.getString("airport_departure_acronym"))
                    .setCity(new City().setName(r.getString("city_name_airport_departure")))
                    .setDescription(r.getString("airport_departure_description"))
                    .setName(r.getString("airport_departure_name"));
            
            final Airport arrivalAirport = new Airport()
                    .setAcronym(r.getString("airport_arrival_acronym"))
                    .setCity(new City().setName(r.getString("city_name_airport_arrival")))
                    .setDescription(r.getString("airport_arrival_description"))
                    .setName(r.getString("airport_arrival_name"));
            
            flight.setAirline(new AirlineRowMapper().map(index, r, ctx))
                  .setDigit(DigitType.valueOfFromId("flight_digit").orNull())
                  .setFrom(departureAirport)
                  .setId(r.getBigDecimal("flight_id").toBigInteger())
                  .setJustification(new JustificationRowMapper().map(index, r, ctx))
                  .setNumber(r.getString("flight_number"))
                  .setPlannedArrivalTime(new JodaDateTimeMapper().extractByName(r, "planned_arrival_time"))
                  .setRealArrivalTime(new JodaDateTimeMapper().extractByName(r, "real_arrival_time"))
                  .setRealDepartureTime(new JodaDateTimeMapper().extractByName(r, "real_departure_time"))
                  .setStatus(r.getString("flight_status"))
                  .setTo(arrivalAirport)
                  .setType(FlightType.valueOfFromId(r.getString("flight_type")).orNull());
            
            
            return flight;
        }
    }
}
