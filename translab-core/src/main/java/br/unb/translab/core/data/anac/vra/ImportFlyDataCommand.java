package br.unb.translab.core.data.anac.vra;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import static com.google.common.base.Strings.*;
import static com.google.common.collect.Lists.*;
import br.unb.translab.core.domain.DigitType;
import br.unb.translab.core.domain.Fly;
import br.unb.translab.core.domain.FlyType;
import br.unb.translab.core.domain.JustificationType;
import br.unb.translab.core.domain.repository.AirlineRepository;
import br.unb.translab.core.domain.repository.AirportRepository;
import br.unb.translab.core.domain.repository.JustificationRepository;
import br.unb.translab.core.io.BigFileReader;

public class ImportFlyDataCommand
{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(ImportFlyDataCommand.class);
    
    private static final SimpleDateFormat DATE_PARSE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    private final AirlineRepository airlineRepository;
    private final JustificationRepository justificationRepository;
    private final AirportRepository airportRepository;
    
    
    public ImportFlyDataCommand(@Nonnull AirlineRepository airlineRepository, 
                                @Nonnull JustificationRepository justificationRepository,
                                @Nonnull AirportRepository airportRepository)
    {
        this.airlineRepository = airlineRepository;
        this.justificationRepository = justificationRepository;
        this.airportRepository = airportRepository;
    }


    public void execute(File file) throws IOException
    {
        try(BigFileReader reader = new BigFileReader(file))
        {
            reader.skip();
            
            List<Fly> flies = newArrayList();
            
            for (String line: reader)
            {
                String[] parts = line.replaceAll("\"", " ").split(";");
                
                if (parts.length == 12)
                {
                    final Fly fly = new Fly();
                    
                    fly.setAirline(airlineRepository.findByOaci(parts[0].trim()).orNull())
                       .setNumber(parts[1])
                       .setDigit(DigitType.valueOfFromId(parts[2].trim()).orNull())
                       .setType(FlyType.valueOfFromId(parts[3].trim()).orNull())
                       .setFrom(airportRepository.findByAcronym(parts[4].trim()).orNull())
                       .setTo(airportRepository.findByAcronym(parts[5].trim()).orNull())
                       .setPlannedDepartureTime(parseDate(parts[6]).orNull())
                       .setRealDepartureTime(parseDate(parts[7]).orNull())
                       .setPlannedArrivalTime(parseDate(parts[8]).orNull())
                       .setRealArrivalTime(parseDate(parts[9]).orNull())
                       .setStatus(parts[10].trim())
                       .setJustification(justificationRepository.findByAcronym(parts[11].trim()).or(new JustificationType()));
                    
                    flies.add(fly);
                }
            }
        }
    }


    private Optional<DateTime> parseDate(String source)
    {
        Optional<DateTime> result = Optional.absent();
        
        if (!isNullOrEmpty(source))
        {
            try
            {
                result = Optional.of(new DateTime(DATE_PARSE_FORMAT.parse(source.trim())));
            }
            catch (ParseException exception)
            {
                LOGGER.error("Error on parsing data [{}] with format dd/MM/yyyy HH:mm. Error message [{}]", source, exception.getMessage());
            }
        }
        
        return result;
    }
}
