package com.banking.core.transactionms.dto;

import com.banking.core.transactionms.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private String accountNumberOrigin;
    private String accountNumberDestination;
    private double amount;
    private String date;
    private TransactionType transactionType;

}
