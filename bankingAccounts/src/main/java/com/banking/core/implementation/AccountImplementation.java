package com.banking.core.implementation;

import com.banking.core.entity.Accounts;
import com.banking.core.entity.Customer;
import com.banking.core.repository.AccountsRepository;
import com.banking.core.repository.CustomerRepository;
import com.banking.core.service.AccountService;
import com.banking.core.web.enums.AccountType;
import com.banking.core.web.mapper.AccountResponseMapper;
import com.banking.core.web.model.AccountRequest;
import com.banking.core.web.model.AccountResponse;
import com.banking.core.web.enums.AccountStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@Log4j2
public class AccountImplementation implements AccountService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;

    public AccountImplementation(CustomerRepository customerRepository, AccountsRepository accountsRepository) {
        this.customerRepository = customerRepository;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public AccountResponse saveAccount(AccountRequest accountRequest) {
        Optional<Customer> customer = customerRepository.findByDni(accountRequest.getCustomerDni());
        int accountType = accountRequest.getAccountType();

        validations(customer, accountType);

        String accountNumber = generateAccountNumber();
        Optional<Accounts> account = accountsRepository.findByAccountNumber(accountNumber);
        while (account.isPresent()) {
            accountNumber = generateAccountNumber();
            account = accountsRepository.findByAccountNumber(accountNumber);
        }
        double balance = Double.parseDouble(accountRequest.getBalance());


        Accounts accountToSave = Accounts.builder()
                .accountNumber(accountNumber)
                .balance(balance)
                .accountType(accountType)
                .accountStatus(AccountStatus.ACTIVE_ACCOUNT.getId())
                .customer(customer.get())
                .build();
        accountsRepository.save(accountToSave);
        return AccountResponseMapper.buildAccountResponse(accountToSave);
    }


    private String generateAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        Long secureNumber = Math.abs(secureRandom.nextLong());
        return String.valueOf(secureNumber);
    }

    private void validations(Optional<Customer> customer, int accountType) {
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer doesn't Exist");
        }

        if (AccountType.getById(accountType).equals(AccountType.NONE)) {
            throw new IllegalArgumentException("Account Type doesn't Exist");
        }
    }

}
