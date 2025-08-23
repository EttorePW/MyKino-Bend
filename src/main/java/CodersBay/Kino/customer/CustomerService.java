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
        try {
            System.out.println("Creating customer: " + newCustomerDTO.getFirstName() + " " + newCustomerDTO.getLastName());
            System.out.println("Customer seats count: " + (newCustomerDTO.getSeats() != null ? newCustomerDTO.getSeats().size() : 0));
            
            Customer customer = Customer.builder()
                    .firstName(newCustomerDTO.getFirstName())
                    .lastName(newCustomerDTO.getLastName())
                    .email(newCustomerDTO.getEmail())
                    .phone(newCustomerDTO.getPhone())
                    .address(newCustomerDTO.getAddress())
                    .anAdult(newCustomerDTO.isAnAdult())
                    .build();

            customerRepository.save(customer);
            System.out.println("Customer saved with ID: " + customer.getCustomerId());

            if (newCustomerDTO.getSeats() != null && !newCustomerDTO.getSeats().isEmpty()) {
                List<Seat> seats = seatService.createNewSeatsList(newCustomerDTO.getSeats(), customer);
                System.out.println("Created " + seats.size() + " seats for customer");
                
                // Store seat IDs in customer for MongoDB
                List<String> seatIds = seats.stream().map(Seat::getSeatId).toList();
                customer.setSeatIds(seatIds);

                customerRepository.save(customer);
                System.out.println("Customer updated with seat IDs: " + seatIds);
            } else {
                System.out.println("No seats provided for customer");
            }
            
            return customer;
        } catch (Exception e) {
            System.err.println("Error creating customer: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create customer: " + e.getMessage(), e);
        }
    }

    public RespCustomerDTO convertToDTO(Customer customer) {
        List<Seat> seats = getCustomerSeats(customer.getCustomerId());
        return RespCustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .anAdult(customer.isAnAdult())
                .seats(seatService.convertListToDTO(seats))
                .build();
    }

    public RespBillDTO convertBillToBillDTO(Bill bill) {
        Customer customer = findCustomerById(bill.getCustomerId());
        return RespBillDTO.builder()
                .billId(bill.getBillId())
                .customerName(bill.getCustomerName())
                .totalPrice(bill.getTotalPrice())
                .billDate(bill.getBillDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .customer(convertToDTO(customer))
                .build();
    }

    public List<RespCustomerDTO> getAll() {
        List<Customer> customers = customerRepository.findAll();
        List<RespCustomerDTO> respCustomerDTOList = new ArrayList<>();
        customers.forEach(customer -> respCustomerDTOList.add(convertToDTO(customer)));
        return respCustomerDTOList;
    }

    public RespCustomerDTO findById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer could not be found", "/api/customers/" + id));
        return convertToDTO(customer);
    }
    
    public Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer could not be found", "/api/customers/" + id));
    }
    
    // Method to get seats by customer ID
    public List<Seat> getCustomerSeats(String customerId) {
        Customer customer = findCustomerById(customerId);
        if (customer.getSeatIds() == null || customer.getSeatIds().isEmpty()) {
            return new ArrayList<>();
        }
        return seatRepository.findAllById(customer.getSeatIds());
    }

    public String deleteById(String id) {
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }
}