package com.banking.core.dto;

import com.banking.core.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for Account entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private Double balance;
    private AccountType accountType;
    private Long customerId;
}
