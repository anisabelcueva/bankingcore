package com.banking.core.customerms.service;


import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerResponse> getAllCustomers();

    Mono<CustomerResponse> getCustomerById(Long id);

    Mono<String> createCustomer(CustomerRequest request);

    Mono<String> updateCustomer(Long id, CustomerRequest request);

    Mono<Void> deleteCustomer(Long id);
}
