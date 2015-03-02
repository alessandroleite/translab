package br.unb.translab.core.domain;

import java.math.BigInteger;

import org.joda.time.DateTime;

public class Fly
{
    private BigInteger id;
    
    private Airline airline;

    private Airport from;

    private Airport to;

    private JustificationType justification;

    private String number;

    private DigitType digit;

    private FlyType type;

    private DateTime plannedDepartureTime;

    private DateTime realDepartureTime;

    private DateTime plannedArrivalTime;

    private DateTime realArrivalTime;

    private String status;

    /**
     * @return the id
     */
    public BigInteger getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public Fly setId(BigInteger id)
    {
        this.id = id;
        return this;
    }
    
    

    /**
     * @return the airline
     */
    public Airline getAirline()
    {
        return airline;
    }

    /**
     * @param airline the airline to set
     */
    public Fly setAirline(Airline airline)
    {
        this.airline = airline;
        return this;
    }

    /**
     * @return the from
     */
    public Airport getFrom()
    {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public Fly setFrom(Airport from)
    {
        this.from = from;
        return this;
    }

    /**
     * @return the to
     */
    public Airport getTo()
    {
        return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public Fly setTo(Airport to)
    {
        this.to = to;
        return this;
    }

    /**
     * @return the justification
     */
    public JustificationType getJustification()
    {
        return justification;
    }

    /**
     * @param justification
     *            the justification to set
     */
    public Fly setJustification(JustificationType justification)
    {
        this.justification = justification;
        return this;
    }

    /**
     * @return the number
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public Fly setNumber(String number)
    {
        this.number = number;
        return this;
    }

    /**
     * @return the digit
     */
    public DigitType getDigit()
    {
        return digit;
    }

    /**
     * @param digit
     *            the digit to set
     */
    public Fly setDigit(DigitType digit)
    {
        this.digit = digit;
        return this;
    }

    /**
     * @return the type
     */
    public FlyType getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public Fly setType(FlyType type)
    {
        this.type = type;
        return this;
    }

    /**
     * @return the plannedDepartureTime
     */
    public DateTime getPlannedDepartureTime()
    {
        return plannedDepartureTime;
    }

    /**
     * @param plannedDepartureTime
     *            the plannedDepartureTime to set
     */
    public Fly setPlannedDepartureTime(DateTime plannedDepartureTime)
    {
        this.plannedDepartureTime = plannedDepartureTime;
        return this;
    }

    /**
     * @return the realDepartureTime
     */
    public DateTime getRealDepartureTime()
    {
        return realDepartureTime;
    }

    /**
     * @param realDepartureTime
     *            the realDepartureTime to set
     */
    public Fly setRealDepartureTime(DateTime realDepartureTime)
    {
        this.realDepartureTime = realDepartureTime;
        return this;
    }

    /**
     * @return the plannedArrivalTime
     */
    public DateTime getPlannedArrivalTime()
    {
        return plannedArrivalTime;
    }

    /**
     * @param plannedArrivalTime
     *            the plannedArrivalTime to set
     */
    public Fly setPlannedArrivalTime(DateTime plannedArrivalTime)
    {
        this.plannedArrivalTime = plannedArrivalTime;
        return this;
    }

    /**
     * @return the realArrivalTime
     */
    public DateTime getRealArrivalTime()
    {
        return realArrivalTime;
    }

    /**
     * @param realArrivalTime
     *            the realArrivalTime to set
     */
    public Fly setRealArrivalTime(DateTime realArrivalTime)
    {
        this.realArrivalTime = realArrivalTime;
        return this;
    }

    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public Fly setStatus(String status)
    {
        this.status = status;
        return this;
    }
}
