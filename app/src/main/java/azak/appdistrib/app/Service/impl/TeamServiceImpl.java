package azak.appdistrib.app.Service.impl;

import azak.appdistrib.app.Dao.PlayerRepository;
import azak.appdistrib.app.Dao.TeamRepository;
import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Service.TeamService;
import azak.appdistrib.app.Exception.TeamNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return this.teamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return this.teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Override
    public Team createTeam(Team team) {
        if (team.getPlayers() != null) {
            team.getPlayers().forEach(player -> player.setTeam(team));
        }
        return this.teamRepository.save(team);
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
                    if (team.getPlayers().equals(existingTeam.getPlayers())) {
                        existingTeam.getPlayers().clear();
                        existingTeam.getPlayers().addAll(team.getPlayers());
                    }
                    return this.teamRepository.save(existingTeam);
                })
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    @Override
    public void deleteTeam(Long id) {
        if (this.teamRepository.existsById(id)) {
            for(Player player : this.teamRepository.findById(id).get().getPlayers()) {
                player.setTeam(null);
            }
            this.teamRepository.deleteById(id);
    }   else
            throw new TeamNotFoundException(id);
    }

    @Override
    public Team saveTeam(Team team) {
        return this.teamRepository.save(team);
    }
}
