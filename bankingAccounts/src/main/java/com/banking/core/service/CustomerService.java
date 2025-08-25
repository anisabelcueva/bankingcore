package com.banking.core.service;

import com.banking.core.dto.CustomerDto;
import java.util.Optional;

public interface CustomerService {
    CustomerDto registerCustomer(CustomerDto customerDto);
    Optional<CustomerDto> findByDni(String dni);
}