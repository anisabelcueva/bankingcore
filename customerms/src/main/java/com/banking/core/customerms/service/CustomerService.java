package com.banking.core.customerms.service;

import com.banking.core.customerms.dto.CustomerDto;
import com.banking.core.customerms.web.model.CustomerRequest;
import com.banking.core.customerms.web.model.CustomerResponse;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerResponse getCustomer(long customerId);

    List<CustomerResponse> getListCustomers();

    CustomerResponse saveCustomer(CustomerRequest customerRequest);

    CustomerResponse updateCustomer(CustomerRequest customerRequest, long customerId);

    CustomerResponse deleteCustomer(long customerId);

    Optional<CustomerDto> findByDni(String dni);
}
