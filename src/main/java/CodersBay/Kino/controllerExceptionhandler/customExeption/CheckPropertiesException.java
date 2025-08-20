package CodersBay.Kino.controllerExceptionhandler.customExeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CheckPropertiesException extends RuntimeException {
    public CheckPropertiesException(String message) {
        super(message);
    }
}
