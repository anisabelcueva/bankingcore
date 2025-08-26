package com.banking.core.service;

import com.banking.core.dto.CustomerDto;
import com.banking.core.web.model.CustomerRequest;
import com.banking.core.web.model.CustomerResponse;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerResponse> getListCustomers();

    CustomerResponse getSaveCustomer(CustomerRequest customerRequest);

    Optional<CustomerDto> findByDni(String dni);
}
