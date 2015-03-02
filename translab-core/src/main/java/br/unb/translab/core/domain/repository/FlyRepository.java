package br.unb.translab.core.domain.repository;

import io.dohko.jdbi.binders.BindBean;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;

import br.unb.translab.core.domain.Fly;

public interface FlyRepository
{
    String SQL_INSERT_FLY = "INSERT INTO fly (airline_id, airport_departure_id, airport_arrival_id, justification_type_id, fly_number, fly_digit,\n" + 
                            "fly_type, planned_departure_time, real_departure_time, planned_arrival_time, real_departure_time,\n" + 
                            "planned_arrival_time, real_arrival_time, status)\n" + 
                            "VALUES (:airline.id, :from.id, :to.id, :justification.id, :number, :digit.id, :type.id,\n" + 
                            ":plannedDepartureTime, :realDepartureTime, :plannedArrivalTime, :realArrivalTime, :status)";

    @SqlUpdate(SQL_INSERT_FLY)
    @GetGeneratedKeys
    BigInteger insert(@BindBean @Nonnull Fly fly);
    
    @SqlBatch(SQL_INSERT_FLY)
    @BatchChunkSize(700)
    void insert(@BindBean @Nonnull Iterable<Fly> flies);
}
