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

public class Airport
{
    private Integer id;
    private City city;
    private String acronym;
    private String description;

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
    public Airport setId(Integer id)
    {
        this.id = id;
        return this;
    }

    /**
     * @return the city
     */
    public City getCity()
    {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public Airport setCity(City city)
    {
        this.city = city;
        return this;
    }

    /**
     * @return the acronym
     */
    public String getAcronym()
    {
        return acronym;
    }

    /**
     * @param acronym
     *            the acronym to set
     */
    public Airport setAcronym(String acronym)
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
     * @param description
     *            the description to set
     */
    public Airport setDescription(String description)
    {
        this.description = description;
        return this;
    }
}
