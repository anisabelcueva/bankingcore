package com.banking.core.transactionms.service;

import com.banking.core.transactionms.model.Transaction;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Void> saveTransaction(TransactionRequest transactionRequest, TransactionType transactionType);

    Flux<Transaction> getAllTransaction();
}
