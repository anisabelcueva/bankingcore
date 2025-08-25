package com.banking.core.service.impl;

import com.banking.core.dto.AccountDTO;
import com.banking.core.dto.TransactionDTO;
import com.banking.core.entity.Account;
import com.banking.core.entity.AccountType;
import com.banking.core.entity.Customer;
import com.banking.core.entity.Transaction;
import com.banking.core.entity.TransactionType;
import com.banking.core.repository.AccountRepository;
import com.banking.core.repository.CustomerRepository;
import com.banking.core.repository.TransactionRepository;
import com.banking.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDTO openAccount(String dni, AccountType accountType) {
        Customer customer = customerRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con DNI " + dni + " no existe."));

        Account newAccount = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .balance(0.0)
                .accountType(accountType)
                .accountStatus(1)
                .customer(customer)
                .build();

        Account savedAccount = accountRepository.save(newAccount);

        return AccountDTO.builder()
                .accountId(savedAccount.getAccountId())
                .accountNumber(savedAccount.getAccountNumber())
                .balance(savedAccount.getBalance())
                .accountType(savedAccount.getAccountType().ordinal())
                .accountStatus(savedAccount.getAccountStatus())
                .customerId(savedAccount.getCustomer().getCustomerId())
                .build();
    }

    @Override
    public AccountDTO deposit(TransactionDTO transactionDto) {
        Account account = accountRepository.findByAccountNumber(String.valueOf(transactionDto.getAccountId()))
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        if (transactionDto.getAmount() <= 0) {
            throw new IllegalArgumentException("El monto del depósito debe ser positivo.");
        }

        double newBalance = account.getBalance() + transactionDto.getAmount();
        account.setBalance(newBalance);

        Transaction transaction = Transaction.builder()
                .creationDate(LocalDateTime.now())
                .transactionType(TransactionType.DEPOSIT)
                .amount(transactionDto.getAmount())
                .account(account)
                .build();
        transactionRepository.save(transaction);

        accountRepository.save(account);

        return convertToDto(account);
    }

    @Override
    public AccountDTO withdraw(TransactionDTO transactionDto) {
        Account account = accountRepository.findByAccountNumber(String.valueOf(transactionDto.getAccountId()))
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada."));

        if (transactionDto.getAmount() <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser positivo.");
        }

        double newBalance = account.getBalance() - transactionDto.getAmount();

        if (account.getAccountType() == AccountType.SAVINGS) {
            if (newBalance < 0) {
                throw new IllegalStateException("Saldo insuficiente. Las cuentas de ahorro no pueden tener saldo negativo.");
            }
        } else if (account.getAccountType() == AccountType.CURRENT) {
            if (newBalance < -500.00) {
                throw new IllegalStateException("Límite de sobregiro excedido (-500.00).");
            }
        }

        account.setBalance(newBalance);

        Transaction transaction = Transaction.builder()
                .creationDate(LocalDateTime.now())
                .transactionType(TransactionType.WITHDRAW)
                .amount(transactionDto.getAmount())
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
}

