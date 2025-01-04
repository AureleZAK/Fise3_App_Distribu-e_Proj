package azak.appdistrib.app.Exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Team ID not found");
    }
}
