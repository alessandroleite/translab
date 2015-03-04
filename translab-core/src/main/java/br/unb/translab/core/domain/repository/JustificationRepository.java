package br.unb.translab.core.domain.repository;

import io.dohko.jdbi.binders.BindBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.google.common.base.Optional;

import br.unb.translab.core.domain.JustificationType;
import br.unb.translab.core.domain.repository.JustificationRepository.JustificationRowMapper;

@RegisterMapper(JustificationRowMapper.class)
public interface JustificationRepository
{
    String SQL_SELECT_ALL = "SELECT id as justification_type_id, acronym as justification_type_acronym, " + 
                            " description as justification_type_description\n" +
                            "FROM justification_type j\n";
    @SqlUpdate("INSERT INTO justification_type (acronym, description) VALUES (:acronym, :description)")
    Integer insert(@BindBean JustificationType justification);
    
    @SqlUpdate("INSERT INTO justification_type (acronym, description) VALUES (:acronym, :description)")
    @BatchChunkSize(1000)
    void insert(Iterable<JustificationType> justifications);
    
    @SqlQuery(SQL_SELECT_ALL + " ORDER BY j.acronym")
    List<JustificationType> listAll();

    @SqlQuery(SQL_SELECT_ALL + " WHERE lower(j.acronym) = lower(:acronym)")
    @SingleValueResult
    Optional<JustificationType> findByAcronym(@Bind("acronym") String acronym);
    
    class JustificationRowMapper implements ResultSetMapper<JustificationType>
    {
        @Override
        public JustificationType map(int index, ResultSet r, StatementContext ctx) throws SQLException
        {
            return new JustificationType()
                    .setAcronym(r.getString("justification_type_acronym"))
                    .setDescription(r.getString("justification_type_description"))
                    .setId(r.getInt("justification_type_id"));
        }
    }
}
