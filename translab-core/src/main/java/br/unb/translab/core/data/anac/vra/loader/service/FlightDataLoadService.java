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
package br.unb.translab.core.data.anac.vra.loader.service;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import br.unb.translab.core.domain.Flight;
import br.unb.translab.core.domain.repository.FlightRepository;
import static com.google.common.collect.Lists.*;

import com.google.common.util.concurrent.AbstractScheduledService;

public class FlightDataLoadService extends AbstractScheduledService
{
    private final BlockingQueue<Flight> queue = new LinkedBlockingDeque<Flight>();

    private final long delayInSeconds;
    private final FlightRepository repository;

    public FlightDataLoadService(long delayInSeconds, FlightRepository repository)
    {
        this.delayInSeconds = delayInSeconds;
        this.repository = repository;
    }

    @Override
    protected void runOneIteration() throws Exception
    {
        int i = 0;

        Flight fly = queue.poll();
        final List<Flight> flights = newArrayList();

        while (fly != null && i++ <= Integer.parseInt(System.getProperty("br.unb.translab.data.vra.batch.loader.size", "1000")))
        {
            flights.add(fly);
            fly = queue.poll();
        }
        
        this.repository.insert(flights);
    }

    @Override
    protected Scheduler scheduler()
    {
        return Scheduler.newFixedDelaySchedule(0, delayInSeconds, SECONDS);
    }
    
    public void offer(Flight fly)
    {
        this.queue.offer(fly);
    }
}
