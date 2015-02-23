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

import br.unb.translab.core.domain.Continent;
import br.unb.translab.core.domain.Country;
import static org.assertj.core.api.Assertions.assertThat;

public class CountryRepositoryTest extends RepositoryTestSupport
{
    private CountryRepository repository;
    private Continent continent;
    
    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        this.repository = openRepository(CountryRepository.class);
        continent = new Continent().setAcronym("EU").setName("Europe");
        this.continent.setId(this.openRepository(ContinentRepository.class).insert(continent));
    }
    
    @Test
    public void insertOneCountry()
    {
        Country country = new Country().setAcronym("FRA").setContinent(continent).setName("France");
        Integer id = this.repository.insert(country);
        country.setId(id);
        
        assertThat(id).isNotNull();
        assertThat(id).isNotNegative();
        
        Optional<Country> country2 = this.repository.findByName(country.getName());
        assertThat(country2.isPresent()).isTrue();
        
        assertThat(country.getId()).isEqualTo(country2.get().getId());
        assertThat(country.getName()).isEqualTo(country2.get().getName());
        assertThat(country.getAcronym()).isEqualTo(country2.get().getAcronym());
        assertThat(country.getContinent()).isEqualTo(country2.get().getContinent());
    }
}
