package CodersBay.Kino.movie.dtos.response;

import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.hall.Hall;
import CodersBay.Kino.hall.dtos.response.RespHallDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RespMovieDTO {

    private String movieId;
    private String title;
    private String mainCharacter;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate premieredAt;
    private MovieVersion movieVersion;
    private List<RespHallDTO> halls;
    private String image;
    private String imageBkd;
    private String videoId;
}

