package com.banking.core.service.impl;

import com.banking.core.dto.TransactionDTO;
import com.banking.core.entity.Account;
import com.banking.core.entity.Transaction;
import com.banking.core.repository.AccountRepository;
import com.banking.core.repository.TransactionRepository;
import com.banking.core.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        List<Transaction> transactions = transactionRepository.findByAccount(account);

        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TransactionDTO convertToDto(Transaction transaction) {
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .creationDate(transaction.getCreationDate())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .accountId(transaction.getAccount().getAccountId())
                .build();
    }
}