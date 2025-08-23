package CodersBay.Kino.bills;

import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.customer.Customer;
import CodersBay.Kino.customer.CustomerService;
import CodersBay.Kino.seat.Seat;
import CodersBay.Kino.services.EmailSendingService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final CustomerService customerService;
    private final EmailSendingService emailSendingService;

    public RespBillDTO createNewBill(NewBillDTO newBillDTO) throws MessagingException {
        Customer customer = customerService.createCustomer(newBillDTO.getCustomer());

        Bill bill = Bill.builder()
                .customerId(customer.getCustomerId())
                .customerName(newBillDTO.getCustomerName())
                .totalPrice(newBillDTO.getTotalPrice())
                .billDate(LocalDate.parse(newBillDTO.getBillDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .build();

        billRepository.save(bill);

        // Enviar email de confirmación
        String emailText = respondTextGenerator(bill);
        emailSendingService.sendEmail(
                customer.getEmail(),
                "Reservierungsbestätigung Kino.at",
                emailText
        );

        return convertBillToBillDTO(bill);
    }

    public String respondTextGenerator(Bill bill) {
        // Get customer and seats using the customerId reference
        Customer customer = customerService.findCustomerById(bill.getCustomerId());
        List<Seat> seats = customerService.getCustomerSeats(customer.getCustomerId());

        String seatsList = seats.stream()
                .sorted(Comparator.comparing(Seat::getRowNumber).thenComparing(Seat::getColNumber))
                .collect(Collectors.groupingBy(
                        Seat::getRowNumber,
                        LinkedHashMap::new,
                        Collectors.mapping(Seat::getColNumber, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> {
                    String rowLetter = String.valueOf((char) ('A' + (entry.getKey() - 1)));
                    String cols = entry.getValue().stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));
                    return String.format("Reihe %s: Sitz %s", rowLetter, cols);
                })
                .collect(Collectors.joining(" | "));

        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 8px;">
                <div style="text-align: center;">
                    <img src="%s" alt="Logo Cine" width="80"/>
                    <h2 style="color: #333;">Reservierungsbestätigung</h2>
                    <p>Danke für deine Reservierung <strong>Bei KINO</strong></p>
                </div>
                <hr/>
                <p><strong>Kunde:</strong> %s</p>
                <p><strong>Film:</strong> %s</p>
                <p><strong>Version:</strong> %s</p>
                <p><strong>Kino:</strong> %s</p>
                <p><strong>Saal Nr:</strong> %d</p>
                <p><strong>Sitze:</strong> %s</p>
                <p><strong>Gesamtbetrag:</strong> %.2f €</p>
                <p><strong>Datum der Reservation:</strong> %s</p>
                <hr/>
                <p style="font-size: 14px; color: gray;">Bitte, zeig diese Email bei deiner Ankunft.</p>
            </div>
        </body>
        </html>
        """.formatted(
                "https://plus.unsplash.com/premium_photo-1710522706751-c2f0c76cc5fd?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                bill.getCustomerName(),
                seats.isEmpty() ? "N/A" : seats.get(0).getMovieName(),
                seats.isEmpty() ? "N/A" : seats.get(0).getMovieVersion(),
                seats.isEmpty() ? "N/A" : seats.get(0).getCinemaName(),
                seats.isEmpty() ? "N/A" : seats.get(0).getHallId(),
                seatsList.isEmpty() ? "N/A" : seatsList,
                bill.getTotalPrice(),
                bill.getBillDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );
    }

    public RespBillDTO convertBillToBillDTO(Bill bill) {
        Customer customer = customerService.findCustomerById(bill.getCustomerId());
        return RespBillDTO.builder()
                .billId(bill.getBillId())
                .customerName(bill.getCustomerName())
                .totalPrice(bill.getTotalPrice())
                .billDate(bill.getBillDate().toString())
                .customer(customerService.convertToDTO(customer))
                .build();
    }

    public Bill getBillById(String id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The Bill could not be found!", "/api/bill/" + id));
    }

    public List<RespBillDTO> getAll() {
        return billRepository.findAll()
                .stream()
                .map(this::convertBillToBillDTO)
                .toList();
    }
}
