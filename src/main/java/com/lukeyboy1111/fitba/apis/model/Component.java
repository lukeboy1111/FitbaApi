package com.lukeyboy1111.fitba.apis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Component. Just now this class is used both in the API and when storing in
 * the DB so will have some JPA annotations and some JSON / Swagger annotations
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-09T16:11:27.745Z")
public class Component
{
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name ;

    @JsonProperty("ref")
    private String ref;
    
    @JsonProperty("type")
    private String type;

    private List<Attribute> attributes = new ArrayList<Attribute>();
    
    private Component()
    {
        
    }
    
    

    /**
     * @param id
     * @param name
     * @param ref
     * @param type
     */
    public Component(String id, String name, String ref, String type, List<Attribute> attributes)
    {
        super();
        this.id = id;
        this.name = name;
        this.ref = ref;
        this.type = type;
        this.attributes = attributes;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getId()
    {
        return id;
    }

    public Component name(String name)
    {
        this.name = name;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getName()
    {
        return name;
    }

    @ApiModelProperty(value = "")
    public String getRef()
    {
        return ref;
    }
    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getType()
    {
        return type;
    }
    
    public List<Attribute> getAttributes()
    {
        return attributes;
    }



    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((ref == null) ? 0 : ref.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Component other = (Component) obj;
        if (attributes == null)
        {
            if (other.attributes != null)
                return false;
        }
        else if (!attributes.equals(other.attributes))
            return false;
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
        if (ref == null)
        {
            if (other.ref != null)
                return false;
        }
        else if (!ref.equals(other.ref))
            return false;
        if (type == null)
        {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return String.format("Component [id=%s, name=%s, ref=%s, type=%s, attributes=%s]", id, name, ref, type, attributes);
    }
    
}
