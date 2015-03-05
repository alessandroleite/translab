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
package br.unb.translab.core.data.anac.vra.loader;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import io.dohko.jdbi.exceptions.AnyThrow;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.Continent;
import br.unb.translab.core.domain.Country;
import br.unb.translab.core.domain.repository.AirportRepository;
import br.unb.translab.core.domain.repository.CityRepository;
import br.unb.translab.core.domain.repository.ContinentRepository;
import br.unb.translab.core.domain.repository.CountryRepository;

import com.google.common.base.Function;

public class AirportDataLoader implements Function<File, List<Airport>>, DataLoader
{
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final ContinentRepository continentRepository;
    private final CountryRepository countryRepository;
    
    public AirportDataLoader(@Nonnull AirportRepository airportRepository, 
                             @Nonnull CityRepository cityRepository,
                             @Nonnull ContinentRepository continentRepository,
                             @Nonnull CountryRepository countryRepository)
    {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
        this.continentRepository = continentRepository;
        this.countryRepository = countryRepository;
    }
    
    
    @Override
    public List<Airport> apply(File input)
    {
        checkNotNull(input);
        List<Airport> result = newArrayList();
        
        Workbook workbook = null;
        
        WorkbookSettings settings = new WorkbookSettings();
        settings.setEncoding("ISO-8859-1");

        try
        {
            workbook = Workbook.getWorkbook(input, settings);
            Sheet sheet = workbook.getSheet(0);

            for (int i = 4; i < sheet.getRows(); i++)
            {
                Cell[] row = sheet.getRow(i);
                
                Continent continent = this.continentRepository.findByName(row[row.length - 1].getContents().trim())
                                                              .or(new Continent().setName(row[row.length - 1].getContents()));
                
                if (continent.getId() == null)
                {
                    continent.setId(this.continentRepository.insert(continent));
                }

                Country country = this.countryRepository.findByName(row[5].getContents().trim())
                                                        .or(new Country().setName(row[5].getContents().trim()).setContinent(continent));
                
                if (country.getId() == null)
                {
                    country.setId(this.countryRepository.insert(country));
                }

                City city = this.cityRepository.findByName(row[3].getContents()).or(new City().setCountry(country).setName(row[3].getContents().trim()));
                
                if (city.getId() == null)
                {
                    city.setId(this.cityRepository.insert(city));
                }

                Airport airport = new Airport()
                        .setAcronym(row[1].getContents().trim())
                        .setCity(city)
                        .setDescription(row[2].getContents().trim());

                result.add(airport);
            }
        }
        catch (BiffException | IOException exception)
        {
            AnyThrow.throwUncheked(exception);
        }
        finally
        {
            if (workbook != null)
            {
                workbook.close();
            }
        }

        return result;
    }

    @Override
    public void load(File file) throws Exception
    {
        this.airportRepository.insert(this.apply(file));
    }
}
