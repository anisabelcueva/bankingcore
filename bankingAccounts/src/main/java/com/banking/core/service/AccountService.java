package com.banking.core.service;

import com.banking.core.web.model.AccountRequest;
import com.banking.core.web.model.AccountResponse;

public interface AccountService {

    AccountResponse saveAccount(AccountRequest accountRequest);

}
