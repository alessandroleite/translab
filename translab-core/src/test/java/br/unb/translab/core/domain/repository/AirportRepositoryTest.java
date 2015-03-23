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

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.Continent;
import br.unb.translab.core.domain.Country;
import static org.assertj.core.api.Assertions.*;

public class AirportRepositoryTest extends RepositoryTestSupport
{
    AirportRepository airportRepository;
    City paris;
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        this.airportRepository = this.openRepository(AirportRepository.class);
        
        Country france = new Country().setAcronym("FRA").setContinent(new Continent().setAcronym("EU").setName("EU")).setName("France");
        france.getContinent().setId(this.openRepository(ContinentRepository.class).insert(france.getContinent()));
        france.setId(this.openRepository(CountryRepository.class).insert(france));
        
        paris = new City().setCountry(france).setName("Paris");
        paris.setId(this.openRepository(CityRepository.class).insert(paris));
    }
    
    @Test
    public void insertOneAirport()
    {
        Airport airport = new Airport().setAcronym("CDGI").setCity(paris).setDescription("description").setName("Charles de Gaulle");
        Integer id = this.airportRepository.insert(airport);
        
        assertThat(id).isNotNull();
        assertThat(id > 0).isTrue();
        
        Optional<Airport> optional = this.airportRepository.findByAcronym(airport.getAcronym());
        
        assertThat(optional.isPresent()).isTrue();
        assertThat(airport.getAcronym()).isEqualTo(optional.get().getAcronym());
        assertThat(airport.getCity()).isEqualTo(optional.get().getCity());
        assertThat(airport.getDescription()).isEqualTo(optional.get().getDescription());
    }
}
