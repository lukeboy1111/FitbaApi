package com.lukeyboy1111.fitba.apis.service;

import java.util.List;
import java.util.Optional;

import com.lukeyboy1111.fitba.apis.exceptions.TeamNotFoundException;
import com.lukeyboy1111.fitba.apis.exceptions.PlayerNotFoundException;
import com.lukeyboy1111.fitba.apis.model.FitbaTeam;
import com.lukeyboy1111.fitba.apis.model.FitbaPlayer;

/**
 * 
 */
public interface TeamService
{
    public List<FitbaTeam> fetchAllFitbaTeams();

    public Optional<FitbaTeam> fetchTeam(String id);

    public FitbaTeam saveNewTeam(FitbaTeam teamDetails);

    public FitbaTeam addPlayerToTeam(String string, FitbaPlayer newPlayer) throws TeamNotFoundException;

    public Optional<FitbaPlayer> fetchPlayer(String id, String playerId);
    
    public FitbaPlayer savePlayer(String teamId, FitbaPlayer player) throws TeamNotFoundException, PlayerNotFoundException;

    public FitbaTeam deletePlayers(String teamId, String playerId) throws PlayerNotFoundException, TeamNotFoundException;
    
    public Optional<List<FitbaTeam>> fetchAllTeams();


	public Optional<List<FitbaPlayer>> fetchPlayers(String teamId);
    public FitbaTeam updateTeamWithNewDetails(String id, String name, String number, String description) throws TeamNotFoundException;
}

