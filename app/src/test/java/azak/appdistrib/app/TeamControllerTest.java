package azak.appdistrib.app;

import azak.appdistrib.app.Dao.TeamRepository;
import azak.appdistrib.app.Domain.Team;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void testGetAllTeams() throws Exception {
        this.mockMvc.
                perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(5)));
    }

    @Test
    public void testGetTeamById() throws Exception {
        this.mockMvc
                .perform(get("/teams/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("FC Barcelona")));
    }

    @Test
    public void testCreateTeam() throws Exception {

        Team team = new Team();
        team.setName("Test");

        ObjectMapper mapper = new ObjectMapper();
        byte[] teambyte = mapper.writeValueAsBytes(team);

        this.mockMvc
                .perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teambyte))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test")));

        List<Team> teams = this.teamRepository.findAll();
        assertEquals(6, teams.size());
        assertEquals("Test", teams.get(5).getName());

        // Attention ici suivant l'ordre des tests le player peut être à l'id 6 ou 7
        team = this.teamRepository.findById(7L).orElse(null);
        this.teamRepository.delete(team);

        assertEquals(5, this.teamRepository.findAll().size());
    }

    @Test
    public void testModifyTeam() throws Exception {
        Team team = new Team();
        team.setName("Team Rocket");
        team = this.teamRepository.save(team);

        List<Team> teams = this.teamRepository.findAll();

        assertEquals(6, teams.size());
        assertEquals("Team Rocket", teams.get(5).getName());

        Team testTeam = new Team();
        testTeam.setName("Test");

        ObjectMapper mapper = new ObjectMapper();
        byte[] teambytes = mapper.writeValueAsBytes(testTeam);

        this.mockMvc
                .perform(put("/teams/{id}", team.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teambytes))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Test")));

        teams = this.teamRepository.findAll();
        assertEquals("Test", teams.get(5).getName());

        this.teamRepository.delete(team);
        assertEquals(5, this.teamRepository.findAll().size());
    }

    @Test
    public void testDeleteTeam() throws Exception {
        Team team = new Team();
        team.setName("Test");

        team = this.teamRepository.save(team);

        List<Team> teams = this.teamRepository.findAll();
        assertEquals(6, teams.size());

        this.mockMvc
                .perform(post("/teams/{id}", team.getId()))
                .andExpect(status().isOk());

        teams = this.teamRepository.findAll();
        assertEquals(5, teams.size());
    }
}
