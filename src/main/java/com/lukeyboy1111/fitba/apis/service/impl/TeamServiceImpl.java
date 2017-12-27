package com.lukeyboy1111.fitba.apis.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lukeyboy1111.fitba.apis.exceptions.TeamNotFoundException;
import com.lukeyboy1111.fitba.apis.exceptions.PlayerNotFoundException;
import com.lukeyboy1111.fitba.apis.model.FitbaTeam;
import com.lukeyboy1111.fitba.apis.model.FitbaPlayer;
import com.lukeyboy1111.fitba.apis.repository.TeamRepository;
import com.lukeyboy1111.fitba.apis.service.TeamService;

/**
 * 
 */
@Service
public class TeamServiceImpl implements TeamService
{
    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository repo)
    {
        super();
        this.teamRepository = repo;
    }

    @Override
    public List<FitbaTeam> fetchAllComponents()
    {
        return teamRepository.findAll();
    }

    @Override
    public Optional<FitbaTeam> fetchTeam(String id)
    {
        FitbaTeam team = teamRepository.findOne(id);
        return Optional.ofNullable(team);
    }

    @Override
    public FitbaTeam saveNewTeam(FitbaTeam team)
    {
        return teamRepository.save(team);
    }

    @Override
    public FitbaTeam addPlayerToTeam(String string, FitbaPlayer newPlayer) throws TeamNotFoundException
    {
        if (teamRepository.exists(string))
        {
            FitbaTeam team = teamRepository.findOne(string);
            team.addPlayer(newPlayer);
            return teamRepository.save(team);
        }
        else throw new TeamNotFoundException(string);
    }
    
    @Override
    public FitbaTeam updateTeamWithNewDetails(String id, String name, String number, String description) throws TeamNotFoundException
    {
        if (teamRepository.exists(id))
        {
            FitbaTeam team = teamRepository.findOne(id);
            team.setName(name);
            team.setTeamAbbreviation(number);
            team.setDescription(description);
            return teamRepository.save(team);
        }
        else throw new TeamNotFoundException(id);
    };

    @Override
    public Optional<FitbaPlayer> fetchPlayer(String teamId, String playerId)
    {
        Optional<FitbaTeam> team = Optional.ofNullable(teamRepository.findOne(teamId));
        return team.flatMap(p -> p.getPlayer(playerId));
    }

    @Override
    public FitbaPlayer savePlayer(String teamId, FitbaPlayer player) throws TeamNotFoundException, PlayerNotFoundException
    {
        if (teamRepository.exists(teamId))
        {
            FitbaTeam team = teamRepository.findOne(teamId);
            player.updateLastUpdated();
            team.updatePlayer(player);
            
            FitbaTeam savedTeam = teamRepository.save(team);
            
            return savedTeam
                    .getPlayer(player.getId())
                    .orElseThrow(PlayerNotFoundException::new);
        }
        else throw new TeamNotFoundException(teamId);
    }

    @Override
    public FitbaTeam deletePlayers(String teamId, String playerId) throws PlayerNotFoundException, TeamNotFoundException
    {
        if (teamRepository.exists(teamId))
        {
            FitbaTeam team = teamRepository.findOne(teamId);
            team.removePlayer(playerId);
            return teamRepository.save(team);
        }
        else throw new TeamNotFoundException(teamId);
    };
    
    @Override
    public Optional<List<FitbaTeam>> fetchAllTeams()
    {
        List<FitbaTeam> teams = teamRepository.findAll();
        return Optional.ofNullable(teams);
    }


	@Override
	public Optional<List<FitbaPlayer>> fetchPlayers(String teamId) {
		if (teamRepository.exists(teamId))
        {
			FitbaTeam team = teamRepository.findOne(teamId);
			return Optional.ofNullable(team.getPlayers());
        }
		return Optional.empty();
	};
}
