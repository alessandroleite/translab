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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class JustificationType
{
    private Integer id;
    private String acronym;
    private String description;
    
    /**
     * @return the acronym
     */
    public String getAcronym()
    {
        return acronym;
    }
    
    /**
     * @param acronym the acronym to set
     */
    public JustificationType setAcronym(String acronym)
    {
        this.acronym = acronym;
        return this;
    }
    
    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public JustificationType setDescription(String description)
    {
        this.description = description;
        return this;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public JustificationType setId(Integer id)
    {
        this.id = id;
        return this;
    }
    
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.getId(), this.getAcronym());
    }

    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        
        JustificationType other = (JustificationType) obj;
        
        return Objects.equal(this.getAcronym(), other.getAcronym()) || 
               (this.getId() != null && this.getId().equals(other.getId()));
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                          .add("id", this.getId())
                          .add("acronym", getAcronym())
                          .add("description", getDescription())
                          .omitNullValues()
                          .toString();
    }
}
