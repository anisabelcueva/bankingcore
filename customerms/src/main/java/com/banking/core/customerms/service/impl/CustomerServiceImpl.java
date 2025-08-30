package com.banking.core.customerms.service.impl;

import com.banking.core.customerms.dto.CustomerDto;
import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.repository.CustomerRepository;
import com.banking.core.customerms.service.CustomerService;
import com.banking.core.customerms.web.mapper.CustomerResponseMapper;
import com.banking.core.customerms.web.model.CustomerRequest;
import com.banking.core.customerms.web.model.CustomerResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
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
    public CustomerResponse getCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return CustomerResponseMapper.buildCustomerResponse(customer.get());
        }
        return null;
    }

    @Override
    public List<CustomerResponse> getListCustomers() {
        return CustomerResponseMapper.buildListCustomerResponse(customerRepository.findAll());
    }

    @Override
    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {

        dataEntryValidations(customerRequest);

        Customer customer = customerRepository.save(Customer.builder()
                .firstSecondName(customerRequest.getFirstSecondName())
                .lastName(customerRequest.getLastName())
                .dni(customerRequest.getDni())
                .email(customerRequest.getEmail())
                .build());

        return CustomerResponseMapper.buildCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest, long customerId) {
        dataEntryValidations(customerRequest);

        Optional<Customer> customerToFind = customerRepository.findById(customerId);

        if (customerToFind.isPresent()) {
            Customer customerToUpdate = customerToFind.get();
            customerToUpdate.setFirstSecondName(customerRequest.getFirstSecondName());
            customerToUpdate.setLastName(customerRequest.getLastName());
            customerToUpdate.setEmail(customerRequest.getEmail());
            customerToUpdate.setDni(customerRequest.getDni());

            return CustomerResponseMapper.buildCustomerResponse(customerRepository.save(customerToUpdate));
        } else {
            return null;
        }
    }

    @Override
    public CustomerResponse deleteCustomer(long customerId) {

        Optional<Customer> customerToDelete = customerRepository.findById(customerId);

        if (customerToDelete.isEmpty()) {
            return null;
        }

        String names = customerToDelete.get().getFirstSecondName();
        String lastname = customerToDelete.get().getLastName();
        String dni = customerToDelete.get().getDni();

        customerRepository.deleteById(customerId);

        if (customerRepository.existsById(customerId)) {
            return null;
        }
        return CustomerResponse.builder()
                .firstSecondName(names)
                .lastName(lastname)
                .dni(dni)
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

    private void dataEntryValidations(CustomerRequest customerRequest) {
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
    }

}
