package com.banking.core.transactionms.service;

import com.banking.core.transactionms.dto.TransactionDto;
import com.banking.core.transactionms.model.Transaction;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionDto> saveTransaction(TransactionRequest transactionRequest, TransactionType transactionType);

    Flux<TransactionDto> getAllTransaction();

}
