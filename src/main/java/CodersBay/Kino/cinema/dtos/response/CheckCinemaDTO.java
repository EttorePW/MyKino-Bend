package CodersBay.Kino.cinema.dtos.response;

import CodersBay.Kino.hall.dtos.response.RespHallDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckCinemaDTO {
    private Long cinemaId;
    private String name;
    private String address;
    private String manager;
    private int maxHalls;
    private List<RespHallDTO> halls;

}