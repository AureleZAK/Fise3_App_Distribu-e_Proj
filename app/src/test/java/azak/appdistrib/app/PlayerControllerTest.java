package azak.appdistrib.app;

import azak.appdistrib.app.Dao.PlayerRepository;
import azak.appdistrib.app.Domain.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testGetAllPlayers() throws Exception {
        this.mockMvc.
                perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(7)));
    }

    @Test
    public void testGetPlayerById() throws Exception {
        this.mockMvc
                .perform(get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Lionel")))
                .andExpect(jsonPath("$.lastName", is("Messi")));
    }

    @Test
    public void testCreatePlayer() throws Exception {

        List<Player> players = this.playerRepository.findAll();
        assertEquals(7, players.size());

        Player player = new Player();
        player.setFirstName("Test");
        player.setLastName("Testeur");

        ObjectMapper mapper = new ObjectMapper();
        byte[] playerbyte = mapper.writeValueAsBytes(player);

        this.mockMvc
                .perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerbyte))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.lastName", is("Testeur")));


        players = this.playerRepository.findAll();
        assertEquals(8, players.size());
        assertEquals("Test", players.get(7).getFirstName());

        // Attention ici suivant l'ordre des tests le player peut être à l'id 8 ou 9
        player = this.playerRepository.findById(8L).orElse(null);
        this.playerRepository.delete(player);

        assertEquals(7, this.playerRepository.findAll().size());
    }

    @Test
    public void testModifyPlayer() throws Exception {
        Player player = new Player();
        player.setFirstName("John");
        player.setLastName("Travolta");

        player = this.playerRepository.save(player);

        List<Player> players = this.playerRepository.findAll();

        assertEquals(8, players.size());
        assertEquals("John", players.get(7).getFirstName());

        Player testPlayer = new Player();
        testPlayer.setFirstName("Test");
        testPlayer.setLastName("Testeur");


        ObjectMapper mapper = new ObjectMapper();
        byte[] playerbyte = mapper.writeValueAsBytes(testPlayer);

        this.mockMvc
                .perform(put("/players/{id}", player.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerbyte))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.lastName", is("Testeur")));

        players = this.playerRepository.findAll();
        assertEquals("Test", players.get(7).getFirstName());

        this.playerRepository.delete(player);
    }

    @Test
    public void testDeletePlayer() throws Exception {
        Player player = new Player();
        player.setFirstName("Test");
        player.setLastName("Testeur");
        player = this.playerRepository.save(player);

        List<Player> players = this.playerRepository.findAll();
        assertEquals(8, players.size());

        this.mockMvc
                .perform(post("/players/{id}", player.getId()))
                .andExpect(status().isOk());

        players = this.playerRepository.findAll();
        assertEquals(7, players.size());
    }
}
