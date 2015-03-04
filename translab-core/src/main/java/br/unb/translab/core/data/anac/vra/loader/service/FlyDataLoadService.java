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
