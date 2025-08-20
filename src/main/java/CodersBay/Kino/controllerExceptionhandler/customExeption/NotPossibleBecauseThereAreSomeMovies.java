package CodersBay.Kino.controllerExceptionhandler.customExeption;

public class NotPossibleBecauseThereAreSomeMovies extends RuntimeException {
    public NotPossibleBecauseThereAreSomeMovies(String message) {
        super(message);
    }
}
