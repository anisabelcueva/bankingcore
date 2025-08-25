package com.banking.core.controller;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.CustomerDto;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.entity.AccountType;
import com.banking.core.service.AccountService;
import com.banking.core.service.CustomerService;
import com.banking.core.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bankingapp/accounts")
public class BankingController {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public BankingController(CustomerService customerService, AccountService accountService, TransactionService transactionService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    // Registrar Cliente: POST /bankingapp/accounts/register
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDto customerDto) {
        try {
            CustomerDto newCustomer = customerService.registerCustomer(customerDto);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Abrir Cuenta: POST /bankingapp/accounts/open
    @PostMapping("/open")
    public ResponseEntity<?> openAccount(@RequestParam String dni, @RequestParam AccountType accountType) {
        try {
            AccountDTO newAccount = accountService.openAccount(dni, accountType);
            return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Depositar Dinero: PUT /bankingapp/accounts/deposit
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionDTO transactionDto) {
        try {
            AccountDTO updatedAccount = accountService.deposit(transactionDto);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Retirar Dinero: PUT /bakingapp/accounts/withdraw
    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionDTO transactionDto) {
        try {
            AccountDTO updatedAccount = accountService.withdraw(transactionDto);
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