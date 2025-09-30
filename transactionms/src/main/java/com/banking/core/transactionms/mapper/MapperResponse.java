package com.banking.core.transactionms.mapper;

import com.banking.core.transactionms.model.dto.TransactionResponse;
import com.banking.core.transactionms.model.Transaction;

public class MapperResponse {

    public static TransactionResponse buildDtoResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .accountNumberOrigin(transaction.getAccountNumberOrigin())
                .accountNumberDestination(transaction.getAccountNumberDestination())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .transactionType(transaction.getTransactionType())
                .build();
    }

}
