package com.banking.core.service.impl;

import com.banking.core.account.model.AccountsRequest;
import com.banking.core.account.model.AccountsResponse;
import com.banking.core.account.model.TransactionRequest;
import com.banking.core.entity.Account;
import com.banking.core.repository.AccountRepository;
import com.banking.core.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@Service
public class AccountsServiceImpl implements AccountsService{

    @Autowired
    private AccountRepository repository;


    // Helper para mapear Account a AccountsResponse
    private AccountsResponse mapToResponse(Account account) {
        AccountsResponse response = new AccountsResponse();
        response.setId(account.getId().toString());
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setAccountType(account.getAccountType());
        response.setClientId(String.valueOf(account.getCustomerId()));
        return response;
    }

    // Crear cuenta
    @Override
    public Mono<AccountsResponse> createAccount(AccountsRequest request) {
        Account account = mapToEntity(request);

        return repository.save(account)
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    // Listar todas las cuentas
    @Override
    public Flux<AccountsResponse> getAllAccounts() {
        return repository.findAll()
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    // Obtener cuenta por ID
    @Override
    public Mono<AccountsResponse> getAccountById(String id) {
        return repository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No customers found")))
                .map(this::mapToResponse)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    // Eliminar cuenta
    @Override
    public Mono<Void> deleteAccount(String id) {
        return repository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")))
                .flatMap(repository::delete)
                .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex));
    }

    // Depositar dinero en cuenta
    @Override
    public Mono<AccountsResponse> depositAccount(String id, TransactionRequest request) {
        return Mono.justOrEmpty(request.getAmount())
                .filter(amount -> amount > 0)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Deposit amount must be positive")))
                // Convertimos el id String a Long dentro del flujo reactivo
                .flatMap(amount -> Mono.fromCallable(() -> Long.valueOf(id))
                        .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid account ID format", ex))
                        // Buscamos la cuenta con el id convertido
                        .flatMap(accountId -> repository.findById(accountId)
                                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account not found")))
                                .flatMap(account -> {
                                    account.setBalance(account.getBalance() + amount);
                                    return repository.save(account);
                                })
                        )
                )
                .map(this::mapToResponse);
    }

    // Retirar dinero de cuenta
    @Override
    public Mono<AccountsResponse> withdrawAccount(String id, TransactionRequest request) {
        return Mono.justOrEmpty(request.getAmount())
                .filter(amount -> amount > 0)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Withdrawal amount must be positive")))
                .flatMap(amount -> Mono.fromCallable(() -> Long.valueOf(id))
                        .onErrorMap(ex -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid account ID format", ex))
                        .flatMap(accountId -> repository.findById(accountId)
                                .switchIfEmpty(Mono.error(new AccountNotFoundException("Account not found")))
                                .flatMap(account -> {
                                    if (account.getBalance() < amount) {
                                        return Mono.error(new RuntimeException("Insufficient funds"));
                                    }
                                    account.setBalance(account.getBalance() - amount);
                                    return repository.save(account);
                                })
                        )
                )
                .map(this::mapToResponse);
    }

    private Account mapToEntity(AccountsRequest request){
        Account account = new Account();
        account.setCustomerId(Long.valueOf(request.getClientId()));
        account.setAccountType(request.getAccountType());
        account.setBalance(0.0);
        account.setAccountNumber(UUID.randomUUID().toString().replace("-", ""));
        return account;
    }

}
