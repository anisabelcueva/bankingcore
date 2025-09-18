package com.banking.core.customerms.service.impl;

import com.banking.core.customerms.dto.CustomerDto;
import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.mocks.MockUtils;
import com.banking.core.customerms.repository.CustomerRepository;
import com.banking.core.customerms.web.model.CustomerRequest;
import com.banking.core.customerms.web.model.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void getCustomer() {
        Customer customer = Customer.builder()
                .customerId(111)
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("18422233")
                .email("anisabel@gmail.com")
                .build();

        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(this.customerRepository.findById(any(Long.class))).thenReturn(optionalCustomer);

        CustomerResponse customerResponse = this.customerServiceImpl.getCustomer(111);

        assertThat(customerResponse).isNotNull();
        assertThat(customerResponse.getDni()).isEqualTo(customer.getDni());
    }

    @Test
    void getListCustomers() {
        when(this.customerRepository.findAll()).thenReturn(MockUtils.buildListCustomer());

        List<CustomerResponse> response =  this.customerServiceImpl.getListCustomers();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getFirstSecondName()).isEqualTo("Ana");
    }

    @Test
    void saveCustomer() {
        Customer customer = Customer.builder()
                .customerId(111)
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("18422233")
                .email("anisabel@gmail.com")
                .build();

        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstSecondName(customer.getFirstSecondName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .build();

        CustomerResponse customerResponse = this.customerServiceImpl.saveCustomer(customerRequest);

        assertThat(customerResponse).isNotNull();
        assertThat(customerResponse.getFirstSecondName()).isEqualTo(customerRequest.getFirstSecondName());
        assertThat(customerResponse.getLastName()).isEqualTo(customerRequest.getLastName());
        assertThat(customerResponse.getDni()).isEqualTo(customerRequest.getDni());
        assertThat(customerResponse.getEmail()).isEqualTo(customerRequest.getEmail());
    }

    @Test
    void updateCustomer() {
        Customer customer = Customer.builder()
                .customerId(111)
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("18422233")
                .email("anisabel@gmail.com")
                .build();

        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(this.customerRepository.findById(any(Long.class))).thenReturn(optionalCustomer);
        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstSecondName(customer.getFirstSecondName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .build();

        CustomerResponse customerResponse = this.customerServiceImpl.updateCustomer(customerRequest, 111);

        assertThat(customerResponse).isNotNull();
    }

    @Test
    void deleteCustomer() {
        Customer customer = Customer.builder()
                .customerId(111)
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("18422233")
                .email("anisabel@gmail.com")
                .build();

        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(this.customerRepository.findById(any(Long.class))).thenReturn(optionalCustomer);
        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse customerResponse = this.customerServiceImpl.deleteCustomer(customer.getCustomerId());

        assertThat(customerResponse).isNotNull();
    }

    @Test
    void findByDni() {
        Customer customer = Customer.builder()
                .customerId(111)
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("18422233")
                .email("anisabel@gmail.com")
                .build();

        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(this.customerRepository.findByDni(any(String.class))).thenReturn(optionalCustomer);

        Optional<CustomerDto> customerDto = this.customerServiceImpl.findByDni(customer.getDni());

        assertThat(customerDto).isNotNull();
    }
}