package com.banking.core.dto;

import com.banking.core.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private long accountId;
    private String accountNumber;
    private double balance;
    private int accountType;
    private int accountStatus;
    private List<Long> transactionIds;
    private long customerId;
}
