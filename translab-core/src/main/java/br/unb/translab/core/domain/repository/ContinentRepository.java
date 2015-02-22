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
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import br.unb.translab.core.domain.Continent;
import br.unb.translab.core.domain.repository.ContinentRepository.ContinentRowMapper;

@RegisterMapper(ContinentRowMapper.class)
public interface ContinentRepository
{
    String SQL_SELECT_ALL_CONTINENTS = "SELECT id as continent_id, acronym as continent_acronym, name as continent_name\n" + 
                                       "FROM continent\n";

    @SqlUpdate("INSERT INTO continent (acronym, name) VALUES (:acronym, :name)")
    Integer insert(@BindBean Continent region);
    
    @SqlQuery(SQL_SELECT_ALL_CONTINENTS + "ORDER BY name")
    List<Continent> listAll();
    
    class ContinentRowMapper implements ResultSetMapper<Continent>
    {
        @Override
        public Continent map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            return new Continent().setAcronym(r.getString("continent_acronym"))
                                         .setId(r.getInt("continent_id"))
                                         .setName(r.getString("continent_name"));
        }
    }
}
