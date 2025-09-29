package com.banking.core.transactionms.mocks;

import com.banking.core.transactionms.dto.TransactionDto;
import com.banking.core.transactionms.model.Transaction;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;

import java.security.SecureRandom;

public class MockUtils {

    public static Transaction getTransactionSaved() {
        return Transaction.builder()
                .id("68d9d0f09e26e2516e59be3f")
                .date("2025-09-28 09:15:50")
                .transactionType(TransactionType.DEPOSIT)
                .accountNumberDestination(MockUtils.generateAccountNumber())
                .accountNumberOrigin(MockUtils.generateAccountNumber())
                .amount(1500)
                .build();
    }

    public static Transaction getTransactionDeposit() {
        return Transaction.builder()
                .id("68be4069f7d6405c4e6f5756")
                .date("2025-09-26 10:23:45")
                .transactionType(TransactionType.DEPOSIT)
                .accountNumberDestination(MockUtils.generateAccountNumber())
                .accountNumberOrigin(MockUtils.generateAccountNumber())
                .amount(2350)
                .build();
    }

    public static Transaction getTransactionWithdrawal() {
        return Transaction.builder()
                .id("68be4605a64e665e6af92311")
                .date("2025-09-26 11:01:10")
                .transactionType(TransactionType.WITHDRAWAL)
                .accountNumberDestination(MockUtils.generateAccountNumber())
                .accountNumberOrigin(MockUtils.generateAccountNumber())
                .amount(320)
                .build();
    }

    public static Transaction getTransactionTransfer() {
        return Transaction.builder()
                .id("68be4607a64e665e6af92312")
                .date("2025-09-27 16:45:20")
                .transactionType(TransactionType.TRANSFER)
                .accountNumberDestination(MockUtils.generateAccountNumber())
                .accountNumberOrigin(MockUtils.generateAccountNumber())
                .amount(250)
                .build();
    }


    public static TransactionDto getTransactionDto() {
        return TransactionDto.builder()
                .date("2025-09-28 13:17:45")
                .transactionType(TransactionType.DEPOSIT)
                .accountNumberDestination(MockUtils.generateAccountNumber())
                .accountNumberOrigin(MockUtils.generateAccountNumber())
                .amount(250)
                .build();
    }

    public static TransactionRequest buildTransactionRequest() {
        return TransactionRequest.builder()
                .accountNumberOrigin("")
                .accountNumberDestination("")
                .amount(870)
                .build();
    }

    public static String generateAccountNumber() {
        long n = Math.abs(new SecureRandom().nextLong()) % 1_000_000_000_000L;
        return String.format("%012d", n);
    }

}
