package azak.appdistrib.app.Exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long id) {
        super("Team with ID " + id + " not found");
    }
}
