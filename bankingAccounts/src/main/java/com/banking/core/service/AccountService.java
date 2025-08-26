package com.banking.core.service;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.web.model.AccountRequest;
import com.banking.core.web.model.AccountResponse;

public interface AccountService {

    AccountResponse saveAccount(AccountRequest accountRequest);

    AccountDTO deposit(TransactionDTO transactionDto);

    AccountDTO withdraw(TransactionDTO transactionDto);

    double checkBalance(String accountNumber);

}
