package azak.appdistrib.app.Exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long id) {
        super("Player with ID " + id + " not found");
    }
}
