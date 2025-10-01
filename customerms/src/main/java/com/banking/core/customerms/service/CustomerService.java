package com.banking.core.customerms.service;

import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface CustomerService {

    Flux<CustomerResponse> getAllCustomers();

    Mono<CustomerResponse> getCustomerById(BigInteger id);

    Mono<String> createCustomer(CustomerRequest request);

    Mono<String> updateCustomer(BigInteger id, CustomerRequest request);

    Mono<Void> deleteCustomer(BigInteger id);
}
