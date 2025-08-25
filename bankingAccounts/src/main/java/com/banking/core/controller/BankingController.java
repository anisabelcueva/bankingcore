package com.banking.core.controller;

import com.banking.core.service.CustomerService;
import com.banking.core.web.model.CustomerRequest;
import com.banking.core.web.model.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api")
public class BankingController {

    private final CustomerService customerService;

    @GetMapping("/customer/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CustomerResponse>> listCustomers() {
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

    @PostMapping("/customer/save")
    public ResponseEntity<CustomerResponse> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse customerResponse = this.customerService.getSaveCustomer(customerRequest);
            return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/accounts/save")
    public ResponseEntity<CustomerResponse> saveAccount(@RequestBody CustomerRequest customerRequest) {
        // In Progress by Ana Isabel Cueva
        return null;
    }





}
