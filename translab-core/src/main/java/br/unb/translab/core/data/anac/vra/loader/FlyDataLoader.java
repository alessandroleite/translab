package br.unb.translab.core.data.anac.vra.loader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import static com.google.common.base.Strings.*;
import static com.google.common.base.Preconditions.*;

import br.unb.translab.core.data.anac.vra.loader.service.FlyDataLoadService;
import br.unb.translab.core.domain.DigitType;
import br.unb.translab.core.domain.Fly;
import br.unb.translab.core.domain.FlyType;
import br.unb.translab.core.domain.JustificationType;
import br.unb.translab.core.domain.repository.AirlineRepository;
import br.unb.translab.core.domain.repository.AirportRepository;
import br.unb.translab.core.domain.repository.FlyRepository;
import br.unb.translab.core.domain.repository.JustificationRepository;
import br.unb.translab.core.io.BigFileReader;

public class FlyDataLoader implements DataLoader
{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(FlyDataLoader.class);

    private static final SimpleDateFormat DATE_PARSE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final AirlineRepository airlineRepository;
    private final JustificationRepository justificationRepository;
    private final AirportRepository airportRepository;

    private final FlyDataLoadService service;

    public FlyDataLoader(@Nonnull AirlineRepository airlineRepository, 
                         @Nonnull JustificationRepository justificationRepository,
                         @Nonnull AirportRepository airportRepository, 
                         @Nonnull FlyRepository flyRepository)
    {
        this.airlineRepository = airlineRepository;
        this.justificationRepository = justificationRepository;
        this.airportRepository = airportRepository;
        service = new FlyDataLoadService(1, flyRepository);
    }

    @Override
    public void load(@Nonnull File file) throws Exception
    {
        checkNotNull(file);
        service.startAsync();

        try (BigFileReader reader = new BigFileReader(file))
        {
            reader.skip();

            for (String line : reader)
            {
                String[] parts = line.replaceAll("\"", "").split(";");

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

                    service.offer(fly);
                }
            }
        }
        finally
        {
            service.stopAsync();
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
