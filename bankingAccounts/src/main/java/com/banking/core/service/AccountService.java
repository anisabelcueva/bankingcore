package com.banking.core.service;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.entity.AccountType;

public interface AccountService {
    AccountDTO openAccount(String dni, AccountType accountType);
    AccountDTO deposit(TransactionDTO transactionDto);
    AccountDTO withdraw(TransactionDTO transactionDto);
    double checkBalance(String accountNumber);
}