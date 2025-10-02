package com.banking.core.customerms.controller;

import com.banking.core.customerms.model.CustomerRequest;
import com.banking.core.customerms.model.CustomerResponse;
import com.banking.core.customerms.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/customers")
public class CustomerMsController {

    private final CustomerService customerService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<String>> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return this.customerService.createCustomer(customerRequest)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable(name = "id") BigInteger id) {
        return customerService.deleteCustomer(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(@PathVariable(name = "id") BigInteger id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ResponseEntity<CustomerResponse>> getCustomers() {
        return customerService.getAllCustomers()
                .map(response -> ResponseEntity.ok().body(response));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<String>> updateCustomer(@PathVariable(name = "id") BigInteger id,
                                                       @Valid @RequestBody CustomerRequest customerRequest) {
        return this.customerService.updateCustomer(id, customerRequest)
                .map(result -> ResponseEntity.ok("Customer updated successfully!"))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
