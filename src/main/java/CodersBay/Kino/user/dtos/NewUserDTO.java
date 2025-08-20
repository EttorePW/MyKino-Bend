package CodersBay.Kino.user.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewUserDTO {

    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String confirmPassword;
}
