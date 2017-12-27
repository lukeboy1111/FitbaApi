package com.lukeyboy1111.fitba.apis.api.endpoints.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lukeyboy1111.fitba.apis.api.endpoints.TeamApi;
import com.lukeyboy1111.fitba.apis.exceptions.TeamNotFoundException;
import com.lukeyboy1111.fitba.apis.exceptions.PlayerNotFoundException;
import com.lukeyboy1111.fitba.apis.model.FitbaPlayer;
import com.lukeyboy1111.fitba.apis.model.FitbaTeam;
import com.lukeyboy1111.fitba.apis.service.TeamService;

import io.swagger.annotations.ApiParam;

@RestController
public class TeamApiController implements TeamApi {

private static final Logger LOGGER = LoggerFactory.getLogger(TeamApiController.class);
    
    private TeamService teamService;

    public TeamApiController(TeamService projService)
    {
        this.teamService = projService;
    }

    public ResponseEntity<FitbaTeam> findFitbaTeamById(
            @ApiParam(value = "ID of team to fetch", required = true) @PathVariable("id") String id)
    {
        return teamService.fetchTeam(id)
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @Override
    public ResponseEntity<List<FitbaTeam>> getAllTeams()
    {
        return teamService.fetchAllTeams()
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Override
    public ResponseEntity<FitbaTeam> saveNewTeam(@RequestBody FitbaTeam teamToSave)
    {
        
        FitbaTeam createdTeam = teamService.saveNewTeam(teamToSave);
        
        
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FitbaTeam> addNewPlayerToTeam(@PathVariable("id") String id, @RequestBody FitbaPlayer newPlayer)
    {
        FitbaTeam updatedTeam;
        try
        {
            updatedTeam = teamService.addPlayerToTeam(id, newPlayer);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        }
        catch (TeamNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<FitbaPlayer> fetchFitbaPlayerById(@PathVariable("teamId") String teamId, @PathVariable("playerId") String playerId)
    {
        return teamService.fetchPlayer(teamId, playerId)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<FitbaPlayer> savePlayer(@PathVariable("teamId") String teamId, 
            @PathVariable("playerId") String playerId, @RequestBody FitbaPlayer player)
    {
        try
        {
            FitbaPlayer savePlayer = teamService.savePlayer(teamId, player);
            return new ResponseEntity<>(savePlayer, HttpStatus.OK);
        }
        catch (TeamNotFoundException | PlayerNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<FitbaTeam> deleteFitbaPlayerForTeam(@PathVariable("teamId") String teamId, @PathVariable("playerId") String playerId)
    {
        try
        {
            FitbaTeam team = teamService.deletePlayers(teamId, playerId);
            return new ResponseEntity<>(team, HttpStatus.OK);
        }
        catch (TeamNotFoundException | PlayerNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


	@Override
	public ResponseEntity<List<FitbaPlayer>> getAllPlayersForFitbaTeam(String teamId) {
		Optional<List<FitbaPlayer>> items = teamService.fetchPlayers(teamId);
		
		return teamService.fetchPlayers(teamId)
                .map(player -> new ResponseEntity<>(player, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@Override
    public ResponseEntity<FitbaTeam> updateFitbaTeam(String id, String name, String number, String description)
    {
        FitbaTeam updatedTeam;
        try
        {
            updatedTeam = teamService.updateTeamWithNewDetails(id, name,number,description);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        }
        catch (TeamNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

}
