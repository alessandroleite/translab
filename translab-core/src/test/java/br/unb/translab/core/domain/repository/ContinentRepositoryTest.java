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

import org.junit.Test;

import br.unb.translab.core.domain.Continent;

import static org.assertj.core.api.Assertions.*;

public class ContinentRepositoryTest extends RepositoryTestSupport
{
    private ContinentRepository repository;
    
    @Override
    public void setUp()
    {
        super.setUp();
        this.repository = openRepository(ContinentRepository.class);
    }

    @Test
    public void insertEuropeanContinent()
    {
        Continent europe = new Continent().setAcronym("EU").setName("Europe");
        Integer id = this.repository.insert(europe);
        
        assertThat(id).isNotNull();
        assertThat(id).isNotNegative();
        assertThat(id).isNotEqualTo(0);
    }
}
