package br.unb.translab.core.data.anac.vra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import br.unb.translab.core.domain.JustificationType;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public class JustificationXlsReadFunction implements Function<File, Optional<List<JustificationType>>>
{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(JustificationXlsReadFunction.class);

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
}
