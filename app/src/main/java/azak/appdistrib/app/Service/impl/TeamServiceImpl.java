package azak.appdistrib.app.Service.impl;

import azak.appdistrib.app.Dao.PlayerRepository;
import azak.appdistrib.app.Dao.TeamRepository;
import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Exception.PlayerNotFoundException;
import azak.appdistrib.app.Service.TeamService;
import azak.appdistrib.app.Exception.TeamNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return this.teamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return this.teamRepository.findById(id).orElseThrow(TeamNotFoundException::new);
    }

    @Override
    public Team createTeam(Team team) {
        Team savedTeam = this.teamRepository.save(team);
        if (team.getPlayers() != null) {
            List<Player> players = new ArrayList<>(team.getPlayers());
            players.forEach(player -> {
                if (player.getId() != null) {
                    if (this.playerRepository.existsById(player.getId())) {
                        Player persistedPlayer = this.playerRepository.findById(player.getId()).get();
                        persistedPlayer.setTeam(savedTeam);
                    } else {
                        throw new PlayerNotFoundException(player.getId());
                    }
                }
            });
            savedTeam.setPlayers(players);
        }
        return this.teamRepository.save(savedTeam);
    }

    @Override
    public Team updateTeam(Long id, Team team) {
        return this.teamRepository.findById(id)
                .map(existingTeam -> {
                    existingTeam.setName(team.getName());
                    existingTeam.setCity(team.getCity());
                    existingTeam.setCoach(team.getCoach());
                    existingTeam.setSport(team.getSport());
                    existingTeam.setStadium(team.getStadium());
                    List<Player> listPlayers = new ArrayList<>();
                    List<Player> teamPlayers = new ArrayList<>(team.getPlayers());
                    teamPlayers.forEach(player -> {
                        if (player.getId() != null) {
                            if (this.playerRepository.existsById(player.getId())) {
                                this.playerRepository.findById(player.getId()).get().setTeam(existingTeam);
                                listPlayers.add(this.playerRepository.findById(player.getId()).get());
                            } else {
                                throw new PlayerNotFoundException(player.getId());
                            }
                        }
                    existingTeam.setPlayers(listPlayers);
                    });
                return this.teamRepository.save(existingTeam);
                })
                .orElseThrow(TeamNotFoundException::new);
    }

    @Override
    public void deleteTeam(Long id) {
        if (this.teamRepository.existsById(id)) {
            for(Player player : this.teamRepository.findById(id).get().getPlayers()) {
                player.setTeam(null);
            }
            this.teamRepository.deleteById(id);
    }   else
            throw new TeamNotFoundException();
    }

    @Override
    public Team saveTeam(Team team) {
        return this.teamRepository.save(team);
    }
}
