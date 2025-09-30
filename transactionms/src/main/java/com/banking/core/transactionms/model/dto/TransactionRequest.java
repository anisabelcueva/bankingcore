package com.banking.core.transactionms.model.dto;

import com.banking.core.transactionms.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @Min(0)
    private double amount;

    private String accountNumberOrigin;

    private String accountNumberDestination;

}
