package com.banking.core.transactionms.controller;

import com.banking.core.transactionms.model.Transaction;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import com.banking.core.transactionms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> deposit(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest, TransactionType.DEPOSIT);
    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> withdrawal(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest, TransactionType.WITHDRAWAL);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> transfer(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest, TransactionType.TRANSFER);
    }

    @GetMapping("/history")
    public Flux<Transaction> getAllTransactions() {
        return transactionService.getAllTransaction();
    }

}
