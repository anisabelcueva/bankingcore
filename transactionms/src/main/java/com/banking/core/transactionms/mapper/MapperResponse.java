package com.banking.core.transactionms.mapper;

import com.banking.core.transactionms.dto.TransactionDto;
import com.banking.core.transactionms.model.Transaction;
import reactor.core.publisher.Mono;

public class MapperResponse {

    public static TransactionDto buildDtoResponse(Transaction transaction) {
        return TransactionDto.builder()
                .accountNumberOrigin(transaction.getAccountNumberOrigin())
                .accountNumberDestination(transaction.getAccountNumberDestination())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .transactionType(transaction.getTransactionType())
                .build();
    }

}
