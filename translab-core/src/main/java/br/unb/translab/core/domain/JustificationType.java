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
