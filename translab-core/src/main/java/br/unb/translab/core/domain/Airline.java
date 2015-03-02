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
