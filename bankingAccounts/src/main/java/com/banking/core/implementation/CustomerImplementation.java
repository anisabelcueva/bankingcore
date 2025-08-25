package com.banking.core.implementation;

import com.banking.core.entity.Customer;
import com.banking.core.repository.AccountsRepository;
import com.banking.core.repository.CustomerRepository;
import com.banking.core.service.CustomerService;
import com.banking.core.web.mapper.CustomerResponseMapper;
import com.banking.core.web.model.CustomerRequest;
import com.banking.core.web.model.CustomerResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CustomerImplementation implements CustomerService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;

    public CustomerImplementation(CustomerRepository customerRepository,
                                  AccountsRepository accountsRepository) {
        this.customerRepository = customerRepository;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public List<CustomerResponse> getListCustomers() {
        return CustomerResponseMapper.buildListCustomerResponse(customerRepository.findAll());
    }

    @Override
    public CustomerResponse getSaveCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.save(Customer.builder()
                .firsSecondName(customerRequest.getFirsSecondName())
                .lastName(customerRequest.getLastName())
                .dni(customerRequest.getDni())
                .email(customerRequest.getEmail())
                .build());

        return CustomerResponseMapper.buildCustomerResponse(customer);
    }

}
