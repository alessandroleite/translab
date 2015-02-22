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
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.google.common.base.Optional;

import br.unb.translab.core.domain.Country;
import br.unb.translab.core.domain.repository.ContinentRepository.ContinentRowMapper;

public interface CountryRepository
{
    String SQL_SELECT_ALL_COUNTRIES = "SELECT id as c.country_id, c.acronym as country_acronym, c.name as country_name,\n" +
                                      "c.continent_id, gr.name as continent_name\n" + 
                                      "FROM country c\n"                            +
                                      "JOIN continent gr on gr.id = c.continent_id\n";
    
    @SqlUpdate("INSERT INTO country (continent_id, acronym, name) VALUES (:continent.id, :acronym, :name)")
    @GetGeneratedKeys
    Integer insert(@BindBean Country country);
    
    @SqlQuery(SQL_SELECT_ALL_COUNTRIES + "ORDER BY c.name, gr.name")
    List<Country> listAll();
    
    @SqlQuery(SQL_SELECT_ALL_COUNTRIES + " WHERE lower(name) = lower(name)")
    @SingleValueResult(Country.class)
    Optional<Country> findByName(String name);
    
    class CountryRowMapper implements ResultSetMapper<Country>
    {
        @Override
        public Country map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            return new Country().setAcronym(r.getString("country_acronym"))
                                .setContinent(new ContinentRowMapper().map(index, r, ctx))
                                .setId(r.getInt("country_id"))
                                .setName(r.getString("country_name"));
        }
    }
}
