package com.banking.core.web.mapper;

import com.banking.core.entity.Account;
import com.banking.core.web.enums.AccountStatus;
import com.banking.core.web.enums.AccountType;
import com.banking.core.web.model.AccountResponse;

public class AccountResponseMapper {

    public static AccountResponse buildAccountResponse(Account account) {
        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .balance(String.valueOf(account.getBalance()))
                .accountType(AccountType.getByName(account.getAccountType().getName()).getName())
                .accountStatus(AccountStatus.getById(account.getAccountStatus()).getName())
                .build();
    }

}
