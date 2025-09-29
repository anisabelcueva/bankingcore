package com.banking.core.customerms.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String dni;

    private String email;

}