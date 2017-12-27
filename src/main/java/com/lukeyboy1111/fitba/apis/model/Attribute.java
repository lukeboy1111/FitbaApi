package com.lukeyboy1111.fitba.apis.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-11T11:18:45.095Z")
public class Attribute
{
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("units")
    private String units;

    private String value;

    private Attribute()
    {
        
    }
    
    /**
     * @param id
     * @param name
     * @param units
     */
    public Attribute(String id, String name, String units, String value)
    {
        super();
        this.id = id;
        this.name = name;
        this.units = units;
        this.value = value;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getId()
    {
        return id;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getName()
    {
        return name;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getUnits()
    {
        return units;
    }
    
    public String getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        Attribute other = (Attribute) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (units == null)
        {
            if (other.units != null)
                return false;
        }
        else if (!units.equals(other.units))
            return false;
        if (value == null)
        {
            if (other.value != null)
                return false;
        }
        else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return String.format("Attribute [id=%s, name=%s, units=%s, value=%s]", id, name, units, value);
    }
}
