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

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import br.unb.translab.core.data.anac.vra.loader.AirportDataLoader;
import br.unb.translab.core.data.anac.vra.loader.JustificationDataLoader;
import br.unb.translab.core.domain.Airline;
import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.DigitType;
import br.unb.translab.core.domain.Flight;
import br.unb.translab.core.domain.FlightType;
import br.unb.translab.core.domain.JustificationType;
import static org.assertj.core.api.Assertions.*;

public class FlightRepositoryTest extends RepositoryTestSupport
{
    private FlightRepository repository;
    private AirportRepository airportRepository;

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        this.repository = openRepository(FlightRepository.class);
        this.airportRepository = openRepository(AirportRepository.class);
        
        new AirportDataLoader(airportRepository, 
                              openRepository(CityRepository.class), 
                              openRepository(ContinentRepository.class), 
                              openRepository(CountryRepository.class))
            .load(FlightRepositoryTest.class.getClassLoader().getResourceAsStream("data/vra/Glossario_de_Aerodromo.xls"));
        
        new JustificationDataLoader(openRepository(JustificationRepository.class))
            .load(FlightRepositoryTest.class.getClassLoader().getResourceAsStream("data/vra/Glossario_de_Justificativas.xls"));
    }

    @Test
    public void insertOneFlight() throws IOException
    {
        final List<Airport> airports = this.airportRepository.listAll();
        
        assertThat(airports).isNotNull();
        assertThat(airports.size()).isGreaterThan(1);
        
        final Airline tam = new Airline().setName("TAM").setOaci("TAM");
        tam.setId(this.openRepository(AirlineRepository.class).insert(tam));
        
        final List<JustificationType> justifications = openRepository(JustificationRepository.class).listAll();
        
        assertThat(justifications).isNotNull();
        assertThat(justifications.size()).isGreaterThan(1);
        
        final Flight flight = new Flight();
        flight.setAirline(tam)
              .setDigit(DigitType.REGULAR)
              .setFrom(airports.get(0))
              .setJustification(justifications.get(0))
              .setNumber("PT-2020")
              .setPlannedArrivalTime(new DateTime())
              .setPlannedDepartureTime(new DateTime(System.currentTimeMillis() - 500000))
              .setStatus("S")
              .setTo(airports.get(1))
              .setType(FlightType.NACIONAL);
        
        final BigInteger flightId = this.repository.insert(flight);
        assertThat(flightId).isNotNull();
        
        Optional<Flight> foundFlight = this.repository.findById(flightId);
        assertThat(foundFlight.isPresent()).isTrue();
    }
}
