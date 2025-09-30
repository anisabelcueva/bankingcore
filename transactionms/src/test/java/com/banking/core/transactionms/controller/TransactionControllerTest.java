package com.banking.core.transactionms.controller;

import com.banking.core.transactionms.model.dto.TransactionResponse;
import com.banking.core.transactionms.mocks.MockUtils;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import com.banking.core.transactionms.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks TransactionController transactionController;
    @Mock private TransactionServiceImpl transactionServiceImpl;
    private TransactionResponse transactionResponseDepositSaved;
    private TransactionResponse transactionResponseWithdrawalSaved;
    private TransactionResponse transactionResponseTransferSaved;

    @BeforeEach
    void setUp() {
        transactionResponseDepositSaved = MockUtils.getTransactionDtoDeposit();

        transactionResponseWithdrawalSaved = MockUtils.getTransactionDtoWithdrawal();

        transactionResponseTransferSaved = MockUtils.getTransactionDtoTransfer();
    }

    @Test
    void deposit() {
        Mockito.when(this.transactionServiceImpl.saveTransaction(any(TransactionRequest.class), any(TransactionType.class)))
                .thenReturn(Mono.just(transactionResponseDepositSaved));

                StepVerifier.create(this.transactionController.deposit(new TransactionRequest()))
                        .assertNext( entity ->{
                            Assertions.assertNotNull(entity.getBody());
                            Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
                            Assertions.assertEquals(TransactionType.DEPOSIT, entity.getBody().getTransactionType());
                        })
                        .expectComplete()
                        .verify();
    }

    @Test
    void withdrawal() {
        Mockito.when(this.transactionServiceImpl.saveTransaction(any(TransactionRequest.class), any(TransactionType.class)))
                .thenReturn(Mono.just(transactionResponseWithdrawalSaved));

        StepVerifier.create(this.transactionController.withdrawal(new TransactionRequest()))
                .assertNext( entity ->{
                    Assertions.assertNotNull(entity.getBody());
                    Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
                    Assertions.assertEquals(TransactionType.WITHDRAWAL, entity.getBody().getTransactionType());
                })
                .expectComplete()
                .verify();

    }

    @Test
    void transfer() {
        Mockito.when(this.transactionServiceImpl.saveTransaction(any(TransactionRequest.class), any(TransactionType.class)))
                .thenReturn(Mono.just(transactionResponseTransferSaved));

        StepVerifier.create(this.transactionController.transfer(new TransactionRequest()))
                .assertNext( entity ->{
                    Assertions.assertNotNull(entity.getBody());
                    Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
                    Assertions.assertEquals(TransactionType.TRANSFER, entity.getBody().getTransactionType());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void getAllTransactions() {
        Mockito.when(this.transactionServiceImpl.getAllTransaction())
                .thenReturn(Flux.just(transactionResponseDepositSaved,
                        transactionResponseWithdrawalSaved,
                        transactionResponseTransferSaved));

        StepVerifier.create(this.transactionController.getAllTransactions())
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertNotNull(transactionDto.getBody());
                    Assertions.assertEquals(TransactionType.DEPOSIT, transactionDto.getBody().getTransactionType());
                })
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertNotNull(transactionDto.getBody());
                    Assertions.assertEquals(TransactionType.WITHDRAWAL, transactionDto.getBody().getTransactionType());
                })
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertNotNull(transactionDto.getBody());
                    Assertions.assertEquals(TransactionType.TRANSFER, transactionDto.getBody().getTransactionType());
                })
                .verifyComplete();


    }

}