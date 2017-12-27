
package com.lukeyboy1111.fitba.apis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class FitbaPlayer
{
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("surname")
    private String surname;

    @JsonProperty("description")
    private String description;

    @JsonProperty("lastUpdated")
    private Date lastUpdated = new Date();

    @JsonProperty("components")
    private List<Component> components = new ArrayList<>();
    
    
    /**
     * @param id
     * @param name
     * @param description
     * @param lastUpdated
     */
    public FitbaPlayer(String name, String description, Date lastUpdated)
    {
        //Since this is an embedded document MongoDB won't generate an ID automatically
        id = ObjectId.get().toHexString();
        this.name = name;
        this.description = description;
        this.lastUpdated = lastUpdated;
    }
    
    /**
     * @param id
     * @param name
     * @param description
     * @param lastUpdated
     */
    public FitbaPlayer(String id, String name, String description, Date lastUpdated)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lastUpdated = lastUpdated;
        this.components = new ArrayList<Component>();
    }
    
    /**
     * @param id
     * @param name
     * @param description
     * @param lastUpdated
     * @param attributes
     */
    public FitbaPlayer(String id, String name, String description, Date lastUpdated, List<Component> components)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lastUpdated = lastUpdated;
        this.components = components;
    }

    /**
     * @param id
     * @param name
     * @param surname
     * @param description
     * @param lastUpdated
     * @param attributes
     */
    public FitbaPlayer(String id, String name, String surname, String description, Date lastUpdated,
			List<Component> components) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.description = description;
		this.lastUpdated = lastUpdated;
		this.components = components;
	}

	//Private constructor for reflection magic used by Jackson and Spring.
    private FitbaPlayer() 
    {
        id = ObjectId.get().toHexString();
    }

    public String getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getSurname()
    {
        return surname;
    }

    public String getDescription()
    {
        return description;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }
    
    public void updateLastUpdated()
    {
        lastUpdated = new Date();
    }
    
    public List<Component> getComponents()
    {
        return components;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((components == null) ? 0 : components.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FitbaPlayer other = (FitbaPlayer) obj;
        if (components == null)
        {
            if (other.components != null)
                return false;
        }
        else if (!components.equals(other.components))
            return false;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (lastUpdated == null)
        {
            if (other.lastUpdated != null)
                return false;
        }
        else if (!lastUpdated.equals(other.lastUpdated))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (surname == null)
        {
            if (other.surname != null)
                return false;
        }
        else if (!surname.equals(other.surname))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return String.format("FitbaPlayer [id=%s, name=%s, surname=%s, description=%s, lastUpdated=%s, components=%s]", id, name, surname, description, lastUpdated, components);
    }   
}
