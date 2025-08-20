package CodersBay.Kino.controllerExceptionhandler.customExeption;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Data
public class NotFoundException extends EntityNotFoundException {

    private String url;
    public NotFoundException(String message, String url) {
        super(message);
        this.url = url;
    }
}
