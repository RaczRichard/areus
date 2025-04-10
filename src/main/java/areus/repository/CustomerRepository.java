package areus.repository;

import areus.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE())) FROM customer", nativeQuery = true)
    Double findAverageAge();
}
