package com.banking.core.controller;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.AmountRequest;
import com.banking.core.dto.CreateAccountRequest;
import com.banking.core.entity.Account;
import com.banking.core.service.impl.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account")
@Validated
@Tag(name = "Accounts", description = "Bank account management operations")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private AccountDTO toDto(Account a) {
        AccountDTO dto = new AccountDTO();
        dto.setId(a.getId());
        dto.setAccountNumber(a.getAccountNumber());
        dto.setBalance(a.getBalance());
        dto.setAccountType(a.getAccountType());
        dto.setCustomerId(a.getCustomerId());
        return dto;
    }

    @PostMapping
    @Operation(summary = "Create a new bank account", description = "Creates a new bank account for a given customer")
    public Mono<ResponseEntity<AccountDTO>> create(
            @Valid @RequestBody CreateAccountRequest request) {
        return accountService.create(request.getCustomerId(), request.getAccountType())
                .map(this::toDto)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @GetMapping
    @Operation(summary = "List all accounts", description = "Retrieves all existing bank accounts")
    public Flux<AccountDTO> listAll() {
        return accountService.listAll()
                .map(this::toDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieves the details of a bank account by its ID")
    public Mono<ResponseEntity<AccountDTO>> getById(
            @Parameter(description = "Unique identifier of the account") @PathVariable Long id) {
        return accountService.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{accountId}/deposit")
    @Operation(summary = "Deposit into account", description = "Deposits a specified amount into the given account")
    public Mono<ResponseEntity<AccountDTO>> deposit(
            @Parameter(description = "Unique identifier of the account") @PathVariable Long accountId,
            @Valid @RequestBody AmountRequest request) {
        return accountService.deposit(accountId, request.getAmount())
                .map(this::toDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{accountId}/withdraw")
    @Operation(summary = "Withdraw from account", description = "Withdraws a specified amount from the given account")
    public Mono<ResponseEntity<AccountDTO>> withdraw(
            @Parameter(description = "Unique identifier of the account") @PathVariable Long accountId,
            @Valid @RequestBody AmountRequest request) {
        return accountService.withdraw(accountId, request.getAmount())
                .map(this::toDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account", description = "Deletes a bank account by its ID")
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the account") @PathVariable Long id) {
        return accountService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}