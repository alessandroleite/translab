package br.unb.translab.core.domain;

import com.google.common.base.Optional;

/**
 * See <a href="http://www2.anac.gov.br/biblioteca/iac/IAC1504.pdf">IAC1504</a> 
 */
public enum FlyType
{
    CARGUEIRO('C', "Cargueiro"),
    
    ESPECIAL ('E', "Especial"),
    
    CARGUEIRO_INTERNATIONAL('G', "Cargueiro Internacional"),
    
    SUB_REGIONAL('H', "Sub-regional"),
    
    INTERNATIONAL ('I', "Internacional"),
    
    REDE_POSTAL ('L', "Rede Postal"),
    
    NACIONAL ('N', "Nacional"),
    
    REGIONAL ('R', "Regional");

    private final Character acronym;
    private final String description;

    private FlyType(char acronym, String description)
    {
        this.acronym = acronym;
        this.description = description;
    }

    /**
     * @return the acronym
     */
    public Character getAcronym()
    {
        return acronym;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    public static Optional<FlyType> valueOfFromId(String id)
    {
        Optional<FlyType> result = Optional.absent();
        
        for (FlyType type: values())
        {
            if (type.getAcronym().equals(id.charAt(0)))
            {
                result = Optional.of(type);
                break;
            }
        }
        
        return result;
    }
}
