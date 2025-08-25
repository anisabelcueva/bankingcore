package com.banking.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long accountId;

    @Column(name = "account_number",unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance")
    private double balance;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING) // <-- IMPORTANTE: guarda el nombre del enum como texto en la BD
    private AccountType accountType;

    @Column(name = "account_status")
    private int accountStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id_fk")
    Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

}
