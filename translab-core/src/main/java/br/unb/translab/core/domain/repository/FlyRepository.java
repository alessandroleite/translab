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

import java.math.BigInteger;

import javax.annotation.Nonnull;

import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;

import br.unb.translab.core.components.Repository;
import br.unb.translab.core.domain.Fly;

@Repository
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
