package CodersBay.Kino.pk;

import java.io.Serializable;
import java.util.Objects;

public class Movie_plays_in_PK implements Serializable {

    private Long hall;
    private Long movie;

    public Movie_plays_in_PK() {}

    public Movie_plays_in_PK(Long hall, Long movie) {
        this.hall = hall;
        this.movie = movie;
    }

    public Long getHall() {
        return hall;
    }

    public void setHall(Long hall) {
        this.hall = hall;
    }

    public Long getMovie() {
        return movie;
    }

    public void setMovie(Long movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie_plays_in_PK that = (Movie_plays_in_PK) o;
        return Objects.equals(hall, that.hall) && Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hall, movie);
    }
}
