package CodersBay.Kino.controllerExceptionhandler.customExeption;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {

    private String url;
    public NotFoundException(String message, String url) {
        super(message);
        this.url = url;
    }
}
