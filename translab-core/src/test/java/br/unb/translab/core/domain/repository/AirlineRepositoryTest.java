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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import br.unb.translab.core.domain.Airline;
import static org.assertj.core.api.Assertions.*;

public class AirlineRepositoryTest extends RepositoryTestSupport
{
    private AirlineRepository _airlineRepository;

    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        this._airlineRepository = openRepository(AirlineRepository.class);
    }

    @Test
    public void insertOneAirline()
    {
        Airline tam = new Airline().setName("TAM").setOaci("TAM");
        Integer id = this._airlineRepository.insert(tam);

        assertThat(id).isNotNull();
        assertThat(id > 0).isTrue();

        Optional<Airline> optional = this._airlineRepository.findByOaci(tam.getOaci());
        assertThat(optional.isPresent()).isTrue();
        
        Airline airline = optional.get();
        assertThat(id).isEqualTo(airline.getId());
        
        assertThat(tam).isEqualTo(airline);
        assertThat(tam.setId(id)).isEqualTo(airline);
        
        List<Airline> airlines = this._airlineRepository.listAll();
        assertThat(airlines.size() == 1).isTrue();
        assertThat(airlines.get(0)).isEqualTo(tam);
    }
}
