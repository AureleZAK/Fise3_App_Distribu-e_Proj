package azak.appdistrib.app;

import azak.appdistrib.app.Dao.PlayerRepository;
import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Service.PlayerService;
import azak.appdistrib.app.Service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    private Team TeamNullPlayer;
    private Team TeamValidPlayer;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerService;

    @BeforeEach
    public void init() {

        TeamNullPlayer = new Team();
        TeamNullPlayer.setName("NullPlayer");
        TeamNullPlayer.setCity("Paris");
        TeamNullPlayer.setSport("Hockey");
        TeamNullPlayer.setCoach("Coach");
        TeamNullPlayer.setStadium("Stadium");

        TeamValidPlayer = new Team();
        TeamValidPlayer.setName("ValidPlayer");
        TeamValidPlayer.setCity("Paris");
        TeamValidPlayer.setSport("Hockey");
        TeamValidPlayer.setCoach("Coach");
        TeamValidPlayer.setStadium("Stadium");

    }

    @Test
    public void testGetAllTeams() {
        // On a déjà 5 équipes dans la base de données
        List<Team> teams = this.teamService.getAllTeams();
        assertEquals(5L, teams.size());
    }


    @Test
    public void testGetTeamsById() {
        Team foundTeam = this.teamService.getTeamById(1L);
        assertEquals("FC Barcelona", foundTeam.getName());
    }

    @Test
    public void testCreateTeam() {
        TeamValidPlayer.addPlayers(this.playerRepository.findById(1L).get());

        Team createdNullTeam = this.teamService.createTeam(TeamNullPlayer);
        assertEquals(0, createdNullTeam.getPlayers().size());

        Team createdPlayerTeam = this.teamService.createTeam(TeamValidPlayer);
        assertEquals(1, createdPlayerTeam.getPlayers().size());
        assertEquals("Lionel", createdPlayerTeam.getPlayers().get(0).getFirstName());

        List<Team> teams = this.teamService.getAllTeams();
        assertEquals(7L, teams.size());

        this.teamService.deleteTeam(createdNullTeam.getId());
        this.teamService.deleteTeam(createdPlayerTeam.getId());

    }

    @Test
    public void testUpdateTeam() {

        Team TestTeam = new Team();
        TestTeam.setName("TestTeam");

        Team createdNullTeam = this.teamService.createTeam(TeamNullPlayer);
        Team createdPlayerTeam = this.teamService.createTeam(TeamValidPlayer);

        List<Team> test = this.teamService.getAllTeams();


        createdNullTeam = this.teamService.updateTeam(createdNullTeam.getId(), TestTeam);
        assertEquals(0, createdNullTeam.getPlayers().size());

        createdPlayerTeam = this.teamService.updateTeam(createdPlayerTeam.getId(), TestTeam);
        assertEquals(0, createdPlayerTeam.getPlayers().size());

        TestTeam.addPlayers(this.playerRepository.findById(1L).get());

        createdNullTeam = this.teamService.updateTeam(createdNullTeam.getId(), TestTeam);
        assertEquals(1, createdNullTeam.getPlayers().size());

        createdPlayerTeam = this.teamService.updateTeam(createdPlayerTeam.getId(), TestTeam);
        assertEquals(1, createdPlayerTeam.getPlayers().size());

        List<Team> teams = this.teamService.getAllTeams();
        assertEquals(7L, teams.size());

        this.teamService.deleteTeam(createdNullTeam.getId());
        this.teamService.deleteTeam(createdPlayerTeam.getId());
    }

    @Test
    public void testDeleteTeam() {
        List<Player> players = this.teamService.getTeamById(5L).getPlayers();
        List<Team> teams = this.teamService.getAllTeams();
        players.forEach(player -> {
            assertEquals(5L, player.getTeam().getId());
        });

        assertEquals(5, teams.size());
        this.teamService.deleteTeam(5L);

        teams = this.teamService.getAllTeams();

        players.forEach(player -> {
            assertNull(player.getTeam());
        });

        assertEquals(4, teams.size());
    }
}

