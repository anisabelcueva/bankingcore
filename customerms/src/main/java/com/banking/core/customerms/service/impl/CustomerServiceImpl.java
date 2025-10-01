package com.banking.core.customerms.service.impl;

import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import com.banking.core.customerms.repository.CustomerRepository;
import com.banking.core.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public Flux<CustomerResponse> getAllCustomers() {
        return repository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No customers found")))
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(BigInteger id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found!")))
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<String> createCustomer(CustomerRequest request) {
        Customer customer = mapToEntity(request);
        Mono<Customer> CustomerSaved = this.repository.save(customer);
        return CustomerSaved.map(c -> "Customer created successfully!")
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<String> updateCustomer(BigInteger id, CustomerRequest request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found with id: " + id)))
                .flatMap(existing -> {
                    existing.setLastName(request.getLastName());
                    existing.setFirstName(request.getFirstName());
                    existing.setDni(request.getDni());
                    existing.setEmail(request.getEmail());
                    return repository.save(existing);
                })
                .map(c -> "Customer updated successfully!")
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<Void> deleteCustomer(BigInteger id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")))
                .flatMap(repository::delete)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unexpected error", ex));
    }

    // 🔹 Helpers: convert between Request/Entity/Response
    private Customer mapToEntity(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dni(request.getDni())
                .email(request.getEmail())
                .build();
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .build();
    }

}
