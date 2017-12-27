package com.lukeyboy1111.fitba.apis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lukeyboy1111.fitba.apis.exceptions.PlayerNotFoundException;

import io.swagger.annotations.ApiModelProperty;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-10-09T16:11:27.745Z")
public class FitbaTeam
{
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name ;

    @JsonProperty("description")
    private String description;
    
    @JsonProperty("teamAbbreviation")
    private String teamAbbreviation;
    
    @JsonProperty("createdDt")
    private Date createdDt;
    
    @JsonProperty("players")
    private List<FitbaPlayer> players = new ArrayList<>();
    
    @SuppressWarnings("unused")
    //Private constructor for jackson to use.
    private FitbaTeam() 
    {
        createdDt = new Date();
    }
    
    public FitbaTeam(String id, Date createdDt)
    {
        this.id = id;
        this.createdDt = createdDt;
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

    @ApiModelProperty(value = "")
    public String getDescription()
    {
        return description;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getTeamAbbreviation()
    {
        return teamAbbreviation;
    }

    public Date getCreatedDt()
    {
        return createdDt;
    }

    public List<FitbaPlayer> getPlayers()
    {
        return players;
    }

    public void addPlayer(FitbaPlayer newPlayer)
    {
        players.add(newPlayer);
    }
    
    public Optional<FitbaPlayer> getPlayer(String playerId)
    {
        return players.stream().filter((FitbaPlayer s) -> s.getId().equals(playerId)).findFirst();
    }
    
    public void updatePlayer(FitbaPlayer player) throws PlayerNotFoundException
    {
        boolean playerExists = players.stream().anyMatch(s -> s.getId().equals(player.getId()));
        if (playerExists)
        {
            Function<FitbaPlayer, FitbaPlayer> swapMatchingPlayers = s -> s.getId().equals(player.getId()) ? player : s;
            players = players.stream().map(swapMatchingPlayers).collect(Collectors.toList());
        }
        else
        {
            throw new PlayerNotFoundException();
        }
    }
    
    public void removePlayer(String playerId) throws PlayerNotFoundException
    {
        boolean playerExists = players.stream().anyMatch(s -> s.getId().equals(playerId));
        if (!playerExists)
        {
            throw new PlayerNotFoundException();
        }
        else 
        {
            players = players.stream().filter(s -> !s.getId().equals(playerId)).collect(Collectors.toList());
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdDt == null) ? 0 : createdDt.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((teamAbbreviation == null) ? 0 : teamAbbreviation.hashCode());
        result = prime * result + ((players == null) ? 0 : players.hashCode());
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
        FitbaTeam other = (FitbaTeam) obj;
        if (createdDt == null)
        {
            if (other.createdDt != null)
                return false;
        }
        else if (!createdDt.equals(other.createdDt))
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
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (teamAbbreviation == null)
        {
            if (other.teamAbbreviation != null)
                return false;
        }
        else if (!teamAbbreviation.equals(other.teamAbbreviation))
            return false;
        if (players == null)
        {
            if (other.players != null)
                return false;
        }
        else if (!players.equals(other.players))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FitbaTeam [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", teamAbbreviation=");
        builder.append(teamAbbreviation);
        builder.append(", createdDt=");
        builder.append(createdDt);
        builder.append("]");
        return builder.toString();
    }

    public void setTeamAbbreviation(String number)
    {
        this.teamAbbreviation = number;
        
    }

    public void setDescription(String description)
    {
        this.description = description;
        
    }

    public void setName(String name)
    {
        this.name = name;
        
    }

   
    
}
