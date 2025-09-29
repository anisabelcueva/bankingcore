package com.banking.core.transactionms.repository;

import com.banking.core.transactionms.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Mono<Transaction> save(Transaction transaction);

    Flux<Transaction> findAll();

}
