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

public class Country
{
    private Integer id;
    private Continent continent;
    private String acronym;
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
    public Country setId(Integer id)
    {
        this.id = id;
        return this;
    }

    /**
     * @return the continent
     */
    public Continent getContinent()
    {
        return continent;
    }

    /**
     * @param continent
     *            the continent to set
     */
    public Country setContinent(Continent continent)
    {
        this.continent = continent;
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
    public Country setName(String name)
    {
        this.name = name;
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
    public Country setAcronym(String acronym)
    {
        this.acronym = acronym;
        return this;
    }
}
