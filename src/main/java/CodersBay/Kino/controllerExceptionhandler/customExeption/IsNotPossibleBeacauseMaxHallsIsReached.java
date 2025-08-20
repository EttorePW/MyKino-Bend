package CodersBay.Kino.controllerExceptionhandler.customExeption;

public class IsNotPossibleBeacauseMaxHallsIsReached extends RuntimeException {
    public IsNotPossibleBeacauseMaxHallsIsReached(String message) {
        super(message);
    }
}
