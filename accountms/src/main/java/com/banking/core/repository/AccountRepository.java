package com.banking.core.repository;
import com.banking.core.entity.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface AccountRepository extends R2dbcRepository<Account, Long> {
    Mono<Boolean> existsByAccountNumber(String accountNumber);
    Mono<Account> findByAccountNumber(String accountNumber);
}