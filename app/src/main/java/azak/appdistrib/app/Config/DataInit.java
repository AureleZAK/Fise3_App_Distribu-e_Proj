package azak.appdistrib.app.Config;

import azak.appdistrib.app.Domain.Player;
import azak.appdistrib.app.Domain.Team;
import azak.appdistrib.app.Service.PlayerService;
import azak.appdistrib.app.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Override
    public void run(String... args) throws Exception {

        Team barcelona = new Team();
        barcelona.setName("FC Barcelona");
        barcelona.setSport("Football");
        barcelona.setCity("Barcelona");
        barcelona.setStadium("Camp Nou");
        barcelona.setCoach("Xavi Hernandez");

        Team goldenState = new Team();
        goldenState.setName("Golden State Warriors");
        goldenState.setSport("Basketball");
        goldenState.setCity("San Francisco");
        goldenState.setStadium("Chase Center");
        goldenState.setCoach("Steve Kerr");

        Team manchesterUnited = new Team();
        manchesterUnited.setName("Manchester United FC");
        manchesterUnited.setSport("Football");
        manchesterUnited.setCity("Manchester");
        manchesterUnited.setStadium("Old Trafford");
        manchesterUnited.setCoach("Erik ten Hag");

        Team psg_foot = new Team();
        psg_foot.setName("Paris Saint-Germain");
        psg_foot.setSport("Football");
        psg_foot.setCity("Paris");
        psg_foot.setStadium("Parc des Princes");
        psg_foot.setCoach("Luis Enrique");

        Team psg_handball = new Team();
        psg_handball.setName("Paris Saint-Germain");
        psg_handball.setSport("Handball");
        psg_handball.setCity("Paris");
        psg_handball.setStadium("Stade Pierre de Coubertin");
        psg_handball.setCoach("Stefan Madsen");

        teamService.saveTeam(barcelona);
        teamService.saveTeam(goldenState);
        teamService.saveTeam(manchesterUnited);
        teamService.saveTeam(psg_foot);
        teamService.saveTeam(psg_handball);

        Player messi = new Player();
        messi.setFirstName("Lionel");
        messi.setLastName("Messi");
        messi.setNationality("Argentine");
        messi.setAge("36");
        messi.setHeight("1.70m");
        messi.setWeight("72kg");
        messi.setSport("Football");
        messi.setNumber("10");
        messi.setTeam(barcelona);

        Player busquets = new Player();
        busquets.setFirstName("Sergio");
        busquets.setLastName("Busquets");
        busquets.setNationality("Espagnol");
        busquets.setAge("35");
        busquets.setHeight("1.89m");
        busquets.setWeight("82kg");
        busquets.setSport("Football");
        busquets.setNumber("5");
        busquets.setTeam(barcelona);

        Player ibra = new Player();
        ibra.setFirstName("Zlatan");
        ibra.setLastName("Ibrahimovic");
        ibra.setNationality("Suédois");
        ibra.setAge("43");
        ibra.setHeight("1.95m");
        ibra.setWeight("95kg");
        ibra.setSport("Football");
        ibra.setNumber("10");
        ibra.setTeam(null);

        Player curry = new Player();
        curry.setFirstName("Stephen");
        curry.setLastName("Curry");
        curry.setNationality("Américain");
        curry.setAge("36");
        curry.setHeight("1.91m");
        curry.setWeight("86kg");
        curry.setSport("Basketball");
        curry.setNumber("30");
        curry.setTeam(goldenState);

        Player thompson = new Player();
        thompson.setFirstName("Klay");
        thompson.setLastName("Thompson");
        thompson.setNationality("Américain");
        thompson.setAge("34");
        thompson.setHeight("2.01m");
        thompson.setWeight("98kg");
        thompson.setSport("Basketball");
        thompson.setNumber("11");
        thompson.setTeam(goldenState);

        Player neymar = new Player();
        neymar.setFirstName("Neymar");
        neymar.setLastName("Jr.");
        neymar.setNationality("Brésilien");
        neymar.setAge("31");
        neymar.setHeight("1.75m");
        neymar.setWeight("68kg");
        neymar.setSport("Football");
        neymar.setNumber("11");
        neymar.setTeam(psg_foot);

        Player mbappe = new Player();
        mbappe.setFirstName("Kylian");
        mbappe.setLastName("Mbappé");
        mbappe.setNationality("Français");
        mbappe.setAge("25");
        mbappe.setHeight("1.78m");
        mbappe.setWeight("73kg");
        mbappe.setSport("Football");
        mbappe.setNumber("7");
        mbappe.setTeam(psg_foot);

        playerService.savePlayer(messi);
        playerService.savePlayer(busquets);
        playerService.savePlayer(ibra);
        playerService.savePlayer(curry);
        playerService.savePlayer(thompson);
        playerService.savePlayer(neymar);
        playerService.savePlayer(mbappe);
    }
}
