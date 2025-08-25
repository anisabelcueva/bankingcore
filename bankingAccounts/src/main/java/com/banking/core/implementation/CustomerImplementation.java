package com.banking.core.implementation;

import com.banking.core.repository.CustomerRepository;
import com.banking.core.service.CustomerService;
import com.banking.core.web.mapper.CustomerResponseMapper;
import com.banking.core.web.model.CustomerResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CustomerImplementation implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerResponse> getListCustomers() {
        return CustomerResponseMapper.buildCustomerResponse(customerRepository.findAll());
    }

}
