package com.banking.core.repository;

import com.banking.core.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByDni(String dni);
}
