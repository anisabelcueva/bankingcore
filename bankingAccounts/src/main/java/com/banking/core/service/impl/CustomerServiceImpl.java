package com.banking.core.service.impl;

import com.banking.core.dto.CustomerDto;
import com.banking.core.entity.Customer;
import com.banking.core.repository.CustomerRepository;
import com.banking.core.service.CustomerService;
import com.banking.core.web.mapper.CustomerResponseMapper;
import com.banking.core.web.model.CustomerRequest;
import com.banking.core.web.model.CustomerResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerResponse> getListCustomers() {
        return CustomerResponseMapper.buildListCustomerResponse(customerRepository.findAll());
    }

    @Override
    public CustomerResponse getSaveCustomer(CustomerRequest customerRequest) {

        if (customerRequest.getFirstSecondName() == null || customerRequest.getLastName() == null ||
                customerRequest.getDni() == null || customerRequest.getEmail() == null) {
            throw new IllegalArgumentException("Todos los campos del cliente son obligatorios.");
        }

        if (customerRepository.findByDni(customerRequest.getDni()).isPresent()) {
            throw new IllegalArgumentException("El DNI ya está registrado.");
        }

        if (!EMAIL_PATTERN.matcher(customerRequest.getEmail()).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }

        Customer customer = customerRepository.save(Customer.builder()
                .firstSecondName(customerRequest.getFirstSecondName())
                .lastName(customerRequest.getLastName())
                .dni(customerRequest.getDni())
                .email(customerRequest.getEmail())
                .build());

        return CustomerResponseMapper.buildCustomerResponse(customer);
    }

    @Override
    public Optional<CustomerDto> findByDni(String dni) {
        return customerRepository.findByDni(dni).map(customer ->
                CustomerDto.builder()
                        .customerId(customer.getCustomerId())
                        .firstSecondName(customer.getFirstSecondName())
                        .lastName(customer.getLastName())
                        .dni(customer.getDni())
                        .email(customer.getEmail())
                        .build()
        );
    }

}
