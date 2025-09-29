package com.banking.core.transactionms.controller;

import com.banking.core.transactionms.dto.TransactionDto;
import com.banking.core.transactionms.mocks.MockUtils;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import com.banking.core.transactionms.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

class TransactionControllerTest {

    @InjectMocks TransactionController transactionController;
    @Mock private TransactionServiceImpl transactionServiceImpl;
    private TransactionDto transactionDtoSaved;

    @BeforeEach
    void setUp() {
        transactionDtoSaved = MockUtils.getTransactionDto();
    }

    @Test
    void deposit() {
        Mockito.when(this.transactionServiceImpl.saveTransaction(any(TransactionRequest.class), any(TransactionType.class)))
                .thenReturn(Mono.just(transactionDtoSaved));

    }



}