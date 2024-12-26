package azak.appdistrib.app.Dao;

import azak.appdistrib.app.Domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsPlayerById(Long id);
}
