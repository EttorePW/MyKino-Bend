package CodersBay.Kino.controllerExceptionhandler.customExeption;

public class PasswordNotMatch extends RuntimeException {
    public PasswordNotMatch(String message) {
        super(message);
    }
}
