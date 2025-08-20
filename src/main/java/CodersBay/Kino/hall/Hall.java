package CodersBay.Kino.hall;

import CodersBay.Kino.cinema.Cinema;
import CodersBay.Kino.enums.MovieVersion;
import CodersBay.Kino.movie.Movie;
import CodersBay.Kino.pk.Movie_plays_in;
import CodersBay.Kino.seat.Seat;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hall {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long hallId;
   private int capacity;
   private int occupiedSeats;
   @Enumerated(EnumType.STRING)
   private MovieVersion supportedMovieVersion;
   private double seatPrice;
   @ManyToOne
   @JoinColumn(name = "cinemaId")
   private Cinema cinema;
   private List<String> screeningTimes = new ArrayList<>();
   @OneToMany(mappedBy = "hall",cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Movie_plays_in> moviePlaysInList = new ArrayList<>();


   public Hall(int capacity, int occupiedSeats, MovieVersion supportedMovieVersion,double seatPrice, Cinema cinema, List<String> screeningTimes) {
      this.capacity = capacity;
      this.occupiedSeats = occupiedSeats;
      this.supportedMovieVersion = supportedMovieVersion;
      this.seatPrice = seatPrice;
      this.cinema = cinema;
      this.screeningTimes = screeningTimes;
   }
}
