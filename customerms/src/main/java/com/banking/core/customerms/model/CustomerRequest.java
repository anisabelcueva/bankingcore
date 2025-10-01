package com.banking.core.customerms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

  private String firstName;

  private String lastName;

  private String dni;

  private String email;

}

