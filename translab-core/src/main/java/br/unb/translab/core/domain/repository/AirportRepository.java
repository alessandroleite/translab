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

import io.dohko.jdbi.binders.BindBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.google.common.base.Optional;

import br.unb.translab.core.components.Repository;
import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.Country;
import br.unb.translab.core.domain.repository.AirportRepository.AiportRowMapper;

@Repository
@RegisterMapper(AiportRowMapper.class)
public interface AirportRepository
{
    String SQL_ALL = "SELECT a.id as airport_id, a.acronym as airport_acronym, a.name as airport_name, a.description as airport_description,\n" + 
                     "a.city_id, c.name as city_name,\n" +
                     "ct.id as country_id, ct.name as country_name, ct.acronym as country_acronym,\n" +
                     "gr.id as continent_id, gr.name as continent_name\n" +
                     "FROM airport a\n"                          +
                     "JOIN city c on c.id = a.city_id\n"         +
                     "JOIN country ct on ct.id = c.country_id\n" +
                     "JOIN continent gr on gr.id = ct.continent_id\n";
    
    @SqlUpdate("INSERT INTO airport (city_id, acronym, name, description) VALUES (:city.id, :acronym, :name, :description)")
    Integer insert(@BindBean Airport airport);
    
    @SqlBatch("INSERT INTO airport (city_id, acronym, name, description) VALUES (:city.id, :acronym, :name, :description)")
    @BatchChunkSize(1000)
    void insert(Iterable<Airport> airports);
    
    @SqlQuery(SQL_ALL + "ORDER BY a.name, c.name, ct.name")
    List<Airport> listAll();
    
    @SqlQuery(SQL_ALL + "WHERE lower(a.acronym) = lower(:acronym)")
    @SingleValueResult
    Optional<Airport> findByAcronym(@Bind("acronym") String acronym);
    
    @SqlQuery(SQL_ALL + "WHERE id = :id")
    Optional<Airport> findById(@Bind("id") Integer id);
    
    
    class AiportRowMapper implements ResultSetMapper<Airport>
    {
        @Override
        public Airport map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            City city = new City().setCountry(new Country().setId(r.getInt("country_id")).setName(r.getString("country_name")))
                                  .setName(r.getString("city_name"))
                                  .setId(r.getInt("city_id"));
            
            return new Airport().setAcronym(r.getString("airport_acronym"))
                                .setCity(city)
                                .setDescription(r.getString("airport_description"))
                                .setId(r.getInt("airport_id"))
                                .setName(r.getString("airport_name"));
        }
    }
}
