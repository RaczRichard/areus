package areus.service;

import areus.model.dto.CustomerDTO;
import areus.model.entity.CustomerEntity;
import areus.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(customerService, "minimumAge", 18);
        ReflectionTestUtils.setField(customerService, "maximumAge", 41);
    }

    @Test
    public void testGetAvgAge() {
        when(customerRepository.findAverageAge()).thenReturn(34.6);

        Double avgAge = customerService.getAvgAge();

        assertEquals(34.6, avgAge);
    }

    @Test
    void testGetCustomersBetween18And40() {
        var customer1 = new CustomerEntity();
        customer1.setId(1L);
        customer1.setFirstName("Ricsi");
        customer1.setLastName("Rácz");
        customer1.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customer2 = new CustomerEntity();
        customer2.setId(2L);
        customer2.setFirstName("Kitti");
        customer2.setLastName("Kelemen");
        customer2.setDateOfBirth(LocalDate.of(1992, 2, 2));

        var customer3 = new CustomerEntity();
        customer3.setId(3L);
        customer3.setFirstName("Kata");
        customer3.setLastName("Cserna");
        customer3.setDateOfBirth(LocalDate.of(1984, 3, 3));

        var customer4 = new CustomerEntity();
        customer4.setId(4L);
        customer4.setFirstName("Dora");
        customer4.setLastName("Varhegyi");
        customer4.setDateOfBirth(LocalDate.of(1984, 5, 4));

        var customer5 = new CustomerEntity();
        customer5.setId(5L);
        customer5.setFirstName("László");
        customer5.setLastName("Kovács");
        customer5.setDateOfBirth(LocalDate.of(1980, 5, 5));

        var customerDTO1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstName("Ricsi");
        customer1.setLastName("Rácz");
        customer1.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customerDTO2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstName("Kitti");
        customer2.setLastName("Kelemen");
        customer2.setDateOfBirth(LocalDate.of(1992, 2, 2));

        var customerDTO3 = new CustomerDTO();
        customer3.setId(3L);
        customer3.setFirstName("Kata");
        customer3.setLastName("Cserna");
        customer3.setDateOfBirth(LocalDate.of(1984, 3, 3));

        var customerDTO4 = new CustomerDTO();
        customer4.setId(4L);
        customer4.setFirstName("Dora");
        customer4.setLastName("Varhegyi");
        customer4.setDateOfBirth(LocalDate.of(1984, 5, 4));

        var customerDTO5 = new CustomerDTO();
        customer5.setId(5L);
        customer5.setFirstName("László");
        customer5.setLastName("Kovács");
        customer5.setDateOfBirth(LocalDate.of(1980, 5, 5));

        var allCustomers = List.of(customer1, customer2, customer3, customer4, customer5);

        when(customerRepository.findAll()).thenReturn(allCustomers);
        when(modelMapper.map(eq(customer1), eq(CustomerDTO.class))).thenReturn(customerDTO1);
        when(modelMapper.map(eq(customer2), eq(CustomerDTO.class))).thenReturn(customerDTO2);
        when(modelMapper.map(eq(customer4), eq(CustomerDTO.class))).thenReturn(customerDTO4);

        var result = customerService.getCustomersBetween18And40();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(customerDTO1));
        assertTrue(result.contains(customerDTO2));
        assertTrue(result.contains(customerDTO4));
        assertFalse(result.contains(customerDTO3));
        assertFalse(result.contains(customerDTO5));
    }

    @Test
    void testCreateCustomer_Success() {
        var customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstName("Ricsi");
        customerEntity.setLastName("Rácz");
        customerEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customer = new CustomerDTO();
        customerEntity.setId(1L);
        customerEntity.setFirstName("Ricsi");
        customerEntity.setLastName("Rácz");
        customerEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(modelMapper.map(any(), eq(CustomerEntity.class))).thenReturn(customerEntity);

        boolean result = customerService.createCustomer(customer);

        assertTrue(result);
    }

    @Test
    void testCreateCustomer_Failure() {
        var customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstName("Ricsi");
        customerEntity.setLastName("Rácz");
        customerEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customer = new CustomerDTO();
        customerEntity.setId(1L);
        customerEntity.setFirstName("Ricsi");
        customerEntity.setLastName("Rácz");
        customerEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(customerRepository.save(customerEntity)).thenThrow(new RuntimeException("Database error"));

        boolean result = customerService.createCustomer(customer);

        assertFalse(result);
    }

    @Test
    void testGetAllCustomers() {
        var customer1 = new CustomerEntity();
        customer1.setId(1L);
        customer1.setFirstName("Ricsi");
        customer1.setLastName("Rácz");
        customer1.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customer2 = new CustomerEntity();
        customer2.setId(2L);
        customer2.setFirstName("Kata");
        customer2.setLastName("Cserna");
        customer2.setDateOfBirth(LocalDate.of(1992, 3, 3));

        var customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("Ricsi");
        customerDTO.setLastName("Rácz");
        customerDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTO2.setFirstName("Kata");
        customerDTO2.setLastName("Cserna");
        customerDTO2.setDateOfBirth(LocalDate.of(1992, 3, 3));

        List<CustomerEntity> customers = List.of(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);
        when(modelMapper.map(eq(customer1), eq(CustomerDTO.class))).thenReturn(customerDTO);
        when(modelMapper.map(eq(customer2), eq(CustomerDTO.class))).thenReturn(customerDTO2);

        var result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        assertTrue(result.contains(customerDTO));
        assertTrue(result.contains(customerDTO2));
    }

    @Test
    void testGetCustomerById() {
        Long id = 1L;
        var customer = new CustomerEntity();
        customer.setId(id);
        customer.setFirstName("Ricsi");
        customer.setLastName("Rácz");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setFirstName("Ricsi");
        customerDTO.setLastName("Rácz");
        customerDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(modelMapper.map(any(), eq(CustomerDTO.class))).thenReturn(customerDTO);

        var result = customerService.getCustomerById(id);

        assertEquals("Ricsi", result.getFirstName());
        assertEquals("Rácz", result.getLastName());
        assertEquals(id, result.getId());
    }

    @Test
    void testGetCustomerByIdNotFound() {
        Long nonExistentId = 999L;
        when(customerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomerById(nonExistentId);
        });

        assertEquals("There is no such costumer!", ex.getMessage());
    }

    @Test
    void testUpdateCustomerSuccess() {
        Long id = 1L;
        var existingCustomerEntity = new CustomerEntity();
        existingCustomerEntity.setId(id);
        existingCustomerEntity.setFirstName("Ricsi");
        existingCustomerEntity.setLastName("Rácz");
        existingCustomerEntity.setDateOfBirth(LocalDate.of(1990, 1, 1));

        var updatedCustomerEntity = new CustomerEntity();
        updatedCustomerEntity.setFirstName("Kata");
        updatedCustomerEntity.setLastName("Cserna");
        updatedCustomerEntity.setDateOfBirth(LocalDate.of(1992, 3, 3));

        var updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO.setFirstName("Kata");
        updatedCustomerDTO.setLastName("Cserna");
        updatedCustomerDTO.setDateOfBirth(LocalDate.of(1992, 3, 3));

        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomerEntity));
        when(modelMapper.map(any(), eq(CustomerEntity.class))).thenReturn(updatedCustomerEntity);

        boolean result = customerService.updateCustomer(id, updatedCustomerDTO);

        assertTrue(result);
        verify(customerRepository).save(updatedCustomerEntity);
    }

    @Test
    void testUpdateCustomerNotFound() {
        Long id = 1L;
        var updatedCustomerEntity = new CustomerEntity();
        updatedCustomerEntity.setFirstName("Kata");
        updatedCustomerEntity.setLastName("Cserna");
        updatedCustomerEntity.setDateOfBirth(LocalDate.of(1992, 3, 3));
        var updatedCustomer = new CustomerDTO();
        updatedCustomer.setFirstName("Kata");
        updatedCustomer.setLastName("Cserna");
        updatedCustomer.setDateOfBirth(LocalDate.of(1992, 3, 3));

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = customerService.updateCustomer(id, updatedCustomer);

        assertFalse(result);
        verify(customerRepository, times(0)).save(updatedCustomerEntity);
    }

    @Test
    void testDeleteCustomerSuccess() {
        Long id = 1L;

        when(customerRepository.existsById(id)).thenReturn(true);

        boolean result = customerService.deleteCustomer(id);

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCustomerNotFound() {
        Long id = 1L;

        when(customerRepository.existsById(id)).thenReturn(false);

        boolean result = customerService.deleteCustomer(id);

        assertFalse(result);
        verify(customerRepository, times(0)).deleteById(id);
    }
}