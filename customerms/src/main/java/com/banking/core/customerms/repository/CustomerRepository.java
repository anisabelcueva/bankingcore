package com.banking.core.customerms.repository;
import com.banking.core.customerms.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, Long> {

    Mono<Customer> findByDni(String dni);
    Mono<Customer> findByEmail(String email);
}
