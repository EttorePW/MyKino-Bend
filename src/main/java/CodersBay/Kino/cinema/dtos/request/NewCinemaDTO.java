package CodersBay.Kino.cinema.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewCinemaDTO {

    private String name;
    private String address;
    private String manager;
    private int maxHalls;

}
