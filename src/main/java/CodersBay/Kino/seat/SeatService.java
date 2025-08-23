package CodersBay.Kino.seat;

import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.customer.Customer;
import CodersBay.Kino.seat.dtos.Request.NewSeatDTO;
import CodersBay.Kino.seat.dtos.Respond.RespSeatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat createNewSeat(NewSeatDTO newSeatDTO, Customer customer) {
        Seat seat = Seat.builder()
                .cinemaName(newSeatDTO.getCinemaName())
                .hallId(String.valueOf(newSeatDTO.getHallId()))
                .movieId(String.valueOf(newSeatDTO.getMovieId()))
                .movieVersion(newSeatDTO.getMovieVersion())
                .movieName(newSeatDTO.getMovieName())
                .colNumber(newSeatDTO.getColNumber())
                .rowNumber(newSeatDTO.getRowNumber())
                .reservationDate(newSeatDTO.getReservationDate())
                .reservationTime(newSeatDTO.getReservationTime())
                .premieredAt(parseToDate(newSeatDTO.getPremieredAt()))
                .customerId(customer.getCustomerId())
                .build();

        return seatRepository.save(seat);
    }

    public List<Seat> createNewSeatsList(List<NewSeatDTO> newSeatDTOList, Customer customer) {
        List<Seat> seats = new ArrayList<>();
        newSeatDTOList.forEach(seatDTO -> seats.add(createNewSeat(seatDTO, customer)));
        return seats;
    }

    public LocalDate parseToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + date + ", error: " + e.getMessage());
            // Return current date as fallback
            return LocalDate.now();
        }
    }

    public List<RespSeatDTO> getAllTheSeats() {
        return convertListToDTO(seatRepository.findAll());
    }

    public RespSeatDTO getSeatById(String id) {
        return convertToDTO(seatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seat not found, please enter a correct ID", "/api/seats/" + id)));
    }

    public List<Seat> getAllSeatsById(List<String> ids) {
        return seatRepository.findAllById(ids);
    }

    public RespSeatDTO convertToDTO(Seat seat) {
        return RespSeatDTO.builder()
                .seatId(seat.getSeatId())
                .cinemaName(seat.getCinemaName())
                .hallId(seat.getHallId())
                .movieId(seat.getMovieId())
                .movieVersion(seat.getMovieVersion())
                .movieName(seat.getMovieName())
                .colNumber(seat.getColNumber())
                .rowNumber(seat.getRowNumber())
                .reservationDate(seat.getReservationDate())
                .reservationTime(seat.getReservationTime())
                .premieredAt(seat.getPremieredAt())
                .build();
    }

    public List<RespSeatDTO> convertListToDTO(List<Seat> seats) {
        List<RespSeatDTO> dtos = new ArrayList<>();
        seats.forEach(seat -> dtos.add(convertToDTO(seat)));
        return dtos;
    }

    public String deleteSeatById(String id) {
        seatRepository.deleteById(id);
        return "Seat deleted successfully";
    }
}

