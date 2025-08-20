package CodersBay.Kino.customer;

import CodersBay.Kino.bills.Bill;
import CodersBay.Kino.bills.BillRepository;
import CodersBay.Kino.bills.RespBillDTO;
import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.customer.dtos.Request.NewCustomerDTO;
import CodersBay.Kino.customer.dtos.Respond.RespCustomerDTO;
import CodersBay.Kino.seat.Seat;
import CodersBay.Kino.seat.SeatRepository;
import CodersBay.Kino.seat.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BillRepository billRepository;
    private final SeatService seatService;
    private final SeatRepository seatRepository;

    public Customer createCustomer(NewCustomerDTO newCustomerDTO) {

        Customer customer = Customer.builder()
                .firstName(newCustomerDTO.getFirstName())
                .lastName(newCustomerDTO.getLastName())
                .email(newCustomerDTO.getEmail())
                .phone(newCustomerDTO.getPhone())
                .address(newCustomerDTO.getAddress())
                .anAdult(newCustomerDTO.isAnAdult())
                .build();

        customerRepository.save(customer);


        List<Seat> seats = seatService.createNewSeatsList(newCustomerDTO.getSeats(), customer);
        customer.setSeats(seats);

        customerRepository.save(customer);
        return customer;
    }

    public RespCustomerDTO convertToDTO(Customer customer) {
        return RespCustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .anAdult(customer.isAnAdult())
                .seats(seatService.convertListToDTO(customer.getSeats()))
                .build();
    }

    public RespBillDTO convertBillToBillDTO(Bill bill) {
        return RespBillDTO.builder()
                .billId(bill.getBillId())
                .customerName(bill.getCustomerName())
                .totalPrice(bill.getTotalPrice())
                .billDate(bill.getBillDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .customer(convertToDTO(bill.getCustomer()))
                .build();
    }

    public List<RespCustomerDTO> getAll() {
        List<Customer> customers = customerRepository.findAll();
        List<RespCustomerDTO> respCustomerDTOList = new ArrayList<>();
        customers.forEach(customer -> respCustomerDTOList.add(convertToDTO(customer)));
        return respCustomerDTOList;
    }

    public RespCustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer could not be found", "/api/customers/" + id));
        return convertToDTO(customer);
    }

    public String deleteById(Long id) {
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}