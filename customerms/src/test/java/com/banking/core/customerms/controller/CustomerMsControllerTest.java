package com.banking.core.customerms.controller;

import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import com.banking.core.customerms.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerMsControllerTest {

    @InjectMocks CustomerMsController customerMsController;
    @Mock private CustomerServiceImpl customerServiceImpl;
    private String createdResponse;
    private CustomerResponse customerResponse;
    private BigInteger customerId;
    private String dni;

    @BeforeEach
    void setUp() {
        customerId = BigInteger.valueOf(324543393);

        dni = "52417878";

        createdResponse = "Customer created successfully!";

        customerResponse = CustomerResponse.builder()
                .id(customerId)
                .firstName("Pepe")
                .lastName("Perez")
                .dni(dni)
                .email("pepe@gmail.com")
                .build();
    }

    @Test
    void createCustomer() {
        Mockito.when(this.customerServiceImpl.createCustomer(any(CustomerRequest.class)))
                .thenReturn(Mono.just(createdResponse));

        StepVerifier.create(this.customerMsController.createCustomer(new CustomerRequest()))
                .assertNext(entity -> {
                    Assertions.assertNotNull(entity.getBody());
                    Assertions.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
                    Assertions.assertEquals(createdResponse, entity.getBody());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void deleteCustomer() {

    }

    @Test
    void getCustomerById() {
        Mockito.when(this.customerServiceImpl.getCustomerById(any(BigInteger.class)))
                .thenReturn(Mono.just(customerResponse));

        StepVerifier.create(this.customerMsController.getCustomerById(customerId))
                .assertNext(response -> {
                    Assertions.assertNotNull(response);
                    Assertions.assertNotNull(response.getBody());
                    Assertions.assertEquals(dni, response.getBody().getDni());
                })
                .verifyComplete();
    }

    @Test
    void getCustomers() {
        Mockito.when(this.customerServiceImpl.getAllCustomers())
                .thenReturn(Flux.just(customerResponse));

        StepVerifier.create(this.customerMsController.getCustomers())
                .assertNext(response -> {
                    Assertions.assertNotNull(response);
                    Assertions.assertNotNull(response.getBody());
                    Assertions.assertEquals(customerId, response.getBody().getId());
                })
                .verifyComplete();

    }

    @Test
    void updateCustomer() {
    }

}