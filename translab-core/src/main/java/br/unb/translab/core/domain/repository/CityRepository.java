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
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import br.unb.translab.core.components.Repository;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.repository.CityRepository.CityRowMapper;
import br.unb.translab.core.domain.repository.CountryRepository.CountryRowMapper;

import com.google.common.base.Optional;

@Repository
@RegisterMapper(CityRowMapper.class)
public interface CityRepository
{
    String SQL_SELECT_ALL_CITY = "SELECT c.id as city_id, c.name as city_name,\n" +
                                 "ct.id as country_id, ct.name as country_name, ct.acronym as country_acronym,\n" +
                                 "gr.id as continent_id, gr.name as continent_name, gr.acronym as continent_acronym\n" +
                                 "FROM city c\n"                          +
                                 "JOIN country ct on ct.id = c.country_id\n" +
                                 "JOIN continent gr on gr.id = ct.continent_id\n";
    
    @SqlUpdate("INSERT INTO city (name, country_id) VALUES (:name, :country.id)")
    @GetGeneratedKeys
    Integer insert(@BindBean City city);
    
    @SqlQuery(SQL_SELECT_ALL_CITY + "ORDER BY c.name, ct.name, gr.name")
    List<City> listAll();
    
    @SqlQuery(SQL_SELECT_ALL_CITY + "WHERE lower(c.name) = lower(:name)")
    @SingleValueResult
    Optional<City> findByName(@Bind("name") String name);
    
    class CityRowMapper implements ResultSetMapper<City>
    {
        @Override
        public City map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            return new City().setCountry(new CountryRowMapper().map(index, r, ctx))
                             .setId(r.getInt("city_id"))
                             .setName(r.getString("city_name"));
        }
    }
}
