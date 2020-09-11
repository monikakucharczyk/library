package monika.library.server;

public class NoUserFoundException extends Exception {
    NoUserFoundException(String message) {
        super(message);
    }
}
