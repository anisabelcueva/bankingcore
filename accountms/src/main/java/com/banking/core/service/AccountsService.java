package com.banking.core.service;
import com.banking.core.account.model.AccountsRequest;
import com.banking.core.account.model.AccountsResponse;
import com.banking.core.account.model.TransactionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountsService {

    Mono<AccountsResponse> createAccount(AccountsRequest request);

    Flux<AccountsResponse> getAllAccounts();

    Mono<AccountsResponse> getAccountById(String id);

    Mono<Void> deleteAccount(String id);

    Mono<AccountsResponse> depositAccount(String id, TransactionRequest request);

    Mono<AccountsResponse> withdrawAccount(String id, TransactionRequest request);
}