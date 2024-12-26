package azak.appdistrib.app.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import azak.appdistrib.app.Domain.Player;
import java.util.List;

public interface PlayerService {

    public List<Player> getAllPlayers();

    public Player getPlayerById(Long id);

    public Player createPlayer(Player player);

    public Player updatePlayer(Long id,Player player);

    public void deletePlayer(Long id);

    public Player savePlayer(Player player);
}
