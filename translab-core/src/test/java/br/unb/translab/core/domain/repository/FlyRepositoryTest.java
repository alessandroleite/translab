package br.unb.translab.core.domain.repository;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.unb.translab.core.data.anac.vra.ImportFlyDataCommand;

public class FlyRepositoryTest extends RepositoryTestSupport
{
    private FlyRepository flyRepository;
    private ImportFlyDataCommand command;
    
    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        this.flyRepository = openRepository(FlyRepository.class);
        command = new ImportFlyDataCommand
                ( openRepository(AirlineRepository.class), 
                  openRepository(JustificationRepository.class), 
                  openRepository(AirportRepository.class)
                );
    }
    
    @Test
    public void insertOneFly() throws IOException
    {
//        command.execute(new File("/Users/alessandro/projetos/translab/VRA_do_MES_2015_1.csv"));
    }
}
