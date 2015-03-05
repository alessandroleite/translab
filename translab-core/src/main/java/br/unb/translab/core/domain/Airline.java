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

public class Airline
{
    private Integer id;
    private String oaci;
    private String name;

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public Airline setId(Integer id)
    {
        this.id = id;
        return this;
    }

    /**
     * @return the oaci
     */
    public String getOaci()
    {
        return oaci;
    }

    /**
     * @param oaci
     *            the oaci to set
     */
    public Airline setOaci(String oaci)
    {
        this.oaci = oaci;
        return this;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public Airline setName(String name)
    {
        this.name = name;
        return this;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.getId(), this.getOaci(), this.getName());
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }
        
        Airline other = (Airline) obj;
        
        return (this.getId() != null && this.getId().equals(other.getId())) ||
               (Objects.equal(this.getName(), other.getName()) && Objects.equal(this.getOaci(), other.getOaci()));
    }
    
    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                          .add("id", this.getId())
                          .add("oaci", this.getOaci())
                          .add("name", this.getName())
                          .omitNullValues()
                          .toString();
    }
}
