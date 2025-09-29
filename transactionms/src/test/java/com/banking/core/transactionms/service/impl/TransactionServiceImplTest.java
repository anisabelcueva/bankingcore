package com.banking.core.transactionms.service.impl;

import com.banking.core.transactionms.mocks.MockUtils;
import com.banking.core.transactionms.model.Transaction;
import com.banking.core.transactionms.model.TransactionType;
import com.banking.core.transactionms.model.dto.TransactionRequest;
import com.banking.core.transactionms.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    private TransactionRepository transactionRepository;

    private Transaction transactionSaved;
    private Transaction transactionDeposit;
    private Transaction transactionWithdrawal;
    private Transaction transactionTransfer;

    @BeforeEach
    void setUp() {
        transactionSaved = MockUtils.getTransactionSaved();

        transactionDeposit = MockUtils.getTransactionDeposit();

        transactionWithdrawal = MockUtils.getTransactionWithdrawal();

        transactionTransfer = MockUtils.getTransactionTransfer();
    }

    @Test
    void saveTransaction() {
        Mockito.when(this.transactionRepository.save(Mockito.any(Transaction.class)))
                .thenReturn(Mono.just(transactionSaved));

        StepVerifier.create(this.transactionServiceImpl.saveTransaction(new TransactionRequest(),
                        TransactionType.DEPOSIT))
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertEquals(transactionSaved.getAccountNumberOrigin(), transactionDto.getAccountNumberOrigin());
                    Assertions.assertEquals(transactionSaved.getAccountNumberDestination(), transactionDto.getAccountNumberDestination());
                    Assertions.assertEquals(transactionSaved.getAmount(), transactionDto.getAmount());
                    Assertions.assertEquals(transactionSaved.getDate(), transactionDto.getDate());
                    Assertions.assertEquals(transactionSaved.getTransactionType(), transactionDto.getTransactionType());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void getAllTransaction() {

        Mockito.when(this.transactionRepository.findAll())
                .thenReturn(Flux.just(transactionDeposit, transactionWithdrawal, transactionTransfer));

        StepVerifier.create(this.transactionServiceImpl.getAllTransaction())
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertEquals(TransactionType.DEPOSIT, transactionDto.getTransactionType());
                })
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertEquals(TransactionType.WITHDRAWAL, transactionDto.getTransactionType());
                })
                .assertNext(transactionDto -> {
                    Assertions.assertNotNull(transactionDto);
                    Assertions.assertEquals(TransactionType.TRANSFER, transactionDto.getTransactionType());
                })
                .verifyComplete();

    }

}
