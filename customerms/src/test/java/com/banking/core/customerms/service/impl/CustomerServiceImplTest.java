package com.banking.core.customerms.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import com.banking.core.customerms.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    private Customer customer;
    private CustomerRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Objeto que conversa con la bd mongodb, este es un objeto que es homologo a una tabla de bd
        customer = new Customer(); //entity
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDni("12345678");
        customer.setEmail("jhon@example.com");

        // Objecto del controller para mostrar datos al cliente(postman, angularm reactm ect..)
        request = new CustomerRequest();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setDni("87654321");
        request.setEmail("jane@example.com");
    }

    @Test
    void getAllCustomers_success() {
        when(repository.findAll()).thenReturn(Flux.just(customer));

        StepVerifier.create(service.getAllCustomers())
                .expectNextMatches(resp -> resp.getId().equals(1L) &&
                        resp.getEmail().equals("jhon@example.com"))
                .verifyComplete();
    }

    @Test
    void getAllCustomers_notFound() {
        when(repository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(service.getAllCustomers())
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException &&
                                ((ResponseStatusException) throwable).getStatus().value() == 500)
                .verify();
    }

    @Test
    void getCustomerById_success() {
        when(repository.findById(1L)).thenReturn(Mono.just(customer));

        StepVerifier.create(service.getCustomerById(1L))
                .expectNextMatches(resp -> resp.getDni().equals("12345678"))
                .verifyComplete();
    }

    @Test
    void getCustomerById_notFound() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(service.getCustomerById(99L))
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    void createCustomer_success() {
        when(repository.save(any(Customer.class))).thenReturn(Mono.just(customer));

        StepVerifier.create(service.createCustomer(request))
                .expectNext("Customer created successfully!")
                .verifyComplete();
    }

    @Test
    void updateCustomer_success() {
        when(repository.findById(1L)).thenReturn(Mono.just(customer));
        when(repository.save(any(Customer.class))).thenReturn(Mono.just(customer));

        StepVerifier.create(service.updateCustomer(1L, request))
                .expectNext("Customer updated successfully!")
                .verifyComplete();
    }

    @Test
    void deleteCustomer_success() {
        when(repository.findById(1L)).thenReturn(Mono.just(customer));
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.deleteCustomer(1L))
                .verifyComplete();
    }

    @Test
    void deleteCustomer_notFound() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(service.deleteCustomer(99L))
                .expectError(ResponseStatusException.class)
                .verify();
    }
}