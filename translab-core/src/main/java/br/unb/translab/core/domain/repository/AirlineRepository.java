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

import br.unb.translab.core.domain.Airline;
import br.unb.translab.core.domain.repository.AirlineRepository.AirlineRowMapper;

import com.google.common.base.Optional;

@RegisterMapper(AirlineRowMapper.class)
public interface AirlineRepository
{
    String SQL_SELECT_ALL_AIRLINES = "SELECT a.id as airline_id, a.name as airline_name, a.oaci as airline_oaci\n" + 
                                     "FROM airline a\n";
    @SqlUpdate("INSERT INTO airline (oaci, name) VALUES (:oaci, :name)")
    @GetGeneratedKeys
    Integer insert(@BindBean Airline airline);
    
    @SqlQuery(SQL_SELECT_ALL_AIRLINES + "WHERE a.id = :id")
    @SingleValueResult
    Optional<Airline> findById(@Bind("id") Integer id);
    
    @SqlQuery(SQL_SELECT_ALL_AIRLINES + "WHERE lower(a.oaci) = lower(:oaci)")
    @SingleValueResult
    Optional<Airline> findByOaci(@Bind("oaci") String oaci);
    
    @SqlQuery(SQL_SELECT_ALL_AIRLINES + "ORDER BY lower(a.oaci)")
    List<Airline> listAll();
    
    class AirlineRowMapper implements ResultSetMapper<Airline>
    {
        @Override
        public Airline map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            return new Airline().setId(r.getInt("airline_id")).setName(r.getString("airline_name")).setOaci(r.getString("airline_oaci"));
        }
    }
}
