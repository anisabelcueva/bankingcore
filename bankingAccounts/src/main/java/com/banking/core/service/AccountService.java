package com.banking.core.service;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.web.model.AccountRequest;
import com.banking.core.web.model.AccountResponse;
import com.banking.core.web.model.TransactionRequest;

public interface AccountService {

    AccountResponse saveAccount(AccountRequest accountRequest);

    AccountDTO deposit(TransactionRequest transactionRequest);

    AccountDTO withdraw(TransactionRequest transactionRequest);

    double checkBalance(String accountNumber);

}
