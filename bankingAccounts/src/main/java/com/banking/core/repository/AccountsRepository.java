package com.banking.core.repository;

import com.banking.core.entity.Accounts;
import com.banking.core.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByAccountNumber(String accountNumber);

}
