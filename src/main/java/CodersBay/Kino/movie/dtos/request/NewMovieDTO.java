package CodersBay.Kino.movie.dtos.request;

import CodersBay.Kino.enums.MovieVersion;
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
public class NewMovieDTO {

    private String title;
    private String mainCharacter;
    private String description;
    private String premieredAt;
    private MovieVersion movieVersion;
    private List<String> halls;
    private String image;
    private String imageBkd;
    private String videoId;

}
