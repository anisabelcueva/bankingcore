package com.banking.core.transactionms.controller;

import com.banking.core.transactionms.model.dto.TransactionResponse;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import com.banking.core.transactionms.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<TransactionResponse>> deposit(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest, TransactionType.DEPOSIT)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<TransactionResponse>> withdrawal(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest, TransactionType.WITHDRAWAL)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<TransactionResponse>> transfer(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest, TransactionType.TRANSFER)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping("/history")
    public Flux<ResponseEntity<TransactionResponse>> getAllTransactions() {
        return transactionService.getAllTransaction()
                .map(response -> ResponseEntity.ok().body(response));
    }

}
