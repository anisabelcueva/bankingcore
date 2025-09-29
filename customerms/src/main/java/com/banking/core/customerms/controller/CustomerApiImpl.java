package com.banking.core.customerms.controller;

import com.banking.core.customerms.api.CustomersApi;
import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import com.banking.core.customerms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CustomerApiImpl implements CustomersApi {
    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<String>> createCustomer(Mono<CustomerRequest> customerRequest,
                                                       ServerWebExchange exchange) {
        return customerRequest
                .flatMap(customerService::createCustomer) // devuelve Mono<String>
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(Long id,
                                                     ServerWebExchange exchange) {
        return customerService.deleteCustomer(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(Long id,
                                                                  ServerWebExchange exchange) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomers(ServerWebExchange exchange) {
        Flux<CustomerResponse> customers = customerService.getAllCustomers();
        return Mono.just(ResponseEntity.ok(customers));
    }

    @Override
    public Mono<ResponseEntity<String>> updateCustomer(Long id,
                                                       Mono<CustomerRequest> customerRequest,
                                                       ServerWebExchange exchange) {
        return customerRequest
                .flatMap(req -> customerService.updateCustomer(id, req))
                .map(result -> ResponseEntity.ok("Customer updated successfully!"))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
