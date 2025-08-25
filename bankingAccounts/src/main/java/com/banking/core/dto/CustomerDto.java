package com.banking.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private long customerId;
    private String firstSecondName;
    private String lastName;
    private String dni;
    private String email;

}
