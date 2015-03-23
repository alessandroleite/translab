package br.unb.translab.core.data.anac.vra.loader;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.repository.AirportRepository;
import br.unb.translab.core.domain.repository.CityRepository;
import br.unb.translab.core.domain.repository.ContinentRepository;
import br.unb.translab.core.domain.repository.CountryRepository;
import br.unb.translab.core.domain.repository.RepositoryTestSupport;

public class AirportDataLoaderTest extends RepositoryTestSupport
{
    private final int numberOfUniqueAirports = 585;
    
    private AirportDataLoader dataLoader;
    private AirportRepository airportRepository;
    private InputStream airportInputStream;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        airportRepository = openRepository(AirportRepository.class);
        
        dataLoader = new AirportDataLoader(
                airportRepository, 
                openRepository(CityRepository.class),
                openRepository(ContinentRepository.class), 
                openRepository(CountryRepository.class));
        
        airportInputStream = AirportDataLoaderTest.class.getClassLoader().getResourceAsStream("data/vra/Glossario_de_Aerodromo.xls");
    }
    
    @Test
    public void testLoadingAirportDataFromXlsFile2MainMemory()
    {
        final List<Airport> airports = dataLoader.apply(airportInputStream);
        assertThat(airports.size()).isEqualTo(numberOfUniqueAirports);
        
        final City firstAirportCity = airports.get(0).getCity();
        assertThat(firstAirportCity).isEqualTo(openRepository(CityRepository.class).findByName(firstAirportCity.getName()).orNull());
    }
    
    @Test
    public void testImportingAirportDataFromXlsFile2Database() throws Exception
    {
        dataLoader.load(airportInputStream);
        
        List<Airport> airports = this.airportRepository.listAll();
        assertThat(airports.size()).isEqualTo(numberOfUniqueAirports);
    }
}
