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

import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.Continent;
import br.unb.translab.core.domain.Country;
import static org.assertj.core.api.Assertions.assertThat;

public class CityRepositoryTest extends RepositoryTestSupport
{
    private CityRepository cityRepository;
    private Country france;    
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        this.cityRepository = openRepository(CityRepository.class);
        france = new Country().setAcronym("FRA").setContinent(new Continent().setAcronym("EU").setName("EU")).setName("France");
        france.getContinent().setId(this.openRepository(ContinentRepository.class).insert(france.getContinent()));
        france.setId(this.openRepository(CountryRepository.class).insert(france));
    }
    
    @Test
    public void insertOneCity()
    {
        City paris = new City().setCountry(france).setName("Paris");
        Integer id = this.cityRepository.insert(paris);
        paris.setId(id);
        
        assertThat(id).isNotNull();
        assertThat(id > 0).isTrue();
        
        Optional<City> optional = this.cityRepository.findByName(paris.getName());
        
        assertThat(optional.isPresent()).isTrue();
        assertThat(paris.getId()).isEqualTo(optional.get().getId());
        assertThat(paris.getName()).isEqualTo(optional.get().getName());
        assertThat(paris.getCountry()).isEqualTo(optional.get().getCountry());
    }
}
