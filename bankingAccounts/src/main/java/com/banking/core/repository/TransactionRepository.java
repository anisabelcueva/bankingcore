package com.banking.core.repository;
import java.util.List;
import com.banking.core.entity.Account;
import com.banking.core.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}
