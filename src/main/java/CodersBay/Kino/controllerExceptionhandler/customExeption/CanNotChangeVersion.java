package CodersBay.Kino.controllerExceptionhandler.customExeption;

public class CanNotChangeVersion extends RuntimeException {
    public CanNotChangeVersion(String message) {
        super(message);
    }
}
