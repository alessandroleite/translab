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

import br.unb.translab.core.domain.Fly;
import br.unb.translab.core.domain.repository.FlyRepository;
import static com.google.common.collect.Lists.*;

import com.google.common.util.concurrent.AbstractScheduledService;

public class FlyDataLoadService extends AbstractScheduledService
{
    private final BlockingQueue<Fly> queue = new LinkedBlockingDeque<Fly>();

    private final long delayInSeconds;
    private final FlyRepository repository;

    public FlyDataLoadService(long delayInSeconds, FlyRepository repository)
    {
        this.delayInSeconds = delayInSeconds;
        this.repository = repository;
    }

    @Override
    protected void runOneIteration() throws Exception
    {
        int i = 0;

        Fly fly = queue.poll();
        
        List<Fly> flies = newArrayList();

        while (fly != null && i++ <= 1000)
        {
            flies.add(fly);
            fly = queue.poll();
        }
        
        this.repository.insert(flies);
    }

    @Override
    protected Scheduler scheduler()
    {
        return Scheduler.newFixedDelaySchedule(0, delayInSeconds, SECONDS);
    }
    
    public void offer(Fly fly)
    {
        this.queue.offer(fly);
    }
}
