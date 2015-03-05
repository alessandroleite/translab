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

import java.io.File;
import java.io.IOException;
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
        Optional<List<JustificationType>> optional = Optional.of((List<JustificationType>) new ArrayList<JustificationType>());
        
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
                optional.get().add(new JustificationType().setAcronym(row[1].getContents()).setDescription(row[2].getContents()));
            }
        }
        catch (BiffException | IOException e)
        {
            LOGGER.error("Error on reading justification's file: [{}]. Error message: [{}]", input.getAbsolutePath(), e.getMessage(), e);
            optional = Optional.absent();
        }
        finally
        {
            if (workbook != null)
            {
                workbook.close();
            }
        }

        return optional;
    }

    @Override
    public void load(File file) throws Exception
    {
        this.repository.insert(this.apply(file).get());
    }
}
