package com.banking.core.controller;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.service.AccountService;
import com.banking.core.service.CustomerService;
import com.banking.core.service.TransactionService;
import com.banking.core.web.model.AccountRequest;
import com.banking.core.web.model.AccountResponse;
import com.banking.core.web.model.CustomerRequest;
import com.banking.core.web.model.CustomerResponse;
import com.banking.core.web.model.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final AccountService accountService;
    private final TransactionService transactionService;

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
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/accounts/save")
    public ResponseEntity<AccountResponse> saveAccount(@RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse accountResponse = this.accountService.saveAccount(accountRequest);
            return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Depositar Dinero: PUT /bankingapp/accounts/deposit
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionRequest transactionRequest) {
        try {
            AccountDTO updatedAccount = accountService.deposit(transactionRequest);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Retirar Dinero: PUT /bakingapp/accounts/withdraw
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionRequest transactionRequest) {
        try {
            AccountDTO updatedAccount = accountService.withdraw(transactionRequest);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Consultar Saldo: GET /bakingapp/accounts/balance/{accountNumber}
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<?> checkBalance(@PathVariable String accountNumber) {
        try {
            double balance = accountService.checkBalance(accountNumber);
            return new ResponseEntity<>("El saldo actual es: " + balance, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Consultar Transacciones: GET /bakingapp/accounts/transactions/{accountNumber}
    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountNumber) {
        try {
            List<TransactionDTO> transactions = transactionService.getTransactionsByAccount(accountNumber);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
