package com.banking.core.dto;

import com.banking.core.web.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private long transactionId;
    private LocalDateTime creationDate;
    private TransactionType transactionType;
    private double amount;
    private long accountId;

}
