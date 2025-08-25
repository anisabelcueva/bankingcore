package com.banking.core.service.impl;

import com.banking.core.dto.CustomerDto;
import com.banking.core.entity.Customer;
import com.banking.core.repository.CustomerRepository;
import com.banking.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) {
        if (customerDto.getFirstSecondName() == null || customerDto.getLastName() == null ||
                customerDto.getDni() == null || customerDto.getEmail() == null) {
            throw new IllegalArgumentException("Todos los campos del cliente son obligatorios.");
        }

        if (customerRepository.findByDni(customerDto.getDni()).isPresent()) {
            throw new IllegalArgumentException("El DNI ya está registrado.");
        }

        if (!EMAIL_PATTERN.matcher(customerDto.getEmail()).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }

        Customer customer = Customer.builder()
                .firstSecondName(customerDto.getFirstSecondName())
                .lastName(customerDto.getLastName())
                .dni(customerDto.getDni())
                .email(customerDto.getEmail())
                .build();

        customer = customerRepository.save(customer);

        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .firstSecondName(customer.getFirstSecondName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .build();
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