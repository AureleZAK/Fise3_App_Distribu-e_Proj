package azak.appdistrib.app.Service.impl;

import azak.appdistrib.app.Dao.TeamRepository;
import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Exception.PlayerNotFoundException;
import azak.appdistrib.app.Exception.TeamNotFoundException;
import azak.appdistrib.app.Service.PlayerService;
import azak.appdistrib.app.Service.TeamService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import azak.appdistrib.app.Dao.PlayerRepository;

import java.util.List;

@Transactional
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return this.playerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Player getPlayerById(Long id) {
        return this.playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @Override
    public Player createPlayer(Player player) {
        if (player.getTeam() != null) {
            if (player.getTeam().getId() != null) {
                Team team = teamService.getTeamById(player.getTeam().getId());
                player.setTeam(team);
                team.addPlayers(player);
            } else {
                throw new TeamNotFoundException();
            }
        } else {
            player.setTeam(null);
        }
        return this.playerRepository.save(player);
    }


    @Override
    public Player updatePlayer(Long id, Player player) {
        return this.playerRepository.findById(id)
                .map(existingPlayer -> {
                    existingPlayer.setAge(player.getAge());
                    existingPlayer.setFirstName(player.getFirstName());
                    existingPlayer.setLastName(player.getLastName());
                    existingPlayer.setNationality(player.getNationality());
                    existingPlayer.setSport(player.getSport());
                    existingPlayer.setNumber(player.getNumber());
                    existingPlayer.setHeight(player.getHeight());
                    existingPlayer.setWeight(player.getWeight());
                    if (existingPlayer.getTeam() != player.getTeam()) {
                        if (existingPlayer.getTeam() != null)
                            existingPlayer.getTeam().removePlayers(existingPlayer);
                        if (player.getTeam() != null) {
                            if (!teamRepository.existsById(player.getTeam().getId()))
                                throw new TeamNotFoundException();
                            existingPlayer.setTeam(player.getTeam());
                            player.getTeam().addPlayers(existingPlayer);
                        } else
                            existingPlayer.setTeam(null);
                    }
                    return this.playerRepository.save(existingPlayer);
                })
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @Override
    public void deletePlayer(Long id) {
        if (this.playerRepository.existsById(id)) {
            Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
            if (player.getTeam() != null) {
                teamService.getTeamById(player.getTeam().getId()).removePlayers(player);
            }
            this.playerRepository.deleteById(id);
        } else {
            throw new PlayerNotFoundException(id);
        }
    }


    @Override
    public Player savePlayer(Player player) {
        return this.playerRepository.save(player);
    }
}
