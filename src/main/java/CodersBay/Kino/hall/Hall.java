package CodersBay.Kino.hall;

import CodersBay.Kino.enums.MovieVersion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "halls")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hall {

   @Id
   private String hallId;
   private int capacity;
   private int occupiedSeats;
   private MovieVersion supportedMovieVersion;
   private double seatPrice;
   private String cinemaId; // Reference to cinema
   private String cinemaName; // Denormalized for easy access
   private String cinemaAddress; // Denormalized for easy access
   private List<String> screeningTimes = new ArrayList<>();
   private List<String> movieIds = new ArrayList<>(); // Movies playing in this hall


   public Hall(int capacity, int occupiedSeats, MovieVersion supportedMovieVersion, double seatPrice, String cinemaId, String cinemaName, String cinemaAddress, List<String> screeningTimes) {
      this.capacity = capacity;
      this.occupiedSeats = occupiedSeats;
      this.supportedMovieVersion = supportedMovieVersion;
      this.seatPrice = seatPrice;
      this.cinemaId = cinemaId;
      this.cinemaName = cinemaName;
      this.cinemaAddress = cinemaAddress;
      this.screeningTimes = screeningTimes != null ? screeningTimes : new ArrayList<>();
      this.movieIds = new ArrayList<>();
   }

   private void initializeLists() {
      if (this.screeningTimes == null) {
         this.screeningTimes = new ArrayList<>();
      }
      if (this.movieIds == null) {
         this.movieIds = new ArrayList<>();
      }
   }

   public List<String> getMovieIds() {
      if (this.movieIds == null) {
         this.movieIds = new ArrayList<>();
      }
      return this.movieIds;
   }

   public List<String> getScreeningTimes() {
      if (this.screeningTimes == null) {
         this.screeningTimes = new ArrayList<>();
      }
      return this.screeningTimes;
   }
}
