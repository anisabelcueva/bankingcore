package com.banking.core.service;

import com.banking.core.web.model.CustomerRequest;
import com.banking.core.web.model.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getListCustomers();

    CustomerResponse getSaveCustomer(CustomerRequest customerRequest);

}
