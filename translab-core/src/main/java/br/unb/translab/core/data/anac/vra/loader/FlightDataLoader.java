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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import static com.google.common.base.Strings.*;
import static com.google.common.base.Preconditions.*;

import br.unb.translab.core.data.anac.vra.loader.service.FlightDataLoadService;
import br.unb.translab.core.domain.DigitType;
import br.unb.translab.core.domain.Flight;
import br.unb.translab.core.domain.FlightType;
import br.unb.translab.core.domain.JustificationType;
import br.unb.translab.core.domain.repository.AirlineRepository;
import br.unb.translab.core.domain.repository.AirportRepository;
import br.unb.translab.core.domain.repository.FlightRepository;
import br.unb.translab.core.domain.repository.JustificationRepository;
import br.unb.translab.core.io.BigFileReader;

public class FlightDataLoader implements DataLoader
{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(FlightDataLoader.class);

    private static final SimpleDateFormat DATE_PARSE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final AirlineRepository airlineRepository;
    private final JustificationRepository justificationRepository;
    private final AirportRepository airportRepository;

    private final FlightDataLoadService service;

    public FlightDataLoader(@Nonnull AirlineRepository airlineRepository, 
                            @Nonnull JustificationRepository justificationRepository,
                            @Nonnull AirportRepository airportRepository, 
                            @Nonnull FlightRepository flyRepository)
    {
        this.airlineRepository = airlineRepository;
        this.justificationRepository = justificationRepository;
        this.airportRepository = airportRepository;
        service = new FlightDataLoadService(1, flyRepository);
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
                    final Flight flight = new Flight();

                    flight.setAirline(airlineRepository.findByOaci(parts[0].trim()).orNull())
                       .setNumber(parts[1])
                       .setDigit(DigitType.valueOfFromId(parts[2].trim()).orNull())
                       .setType(FlightType.valueOfFromId(parts[3].trim()).orNull())
                       .setFrom(airportRepository.findByAcronym(parts[4].trim()).orNull())
                       .setTo(airportRepository.findByAcronym(parts[5].trim()).orNull())
                       .setPlannedDepartureTime(parseDate(parts[6]).orNull())
                       .setRealDepartureTime(parseDate(parts[7]).orNull())
                       .setPlannedArrivalTime(parseDate(parts[8]).orNull())
                       .setRealArrivalTime(parseDate(parts[9]).orNull())
                       .setStatus(parts[10].trim())
                       .setJustification(justificationRepository.findByAcronym(parts[11].trim()).or(new JustificationType()));

                    service.offer(flight);
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
                LOGGER.error("Error on parsing the date [{}] with the format dd/MM/yyyy HH:mm. The error message is [{}]", 
                        source, exception.getMessage());
            }
        }

        return result;
    }
}
