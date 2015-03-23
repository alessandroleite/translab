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
package br.unb.translab.core.domain;

import com.google.common.base.Optional;

/**
 * See <a href="http://www2.anac.gov.br/biblioteca/iac/IAC1504.pdf">IAC1504</a> 
 */
public enum FlightType
{
    /**
     * 
     */
    CARGUEIRO('C', "Cargueiro"),
    
    /**
     * 
     */
    ESPECIAL ('E', "Especial"),
    
    /**
     * 
     */
    CARGUEIRO_INTERNATIONAL('G', "Cargueiro Internacional"),
    
    /**
     * 
     */
    SUB_REGIONAL('H', "Sub-regional"),
    
    /**
     * 
     */
    INTERNATIONAL ('I', "Internacional"),
    
    /**
     * 
     */
    REDE_POSTAL ('L', "Rede Postal"),
    
    /**
     * 
     */
    NACIONAL ('N', "Nacional"),
    
    /**
     * 
     */
    REGIONAL ('R', "Regional");

    /**
     * Acronym
     */
    private final Character _acronym;
    
    /**
     * Description
     */
    private final String _description;

    private FlightType(char acronym, String description)
    {
        this._acronym = acronym;
        this._description = description;
    }

    /**
     * @return the acronym
     */
    public Character getAcronym()
    {
        return _acronym;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return _description;
    }

    public static Optional<FlightType> valueOfFromId(String id)
    {
        Optional<FlightType> result = Optional.absent();
        
        for (FlightType type: values())
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
