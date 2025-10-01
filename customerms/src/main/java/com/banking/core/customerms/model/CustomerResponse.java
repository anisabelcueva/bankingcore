package com.banking.core.customerms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

  private BigInteger id;

  private String firstName;

  private String lastName;

  private String dni;

  private String email;

}

