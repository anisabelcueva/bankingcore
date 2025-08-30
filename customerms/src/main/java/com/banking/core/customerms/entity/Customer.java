package com.banking.core.customerms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "firssecondname")
    private String firstSecondName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "dni", unique = true, nullable = false)
    private String dni;

    @Column(name = "email")
    private String email;

//    @OneToMany(mappedBy="customer")
//    private List<Account> listAccounts;

}
