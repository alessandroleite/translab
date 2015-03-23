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
import br.unb.translab.core.data.anac.vra.loader.DataLoader;
import br.unb.translab.core.domain.Airline;
import br.unb.translab.core.domain.repository.AirlineRepository;
import static com.google.common.collect.Lists.*;
import static com.google.common.base.Preconditions.*;

import com.google.common.base.Function;

public class AirlineDataLoader implements Function<File, List<Airline>>, DataLoader
{
    private final AirlineRepository airlineRepository;
    
    public AirlineDataLoader(@Nonnull AirlineRepository repository)
    {
        this.airlineRepository = repository;
    }
    
    @Override
    public List<Airline> apply(@Nonnull File input)
    {
        checkNotNull(input);
        final List<Airline> airlines = newArrayList();
        
        Workbook workbook = null;

        final WorkbookSettings settings = new WorkbookSettings();
        settings.setEncoding(System.getProperty("anac.vra.data.encoding", System.getProperty("jxl.encoding", "ISO-8859-1")));

        try
        {
            workbook = Workbook.getWorkbook(input, settings);
            Sheet sheet = workbook.getSheet(0);

            for (int i = 4; i < sheet.getRows(); i++)
            {
                Cell[] row = sheet.getRow(i);
                
                Airline airline = new Airline().setName(row[1].getContents().trim()).setOaci(row[0].getContents().trim());
                airlines.add(airline);
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
        
        return airlines;
    }

    @Override
    public void load(File file) throws Exception
    {
        checkNotNull(file);
        this.airlineRepository.insert(this.apply(file));
    }
}
