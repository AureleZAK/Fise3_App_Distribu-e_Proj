package azak.appdistrib.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import azak.appdistrib.app.Domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
