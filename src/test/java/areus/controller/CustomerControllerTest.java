package areus.controller;

import areus.model.dto.CustomerDTO;
import areus.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void testGetAverageAge() {
        Double expectedAverageAge = 32.5;
        when(customerService.getAvgAge()).thenReturn(expectedAverageAge);

        ResponseEntity<Double> response = customerController.getAverageAge();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAverageAge, response.getBody());
    }

    @Test
    void testGetCustomersBetween18And40() {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstName("Ricsi");
        customer.setLastName("Rácz");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        List<CustomerDTO> customers = List.of(customer);

        when(customerService.getCustomersBetween18And40()).thenReturn(customers);

        var response = customerController.getCustomersBetween18And40();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Ricsi", response.get(0).getFirstName());
        assertEquals("Rácz", response.get(0).getLastName());
    }

    @Test
    void testCreateCustomerSuccess() {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(true);

        ResponseEntity<String> response = customerController.createCustomer(new CustomerDTO());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Customer created successfully", response.getBody());
    }

    @Test
    void testCreateCustomerFailure() {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(false);

        ResponseEntity<String> response = customerController.createCustomer(new CustomerDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Customer creation failed", response.getBody());
    }

    @Test
    void testGetAllCustomers() {
        var customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstName("Ricsi");
        customer1.setLastName("Rácz");
        customer1.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstName("Kata");
        customer2.setLastName("Cserna");
        customer2.setDateOfBirth(LocalDate.of(1992, 3, 3));
        List<CustomerDTO> customers = List.of(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        List<CustomerDTO> result = customerController.getAllCustomers();

        assertEquals(2, result.size());
        assertTrue(result.contains(customers.get(0)));
        assertTrue(result.contains(customers.get(1)));
    }

    @Test
    void testGetCustomerById_Found() {
        var customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstName("Ricsi");
        customer.setLastName("Rácz");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testUpdateCustomer_Success() {
        var customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstName("Ricsi");
        customer.setLastName("Rácz");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(customerService.updateCustomer(1L, customer)).thenReturn(true);

        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(1L, customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateCustomer_NotFound() {
        var customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstName("Ricsi");
        customer.setLastName("Rácz");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(customerService.updateCustomer(1L, customer)).thenReturn(false);

        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(1L, customer);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCustomer_Success() {
        Long id = 1L;
        when(customerService.deleteCustomer(id)).thenReturn(true);

        ResponseEntity<Void> response = customerController.deleteCustomer(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCustomer_NotFound() {
        Long id = 1L;
        when(customerService.deleteCustomer(id)).thenReturn(false);

        ResponseEntity<Void> response = customerController.deleteCustomer(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}