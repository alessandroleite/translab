package br.unb.translab.core.domain.repository;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.unb.translab.core.data.anac.vra.loader.FlyDataLoader;

public class FlyRepositoryTest extends RepositoryTestSupport
{
    private FlyDataLoader loader;

    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        loader = new FlyDataLoader(
                openRepository(AirlineRepository.class), 
                openRepository(JustificationRepository.class),
                openRepository(AirportRepository.class), 
                openRepository(FlyRepository.class));
    }

    @Test
    public void insertOneFly() throws IOException
    {
    }
}
