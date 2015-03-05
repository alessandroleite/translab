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
public enum DigitType
{
    
    REGULAR ("0", "Voo Regular"),
    
    EXTRA_COM_HOTRAN ("1", "Voo extra com HOTRAN"),
    
    EXTRA_SEM_HOTRAN ("2", "Voo extra sem HOTRAN"),
    
    RETORNO ("3", "Voo de retorno"),
    
    INCLUSAO_ETAPA_HOTRAN ("4", "Inclusão de etapa em um voo previsto em HOTRAN"),
    
    CARGUEIRO ("5", "Voo Cargueiro"),
    
    SERVICO ("6", "Voo de Serviço"),
    
    FRETAMENTO ("7", "Voo de fretamento"),
    
    CHARTER ("8", "Voo charter"),
    
    INSTRUCAO ("A", "Voo de instrução"),
    
    EXPERIENCIA("B", "Voo de experiência");
    
    private final String id;
    
    private final String description;

    private DigitType(String id, String description)
    {
        this.id = id;
        this.description = description;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    
    public static Optional<DigitType> valueOfFromId(String id)
    {
        Optional<DigitType> result = Optional.absent();
        
        for (DigitType type: values())
        {
            if (type.getId().equalsIgnoreCase(id))
            {
                result = Optional.of(type);
                break;
            }
        }
        
        return result;
    }
   
}
