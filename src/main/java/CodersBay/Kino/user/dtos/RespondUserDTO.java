package CodersBay.Kino.user.dtos;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespondUserDTO {

    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;

}
