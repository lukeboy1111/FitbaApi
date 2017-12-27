package com.lukeyboy1111.fitba.apis.service.impl;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lukeyboy1111.fitba.apis.exceptions.TeamNotFoundException;
import com.lukeyboy1111.fitba.apis.exceptions.PlayerNotFoundException;
import com.lukeyboy1111.fitba.apis.model.FitbaTeam;
import com.lukeyboy1111.fitba.apis.model.FitbaPlayer;
import com.lukeyboy1111.fitba.apis.repository.TeamRepository;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest
{
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamServiceImpl;
    
    @Mock
    private FitbaTeam dummyTeam;

    @Test
    public void fetchReturnsEmptyOptionalWhenNoTeamIsFound()
    {
        assertThat(teamServiceImpl.fetchTeam("NoExist"), is(Optional.empty()));
    }

    @Test
    public void fetchReturnsTeamWhenItExists()
    {
        FitbaTeam existingTeam = new FitbaTeam("Exists", new Date());
        when(teamRepository.findOne("Exists")).thenReturn(existingTeam);
        assertThat(teamServiceImpl.fetchTeam("Exists"), is(Optional.of(existingTeam)));
    }
    
    @Test
    public void newTeamIsSavedInTheDatabase()
    {
        FitbaTeam team = new FitbaTeam("1", new Date());
        when(teamRepository.save(team)).thenReturn(team);
        
        FitbaTeam savedTeam = teamServiceImpl.saveNewTeam(team);
        assertThat(savedTeam, hasProperty("id", is("1")));
    }
    
    @Test
    public void playerIsAddedToTeamWhenItIsSaved() throws TeamNotFoundException
    {
        FitbaTeam existingTeam = new FitbaTeam("1", new Date());
        FitbaPlayer existingPlayer= new FitbaPlayer("Mw Test player", "Description", new Date());
        existingTeam.addPlayer(existingPlayer);
        
        when(teamRepository.findOne("1")).thenReturn(existingTeam);
        when(teamRepository.exists("1")).thenReturn(true);
        when(teamRepository.save(existingTeam)).thenReturn(existingTeam);
        
        FitbaPlayer newSceheme = new FitbaPlayer("Mw Test player", "Description", new Date());
        FitbaTeam savedTeam = teamServiceImpl.addPlayerToTeam("1", newSceheme);
        
        assertThat(savedTeam.getPlayers(), hasSize(2));
    }
    
    @Test
    public void exceptionIsThrownWhenAddingPlayerToTeamThatDoesntExist()
    {
        when(teamRepository.findOne("1")).thenReturn(null);
        
        FitbaPlayer newSceheme = new FitbaPlayer("Mw Test player", "Description", new Date());
        try
        {
            teamServiceImpl.addPlayerToTeam("1", newSceheme);
            fail("Exception should have been thrown but wasn't");
        }
        catch (TeamNotFoundException e)
        {
            assertThat(e.getTeamId(), is("1"));
        }
    }
    
    @Test
    public void emptyOptionalIsReturnedWhenPlayerCantBeFound()
    {
        Optional<FitbaPlayer> player = teamServiceImpl.fetchPlayer("23rd", "sg3g");
        assertThat(player, is(Optional.empty()));
    }
    
    @Test
    public void whenTeamHasPlayerWithMatchingIdItIsReturned()
    {
        FitbaTeam existingTeam = new FitbaTeam("23rd", new Date());
        FitbaPlayer existingPlayer= new FitbaPlayer("Mw Test player", "Description", new Date());
        String playerId = existingPlayer.getId();
        existingTeam.addPlayer(existingPlayer);

        when(teamRepository.findOne("23rd")).thenReturn(existingTeam);

        
        Optional<FitbaPlayer> player = teamServiceImpl.fetchPlayer("23rd", playerId);
        assertThat(player, is(Optional.of(existingPlayer)));
    }
    
    @Test(expected=TeamNotFoundException.class)
    public void whenTeamDoesntExistAndPlayerIsSavedAnExceptionIsThrown() throws TeamNotFoundException, PlayerNotFoundException 
    {
        String teamId = "1";
        FitbaPlayer playerId = null;
        when(teamRepository.exists(teamId)).thenReturn(false);
        teamServiceImpl.savePlayer(teamId, playerId);
    }
    
    @Test(expected=PlayerNotFoundException.class)
    public void whenProjecExistsAndPlayerDoesntThenExceptionIsThrown() throws TeamNotFoundException, PlayerNotFoundException 
    {
        String teamId = "1";
        FitbaPlayer player = new FitbaPlayer("New Player", "Description of player", new Date());
        FitbaTeam teamWithNoPlayers = new FitbaTeam("1", new Date());

        when(teamRepository.exists(teamId)).thenReturn(true);
        when(teamRepository.findOne("1")).thenReturn(teamWithNoPlayers);
        
        teamServiceImpl.savePlayer(teamId, player);
    }
    
    @Test
    public void playerCanBeSavedWhenItIsAlreadyAttachedToATeam() throws TeamNotFoundException, PlayerNotFoundException 
    {
        String teamId = "1";
        FitbaPlayer player = new FitbaPlayer("New Player", "Description of player", new Date());
        FitbaTeam teamWithAPlayer = new FitbaTeam("1", new Date());
        teamWithAPlayer.addPlayer(player);

        when(teamRepository.exists(teamId)).thenReturn(true);
        when(teamRepository.findOne("1")).thenReturn(teamWithAPlayer);
        when(teamRepository.save(teamWithAPlayer)).thenReturn(teamWithAPlayer);
        
        teamServiceImpl.savePlayer(teamId, player);
        
        verify(teamRepository).save(teamWithAPlayer);
    }

    @Test
    public void playerHasItsLastUpdatedTimeUpdateBeforeItIsSaved() throws TeamNotFoundException, PlayerNotFoundException 
    {
        String teamId = "1";
        Date initialLastUpdated = new Date();
        FitbaPlayer player = new FitbaPlayer("123", "New Player", "Description of player", initialLastUpdated);
        FitbaTeam teamWithAPlayer = new FitbaTeam("1", new Date());
        teamWithAPlayer.addPlayer(player);
        
        when(teamRepository.exists(teamId)).thenReturn(true);
        when(teamRepository.findOne("1")).thenReturn(teamWithAPlayer);
        when(teamRepository.save(any(FitbaTeam.class))).thenAnswer(i -> i.getArgumentAt(0, FitbaTeam.class));

        teamServiceImpl.savePlayer(teamId, player);
        
        ArgumentCaptor<FitbaTeam> argument = ArgumentCaptor.forClass(FitbaTeam.class);
        verify(teamRepository).save(argument.capture());
        
        List<FitbaPlayer> updatePlayers = argument.getValue().getPlayers();
        assertThat(updatePlayers, hasSize(1));
        assertThat(updatePlayers.get(0).getLastUpdated(), is(greaterThan(initialLastUpdated)));
    }
    
    @Test
    public void playerCanBeDeletedFromAnExistingTeam() throws TeamNotFoundException, PlayerNotFoundException 
    {
        String teamId = "1";
        FitbaPlayer player = new FitbaPlayer("New Player", "Description of player", new Date());
        FitbaTeam teamWithAPlayer = new FitbaTeam("1", new Date());
        teamWithAPlayer.addPlayer(player);

        when(teamRepository.exists(teamId)).thenReturn(true);
        when(teamRepository.findOne("1")).thenReturn(teamWithAPlayer);
        
        teamServiceImpl.deletePlayers(teamId, player.getId());
        
        ArgumentCaptor<FitbaTeam> argument = ArgumentCaptor.forClass(FitbaTeam.class);
        verify(teamRepository).save(argument.capture());
        
        assertThat(argument.getValue().getId(), is("1"));
        assertThat(argument.getValue().getPlayers(), hasSize(0));
    }
    
    @Test
    public void fetchAllReturnsTeamsWhenTheyExist()
    {
        List<FitbaTeam> myTeams = new ArrayList<FitbaTeam>();
        FitbaTeam proj1 = new FitbaTeam("Proj1", new Date());
        FitbaTeam proj2 = new FitbaTeam("Proj2", new Date());
        
        myTeams.add(proj1);
        myTeams.add(proj2);
        
        when(teamRepository.findAll()).thenReturn(myTeams);
        assertThat(teamServiceImpl.fetchAllTeams(),is(Optional.of(myTeams)));
    }
    
    @Test
    public void fetchAllReturnsPlayersForTeamWhenTheyExist()
    {
        List<FitbaPlayer> myPlayers = new ArrayList<FitbaPlayer>();
        
        FitbaPlayer player1 = new FitbaPlayer("New Player 1", "Description of player", new Date());
        FitbaPlayer player2 = new FitbaPlayer("New Player 2", "Description of player", new Date());
        
        myPlayers.add(player1);
        myPlayers.add(player2);
        when(teamRepository.exists("proj1")).thenReturn(true);
        when(teamRepository.findOne("proj1")).thenReturn(dummyTeam);
    	when(dummyTeam.getId()).thenReturn("proj1");
        when(dummyTeam.getPlayers()).thenReturn(myPlayers);
        
        assertThat(teamServiceImpl.fetchPlayers("proj1"),is(Optional.of(myPlayers)));
    }
        
    public void teamCanBeUpdatedWithNewDetails() throws TeamNotFoundException
    {
        FitbaTeam proj1 = new FitbaTeam("Proj1", new Date());
        
        when(teamRepository.exists("Proj1")).thenReturn(true);
        when(teamRepository.findOne("Proj1")).thenReturn(proj1);
        
        teamServiceImpl.updateTeamWithNewDetails("Proj1", "Projjy", "123", "My Projjy");
        ArgumentCaptor<FitbaTeam> argument = ArgumentCaptor.forClass(FitbaTeam.class);
        verify(teamRepository).save(argument.capture());

        FitbaTeam result = argument.getValue();
        assertEquals(result.getName(),"Projjy");
        assertEquals(result.getDescription(),"My Projjy");
        assertEquals(result.getTeamAbbreviation(),"123");
    }
}
;