package areus.controller;

import areus.model.dto.CustomerDTO;
import areus.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        Double avgAge = customerService.getAvgAge();
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping("/between")
    public List<CustomerDTO> getCustomersBetween18And40() {
        return customerService.getCustomersBetween18And40();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        if (customerService.createCustomer(customerDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer creation failed");
        }
    }

    @GetMapping("/get")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        if (customerService.updateCustomer(id, customerDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerService.deleteCustomer(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
