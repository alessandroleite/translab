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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import br.unb.translab.core.domain.JustificationType;
import br.unb.translab.core.domain.repository.JustificationRepository;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public class JustificationDataLoader implements Function<File, Optional<List<JustificationType>>>, DataLoader
{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(JustificationDataLoader.class);
    
    private final JustificationRepository repository;
    
    public JustificationDataLoader(@Nonnull JustificationRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public Optional<List<JustificationType>> apply(File input)
    {
        Optional<List<JustificationType>> result = Optional.of((List<JustificationType>) new ArrayList<JustificationType>());
        
        try
        {
            try(InputStream stream = new FileInputStream(input))
            {
                result = apply(stream);
            }
            
        }catch (IOException exception)
        {
            AnyThrow.throwUncheked(exception);
        }
        
        return result;
    }
    
    public Optional<List<JustificationType>> apply(InputStream input)
    {
        Optional<List<JustificationType>> result = Optional.of((List<JustificationType>) new ArrayList<JustificationType>());
        
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
                result.get().add(new JustificationType().setAcronym(row[1].getContents().trim()).setDescription(row[2].getContents().trim()));
            }
        }
        catch (BiffException | IOException e)
        {
            LOGGER.error("Error on reading the justification. The error message is: [{}]", e.getMessage(), e);
            result = Optional.absent();
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
    public void load(@Nonnull File file) throws Exception
    {
        apply(file).get().stream().forEach(j -> repository.insert(j));
//        this.repository.insert(this.apply(file).get());
    }
    
    public void load(@Nonnull InputStream input) throws Exception
    {
        apply(input).get().stream().forEach(j -> repository.insert(j));
//      this.repository.insert(apply(input).get());  
    }
}
