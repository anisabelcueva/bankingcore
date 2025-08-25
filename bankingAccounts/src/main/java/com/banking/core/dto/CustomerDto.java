package com.banking.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private long customerId;
    private String firsSecondName;
    private String lastName;
    private String dni;
    private String email;

}
