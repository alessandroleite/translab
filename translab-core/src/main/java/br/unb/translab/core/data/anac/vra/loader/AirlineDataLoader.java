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

        WorkbookSettings settings = new WorkbookSettings();
        settings.setEncoding("ISO-8859-1");

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
