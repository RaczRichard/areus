package areus.service;

import areus.model.dto.CustomerDTO;
import areus.model.entity.CustomerEntity;
import areus.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CustomerRepository customerRepository;
    @Value("${areus.minimum-age}")
    private Integer minimumAge;
    @Value("${areus.maximum-age}")
    private Integer maximumAge;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public Double getAvgAge() {
        return customerRepository.findAverageAge();
    }

    public List<CustomerDTO> getCustomersBetween18And40() {
        var today = LocalDate.now();
        return customerRepository.findAll().stream().filter(CustomerDTO -> {
            var birthDate = CustomerDTO.getDateOfBirth();
            var eighteenthBirthday = birthDate.plusYears(minimumAge);
            var thirtyFirstBirthday = birthDate.plusYears(maximumAge);
            return !today.isBefore(eighteenthBirthday) && today.isBefore(thirtyFirstBirthday);
        }).map(customerEntity -> modelMapper.map(customerEntity, CustomerDTO.class)).toList();
    }

    public boolean createCustomer(CustomerDTO customer) {
        try {
            customerRepository.save(modelMapper.map(customer, CustomerEntity.class));
            return true;
        } catch (Exception e) {
            log.error("Exception:", e);
            return false;
        }
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerEntity -> modelMapper.map(customerEntity, CustomerDTO.class)).toList();
    }

    public CustomerDTO getCustomerById(Long id) {
        return modelMapper.map(customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no such costumer!")), CustomerDTO.class);
    }

    public boolean updateCustomer(Long id, CustomerDTO customer) {
        var existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            customer.setId(id);
            try {
                customerRepository.save(modelMapper.map(customer, CustomerEntity.class));
                return true;
            } catch (Exception e) {
                log.error("Exception:", e);
                return false;
            }
        }
        
        return false;
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
