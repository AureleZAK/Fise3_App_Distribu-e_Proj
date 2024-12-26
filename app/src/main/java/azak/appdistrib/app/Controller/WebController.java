package azak.appdistrib.app.Controller;

import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Service.PlayerService;
import azak.appdistrib.app.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamService teamService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Mapping pour les requetes Web
    @GetMapping("/playersview")
    public String getPlayersView(Model model) {
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("playerslist", players);
        return "players-list";
    }

    // Mapping pour les requetes Web
    @GetMapping("/teamsview")
    public String getTeamsView(Model model) {
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teamslist", teams);
        return "teams-list";
    }
}
