package com.banking.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;


@Table("account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Getter
    @Setter
    @Id
    private Long id;
    private String accountNumber;
    private Double balance;
    private String accountType;
    private Long customerId;


}