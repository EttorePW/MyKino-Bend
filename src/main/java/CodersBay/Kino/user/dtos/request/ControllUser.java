package CodersBay.Kino.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ControllUser {
    private String userEmail;
    private String password;
}
