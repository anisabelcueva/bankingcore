package com.banking.core.web.mapper;

import com.banking.core.entity.Accounts;
import com.banking.core.web.enums.AccountStatus;
import com.banking.core.web.enums.AccountType;
import com.banking.core.web.model.AccountResponse;

public class AccountResponseMapper {

    public static AccountResponse buildAccountResponse(Accounts account) {
        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .balance(String.valueOf(account.getBalance()))
                .accountType(AccountType.getById(account.getAccountType()).getName())
                .accountStatus(AccountStatus.getById(account.getAccountStatus()).getName())
                .build();
    }

}
