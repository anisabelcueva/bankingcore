package com.banking.core.transactionms.service.impl;

import com.banking.core.transactionms.model.Transaction;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import com.banking.core.transactionms.repository.TransactionRepository;
import com.banking.core.transactionms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Mono<Void> saveTransaction(TransactionRequest transactionRequest, TransactionType transactionType) {
        String dateAndTime = getDateAndTimeFormatted();

        Transaction transaction =Transaction.builder()
                .transactionType(transactionType)
                .amount(transactionRequest.getAmount())
                .date(dateAndTime)
                .accountNumberOrigin(transactionRequest.getAccountNumberOrigin())
                .accountNumberDestination(transactionRequest.getAccountNumberDestination())
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    public Flux<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    private String getDateAndTimeFormatted() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String pattern = "yyyy-MM-dd HH:mm:ss"; // 2025-09-07 20:55:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return currentDateTime.format(formatter);
    }

}
