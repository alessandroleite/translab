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

import static com.google.common.base.Objects.*;

public class Airport
{
    private Integer _id;
    private City _city;
    private String _acronym;
    private String _name;
    private String _description;

    /**
     * @return the id
     */
    public Integer getId()
    {
        return _id;
    }

    /**
     * @param id
     *            the id to set
     */
    public Airport setId(Integer id)
    {
        this._id = id;
        return this;
    }

    /**
     * @return the city
     */
    public City getCity()
    {
        return _city;
    }

    /**
     * @param city
     *            the city to set
     */
    public Airport setCity(City city)
    {
        this._city = city;
        return this;
    }

    /**
     * @return the acronym
     */
    public String getAcronym()
    {
        return _acronym;
    }

    /**
     * @param acronym
     *            the acronym to set
     */
    public Airport setAcronym(String acronym)
    {
        this._acronym = acronym;
        return this;
    }
    

    /**
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * @param name the name to set
     */
    public Airport setName(String name)
    {
        this._name = name;
        return this;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * @param description
     *            the description to set
     */
    public Airport setDescription(String description)
    {
        this._description = description;
        return this;
    }
    
    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                          .add("acronym", this.getAcronym())
                          .add("name", getName())
                          .add("city", getCity())
                          .add("description", getDescription())
                          .add("id", getId())
                          .omitNullValues()
                          .toString();
    }
    
    
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }
        
        Airport other = (Airport) obj;
        
        return (equal(this.getCity(), other.getCity()) && equal(this.getName(), other.getName())) ||
               (this.getId() != null && this.getId().equals(other.getId())); 
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.getId(), this.getName(), this.getCity()) * 13;
    }
}
