package CodersBay.Kino.controllerExceptionhandler.customExeption;

public class MovieVersionIsNotSupported extends RuntimeException {
    public MovieVersionIsNotSupported(String message) {
        super(message);
    }
}
