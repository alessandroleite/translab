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
