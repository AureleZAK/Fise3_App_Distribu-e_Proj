package azak.appdistrib.app;

import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Exception.TeamNotFoundException;
import azak.appdistrib.app.Service.PlayerService;
import azak.appdistrib.app.Service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerServiceTest {


    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    private Player playerNullTeam;
    private Player playerValidTeam;
    private Player playerNotValidTeam;
    private Team teamNotValid;

    @BeforeEach
    public void init() {

        playerNullTeam = new Player();
        playerNullTeam.setFirstName("Null");
        playerNullTeam.setLastName("Testeur");
        playerNullTeam.setNationality("French");
        playerNullTeam.setAge(25);
        playerNullTeam.setHeight(180.0f);
        playerNullTeam.setWeight(75.0f);
        playerNullTeam.setSport("Hockey");
        playerNullTeam.setTeam(null);

        playerValidTeam = new Player();
        playerValidTeam.setFirstName("Valid");
        playerValidTeam.setLastName("Testeur");
        playerValidTeam.setNationality("French");
        playerValidTeam.setAge(25);
        playerValidTeam.setHeight(180.0f);
        playerValidTeam.setWeight(75.0f);
        playerValidTeam.setSport("Hockey");
        playerValidTeam.setTeam(this.teamService.getTeamById(1L));

        teamNotValid = new Team();
        this.teamService.createTeam(teamNotValid);
        long notValidId = teamNotValid.getId();
        this.teamService.deleteTeam(notValidId);

        playerNotValidTeam = new Player();
        playerNotValidTeam.setFirstName("NotValid");
        playerNotValidTeam.setLastName("Testeur");
        playerNotValidTeam.setNationality("French");
        playerNotValidTeam.setAge(25);
        playerNotValidTeam.setHeight(180.0f);
        playerNotValidTeam.setWeight(75.0f);
        playerNotValidTeam.setSport("Hockey");
        playerNotValidTeam.setTeam(teamNotValid);
    }

    @Test
    public void testGetAllPlayers() {
        // On a déjà 7 players dans la base de données
        List<Player> players = this.playerService.getAllPlayers();
        assertEquals(7, players.size());
    }


   @Test
    public void testGetPlayerById() {
        Player foundPlayer = this.playerService.getPlayerById(1L);
        assertEquals("Lionel", foundPlayer.getFirstName());
        assertEquals("Messi", foundPlayer.getLastName());
    }

   @Test
    public void testCreatePlayer() {

        // La création d'un joueur diffère en fonction de s'il à une équipe ou non, on test donc les 3 cas :
        // Une équipe valide (Le joueur est crée
        // Une équipe non valide (Le joueur n'est pas crée une exception est levée)
        // Pas d'équipe (Le joueur est crée sans équipe)

       playerValidTeam = this.playerService.createPlayer(playerValidTeam);
       playerNullTeam = this.playerService.createPlayer(playerNullTeam);
       assertThrows(TeamNotFoundException.class, () -> this.playerService.createPlayer(playerNotValidTeam));
       assertNull(playerNullTeam.getTeam());
       assertEquals(this.teamService.getTeamById(1L).getId(), playerValidTeam.getTeam().getId());

       List<Player> players = this.playerService.getAllPlayers();

       assertEquals(9, players.size());

       this.playerService.deletePlayer(playerValidTeam.getId());
       this.playerService.deletePlayer(playerNullTeam.getId());
    }

    @Test
    public void testUpdatePlayer() {
        // De la même manière que pour la création d'un joueur, la mise à jour d'un joueur diffère en fonction de s'il à une équipe ou non, on test donc les 3 cas :
        Player playerTest = new Player();
        playerTest.setFirstName("Test");
        playerTest.setLastName("Testeur");
        playerTest.setNationality("French");
        playerTest.setAge(25);
        playerTest.setHeight(180.0f);
        playerTest.setWeight(75.0f);
        playerTest.setSport("Hockey");
        playerTest.setTeam(null);

        playerValidTeam = this.playerService.createPlayer(playerValidTeam);
        playerNullTeam = this.playerService.createPlayer(playerNullTeam);

        playerValidTeam = this.playerService.updatePlayer(playerValidTeam.getId(), playerTest);
        playerNullTeam = this.playerService.updatePlayer(playerNullTeam.getId(), playerTest);

        assertNull(playerValidTeam.getTeam());
        assertNull(playerNullTeam.getTeam());

        assertThrows(TeamNotFoundException.class, () -> this.playerService.updatePlayer(playerValidTeam.getId(), playerNotValidTeam));
        assertThrows(TeamNotFoundException.class, () -> this.playerService.updatePlayer(playerNullTeam.getId(), playerNotValidTeam));

        playerTest.setTeam(this.teamService.getTeamById(1L));
        playerValidTeam = this.playerService.updatePlayer(playerValidTeam.getId(), playerTest);

        assertEquals(this.teamService.getTeamById(1L).getId(), playerValidTeam.getTeam().getId());

        playerTest.setTeam(this.teamService.getTeamById(2L));
        playerValidTeam = this.playerService.updatePlayer(playerValidTeam.getId(), playerTest);

        assertEquals(this.teamService.getTeamById(2L).getId(), playerValidTeam.getTeam().getId());

        List<Player> players = this.playerService.getAllPlayers();
        assertEquals(9, players.size());

        this.playerService.deletePlayer(playerValidTeam.getId());
        this.playerService.deletePlayer(playerNullTeam.getId());
    }

    @Test
    public void testDeletePlayer() {
        Player player = new Player();
        player.setFirstName("Test");
        player.setLastName("Testeur");

        player = this.playerService.createPlayer(player);

        this.playerService.deletePlayer(player.getId());
        List<Player> players = this.playerService.getAllPlayers();
        assertEquals(7, players.size());
    }
}
