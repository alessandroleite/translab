package br.unb.translab.core.data.anac.vra;

import io.dohko.jdbi.exceptions.AnyThrow;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Function;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import br.unb.translab.core.domain.Airport;
import br.unb.translab.core.domain.City;
import br.unb.translab.core.domain.Continent;
import br.unb.translab.core.domain.Country;
import static com.google.common.collect.Lists.*;

public class AirportXlsReadFunction implements Function<File, List<Airport>>
{
    @Override
    public List<Airport> apply(File input)
    {
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

                Country country = new Country()
                        .setName(row[5].getContents())
                        .setContinent(new Continent().setName(row[row.length - 1].getContents()));

                City city = new City().setCountry(country).setName(row[3].getContents());

                Airport airport = new Airport()
                        .setAcronym(row[1].getContents())
                        .setCity(city)
                        .setDescription(row[2].getContents());

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
    
    public static void main(String[] args)
    {
        new AirportXlsReadFunction().apply(new File(args[0]));
    }
}
