package com.banking.core.service.impl;
import com.banking.core.entity.Account;
import com.banking.core.entity.AccountType;
import com.banking.core.exception.InsufficientFundsException;
import com.banking.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountNotFoundException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final TransactionalOperator txOperator;
    private final SecureRandom random = new SecureRandom();

    /**
     * Create a new account for a customer. The account number is generated and guaranteed unique.
     */
    public Mono<Account> create(Long customerId, AccountType accountType) {
        return generateUniqueAccountNumber()
                .flatMap(accountNumber -> {
                    Account account = Account.builder()
                            .accountNumber(accountNumber)
                            .balance(0.0)
                            .accountType(accountType)
                            .customerId(customerId)
                            .build();
                    return repository.save(account);
                });
    }

    /** List all accounts (Flux). */
    public Flux<Account> listAll() {
        return repository.findAll();
    }

    /** Find account by id or error. */
    public Mono<Account> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account not found")));
    }

    /**
     * Deposit amount into account. Operation executed within a reactive transaction.
     */
    public Mono<Account> deposit(Long id, Double amount) {
        if (amount == null || amount <= 0) {
            return Mono.error(new IllegalArgumentException("Amount must be positive"));
        }

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account not found")))
                .flatMap(account -> {
                    account.setBalance(account.getBalance() + amount);
                    return repository.save(account);
                })
                .as(txOperator::transactional);
    }

    /**
     * Withdraw amount from account. Fails if insufficient funds. Executed in a reactive transaction.
     */
    public Mono<Account> withdraw(Long id, Double amount) {
        if (amount == null || amount <= 0) {
            return Mono.error(new IllegalArgumentException("Amount must be positive"));
        }

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("account not found")))
                .flatMap(account -> {
                    if (account.getBalance() < amount) {
                        return Mono.error(new InsufficientFundsException("Insufficient balance" ));
                    }
                    account.setBalance(account.getBalance() - amount);
                    return repository.save(account);
                })
                .as(txOperator::transactional);
    }

    /** Delete account by id. */
    public Mono<Void> delete(Long id) {
        return repository.existsById(id)
                .flatMap(exists -> exists ? repository.deleteById(id) : Mono.error(new AccountNotFoundException("account not found")));
    }

    /* ------------------ Helpers ------------------ */

    /**
     * Generate a unique account number in a reactive manner.
     */
    private Mono<String> generateUniqueAccountNumber() {
        return Mono.defer(() -> {
            String candidate = generateAccountNumber();
            return repository.existsByAccountNumber(candidate)
                    .flatMap(exists -> exists ? Mono.defer(this::generateUniqueAccountNumber) : Mono.just(candidate));
        });
    }

    /** Generate a 12-digit account number (string). */
    private String generateAccountNumber() {
        long n = Math.abs(random.nextLong()) % 1_000_000_000_000L;
        return String.format("%012d", n);
    }
}