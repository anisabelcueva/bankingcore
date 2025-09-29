package com.banking.core.customerms.service.impl;

import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import com.banking.core.customerms.repository.CustomerRepository;
import com.banking.core.customerms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public Flux<CustomerResponse> getAllCustomers() {
        return repository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No customers found")))
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found!")))
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<String> createCustomer(CustomerRequest request) {
        Customer customer = mapToEntity(request);
        return repository.save(customer)
                .map(c -> "Customer created successfully!")
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    @Override
    public Mono<String> updateCustomer(Long id, CustomerRequest request) {
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
    public Mono<Void> deleteCustomer(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")))
                .flatMap(repository::delete)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unexpected error", ex));
    }

    // 🔹 Helpers: convert between Request/Entity/Response
    private Customer mapToEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDni(request.getDni());
        customer.setEmail(request.getEmail());
        return customer;
    }

    private CustomerResponse mapToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setDni(customer.getDni());
        response.setEmail(customer.getEmail());
        return response;
    }

}
