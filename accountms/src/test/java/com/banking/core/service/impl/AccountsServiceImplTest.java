package com.banking.core.service.impl;

import org.junit.jupiter.api.Test;

import com.banking.core.account.model.AccountsRequest;
import com.banking.core.account.model.AccountsResponse;
import com.banking.core.account.model.TransactionRequest;
import com.banking.core.entity.Account;
import com.banking.core.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.security.auth.login.AccountNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AccountsServiceImplTest {

    @InjectMocks
    private AccountsServiceImpl accountsService;

    @Mock
    private AccountRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper para crear una cuenta dummy
    private Account createDummyAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123ABC");
        account.setBalance(1000.0);
        account.setAccountType("AHORROS");
        account.setCustomerId(10L);
        return account;
    }

    // Helper para crear AccountsRequest dummy
    private AccountsRequest createDummyRequest() {
        AccountsRequest request = new AccountsRequest();
        request.setClientId("10");
        request.setAccountType("AHORROS");
        return request;
    }

    // Helper para crear TransactionRequest dummy
    private TransactionRequest createDummyTransaction(Double amount) {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);
        return request;
    }

    @Test
    void createAccount_success() {
        Account toSave = new Account();
        toSave.setCustomerId(10L);
        toSave.setAccountType("AHORROS");
        toSave.setBalance(0.0);

        when(repository.save(any(Account.class)))
                .thenAnswer(invocation -> {
                    Account acc = invocation.getArgument(0);
                    acc.setId(1L);
                    acc.setAccountNumber("123ABC");
                    return Mono.just(acc);
                });

        AccountsRequest request = createDummyRequest();

        StepVerifier.create(accountsService.createAccount(request))
                .expectNextMatches(response ->
                        response.getId().equals("1") &&
                                response.getAccountNumber().equals("123ABC") &&
                                response.getBalance() == 0.0 &&
                                response.getAccountType().equals("AHORROS") &&
                                response.getClientId().equals("10")
                )
                .verifyComplete();

        verify(repository).save(any(Account.class));
    }

    @Test
    void getAllAccounts_success() {
        Account acc1 = createDummyAccount();
        Account acc2 = createDummyAccount();
        acc2.setId(2L);
        acc2.setAccountNumber("456DEF");

        when(repository.findAll()).thenReturn(Flux.just(acc1, acc2));

        StepVerifier.create(accountsService.getAllAccounts())
                .expectNextCount(2)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void getAccountById_found() {
        Account acc = createDummyAccount();

        when(repository.findById(1L)).thenReturn(Mono.just(acc));

        StepVerifier.create(accountsService.getAccountById("1"))
                .expectNextMatches(response -> response.getId().equals("1"))
                .verifyComplete();

        verify(repository).findById(1L);
    }

    @Test
    void getAccountById_notFound() {
        when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(accountsService.getAccountById("1"))
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException &&
                                ((ResponseStatusException) throwable).getStatus() == HttpStatus.NOT_FOUND
                )
                .verify();

        verify(repository).findById(1L);
    }

    @Test
    void deleteAccount_success() {
        Account acc = createDummyAccount();
        when(repository.findById(1L)).thenReturn(Mono.just(acc));
        when(repository.delete(acc)).thenReturn(Mono.empty());

        StepVerifier.create(accountsService.deleteAccount("1"))
                .verifyComplete();

        verify(repository).findById(1L);
        verify(repository).delete(acc);
    }

    @Test
    void deleteAccount_notFound() {
        when(repository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(accountsService.deleteAccount("1"))
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException &&
                                ((ResponseStatusException) throwable).getStatus() == HttpStatus.INTERNAL_SERVER_ERROR
                )
                .verify();

        verify(repository).findById(1L);
        verify(repository, never()).delete(any());
    }

    @Test
    void depositAccount_success() {
        Account acc = createDummyAccount();
        when(repository.findById(1L)).thenReturn(Mono.just(acc));
        when(repository.save(any(Account.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        TransactionRequest request = createDummyTransaction(500.0);

        StepVerifier.create(accountsService.depositAccount("1", request))
                .expectNextMatches(response -> response.getBalance() == 1500.0)
                .verifyComplete();

        verify(repository).findById(1L);
        verify(repository).save(any(Account.class));
    }

    @Test
    void depositAccount_invalidAmount() {
        TransactionRequest request = createDummyTransaction(-10.0);

        StepVerifier.create(accountsService.depositAccount("1", request))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().equals("Deposit amount must be positive"))
                .verify();

        verify(repository, never()).findById(anyLong());
    }

    @Test
    void withdrawAccount_success() {
        Account acc = createDummyAccount();
        when(repository.findById(1L)).thenReturn(Mono.just(acc));
        when(repository.save(any(Account.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        TransactionRequest request = createDummyTransaction(500.0);

        StepVerifier.create(accountsService.withdrawAccount("1", request))
                .expectNextMatches(response -> response.getBalance() == 500.0)
                .verifyComplete();

        verify(repository).findById(1L);
        verify(repository).save(any(Account.class));
    }

    @Test
    void withdrawAccount_insufficientFunds() {
        Account acc = createDummyAccount();
        when(repository.findById(1L)).thenReturn(Mono.just(acc));

        TransactionRequest request = createDummyTransaction(1500.0);

        StepVerifier.create(accountsService.withdrawAccount("1", request))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().equals("Insufficient funds"))
                .verify();

        verify(repository).findById(1L);
        verify(repository, never()).save(any(Account.class));
    }

    @Test
    void withdrawAccount_invalidAmount() {
        TransactionRequest request = createDummyTransaction(0.0);

        StepVerifier.create(accountsService.withdrawAccount("1", request))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().equals("Withdrawal amount must be positive"))
                .verify();

        verify(repository, never()).findById(anyLong());
    }
}