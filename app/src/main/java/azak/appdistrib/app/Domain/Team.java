package azak.appdistrib.app.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
    private List<Player> players;

    private String name;

    private String sport;

    private String stadium;

    private String city;

    private String coach;

    public Team() {
        this.players = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players.forEach(player -> player.setTeam(null));
        this.players = players;
        players.forEach(player -> player.setTeam(this));
    }

    public void addPlayers(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }

    public void removePlayers(Player player) {
        this.players.remove(player);
        player.setTeam(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}
