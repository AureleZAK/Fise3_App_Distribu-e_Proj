package azak.appdistrib.app.Service;

import azak.appdistrib.app.Domain.Team;

import java.util.List;

public interface TeamService {

    public List<Team> getAllTeams();

    public Team getTeamById(Long id);

    public Team createTeam(Team team);

    public Team updateTeam(Long id,Team team);

    public void deleteTeam(Long id);

    public Team saveTeam(Team team);
}
