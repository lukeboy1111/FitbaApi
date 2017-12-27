package com.lukeyboy1111.fitba.apis.model;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import com.lukeyboy1111.fitba.apis.exceptions.PlayerNotFoundException;

public class FitbaTeamTest
{

    @Test
    public void newTeamIsCreatedWithAnEmptyListOfPlayers()
    {
        assertThat(new FitbaTeam("Test ID", new Date()).getPlayers(), is(not(nullValue())));
    }
    
    @Test
    public void updatePlayerOverwritesAnyExistingPlayerWithAMatchingId() throws PlayerNotFoundException 
    {
        FitbaTeam team = new FitbaTeam("123dsg", new Date());
        FitbaPlayer player = new FitbaPlayer("123", "Name", "description", new Date());
        team.addPlayer(player);
        
        FitbaPlayer newPlayer = new FitbaPlayer("123", "Update name", "updated description", new Date());
        team.updatePlayer(newPlayer);
        
        assertThat(team.getPlayer("123"), is(Optional.of(newPlayer)));
    }
}
