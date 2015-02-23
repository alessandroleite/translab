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

import com.google.common.base.Objects;


public class City
{
    private Integer id;
    private Country country;
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
    public City setId(Integer id)
    {
        this.id = id;
        return this;
    }

    /**
     * @return the country
     */
    public Country getCountry()
    {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public City setCountry(Country country)
    {
        this.country = country;
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
    public City setName(String name)
    {
        this.name = name;
        return this;
    }

    @Override
    public int hashCode()
    {
       return Objects.hashCode(this.getId(), this.getName(), this.getCountry()) * 31;
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
        
        City other = (City) obj;
        return (Objects.equal(this.getName(), other.getName()) && Objects.equal(this.getCountry(), other.getCountry())) ||
               (this.getId() != null && Objects.equal(this.getId(), other.getId()));
    }
}
