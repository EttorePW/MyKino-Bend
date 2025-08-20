package CodersBay.Kino.cinema.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RespCinemaDTO {
    private Long cinemaId;
    private String name;
    private String address;
    private String manager;
    private int maxHalls;

}