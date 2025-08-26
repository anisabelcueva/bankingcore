package com.banking.core.service.impl;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.entity.Account;
import com.banking.core.entity.Customer;
import com.banking.core.entity.Transaction;
import com.banking.core.repository.AccountRepository;
import com.banking.core.repository.CustomerRepository;
import com.banking.core.repository.TransactionRepository;
import com.banking.core.service.AccountService;
import com.banking.core.web.enums.AccountType;
import com.banking.core.web.enums.TransactionType;
import com.banking.core.web.mapper.AccountResponseMapper;
import com.banking.core.web.model.AccountRequest;
import com.banking.core.web.model.AccountResponse;
import com.banking.core.web.enums.AccountStatus;
import com.banking.core.web.model.TransactionRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(CustomerRepository customerRepository,
                              AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountResponse saveAccount(AccountRequest accountRequest) {
        Optional<Customer> customer = customerRepository.findByDni(accountRequest.getCustomerDni());
        String accountType = accountRequest.getAccountType();

        validations(customer, accountType);

        String accountNumber = generateAccountNumber();
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        while (account.isPresent()) {
            accountNumber = generateAccountNumber();
            account = accountRepository.findByAccountNumber(accountNumber);
        }
        double balance = Double.parseDouble(accountRequest.getBalance());


        Account accountToSave = Account.builder()
                .accountNumber(accountNumber)
                .balance(balance)
                .accountType(AccountType.getByName(accountType))
                .accountStatus(AccountStatus.ACTIVE_ACCOUNT.getId())
                .customer(customer.get())
                .build();
        accountRepository.save(accountToSave);
        return AccountResponseMapper.buildAccountResponse(accountToSave);
    }

    @Override
    public AccountDTO deposit(TransactionRequest transactionRequest) {
        Account account = accountRepository.findById(transactionRequest.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        if (transactionRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("El monto del depósito debe ser positivo.");
        }

        double newBalance = account.getBalance() + transactionRequest.getAmount();
        account.setBalance(newBalance);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(transactionRequest.getAmount())
                .account(account)
                .build();
        transactionRepository.save(transaction);

        accountRepository.save(account);

        return convertToDto(account);
    }

    @Override
    public AccountDTO withdraw(TransactionRequest transactionRequest) {
        Account account = accountRepository.findById(transactionRequest.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        if (transactionRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser positivo.");
        }

        double newBalance = account.getBalance() - transactionRequest.getAmount();

        if (account.getAccountType() == AccountType.SAVINGS_ACCOUNT) {
            if (newBalance < 0) {
                throw new IllegalStateException("Saldo insuficiente. Las cuentas de ahorro no pueden tener saldo negativo.");
            }
        } else if (account.getAccountType() == AccountType.CHECKING_ACCOUNT) {
            if (newBalance < -500.00) {
                throw new IllegalStateException("Límite de sobregiro excedido (-500.00).");
            }
        }

        account.setBalance(newBalance);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount(transactionRequest.getAmount())
                .account(account)
                .build();
        transactionRepository.save(transaction);

        accountRepository.save(account);

        return convertToDto(account);
    }

    @Override
    public double checkBalance(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada.")).getBalance();
    }

    private AccountDTO convertToDto(Account account) {
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType().ordinal())
                .accountStatus(account.getAccountStatus())
                .customerId(account.getCustomer().getCustomerId())
                .transactionIds(account.getTransactions().stream()
                        .map(Transaction::getTransactionId)
                        .collect(Collectors.toList()))
                .build();
    }

    private String generateAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        Long secureNumber = Math.abs(secureRandom.nextLong());
        return String.valueOf(secureNumber);
    }

    private void validations(Optional<Customer> customer, String accountType) {
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer doesn't Exist");
        }

        if (AccountType.getByName(accountType).equals(AccountType.NONE)) {
            throw new IllegalArgumentException("Account Type doesn't Exist");
        }
    }

}
