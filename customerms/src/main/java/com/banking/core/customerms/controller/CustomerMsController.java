package com.banking.core.customerms.controller;

import com.banking.core.customerms.service.CustomerService;
import com.banking.core.customerms.web.model.CustomerRequest;
import com.banking.core.customerms.web.model.CustomerResponse;
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

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/customers")
public class CustomerMsController {

    private final CustomerService customerService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        try {
            List<CustomerResponse> customers = this.customerService.getListCustomers();
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<CustomerResponse> saveCustomer(
            @RequestBody CustomerRequest customerRequest
    ) {
        try {
            CustomerResponse customerResponse = this.customerService.saveCustomer(customerRequest);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable(name = "id") long customerId
    ) {
        try {
            CustomerResponse customer = this.customerService.getCustomer(customerId);
            if (Objects.isNull(customer)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable(name = "id") long customerId,
            @RequestBody CustomerRequest customerRequest
    ) {
        try {
            CustomerResponse customerResponse = this.customerService.updateCustomer(customerRequest, customerId);
            if (Objects.isNull(customerResponse)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerResponse> deleteCustomer(
            @PathVariable(name = "id") long customerId
    ) {
        try {
            CustomerResponse custom = this.customerService.deleteCustomer(customerId);
            if (Objects.nonNull(custom)){
                return new ResponseEntity<>(custom, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
