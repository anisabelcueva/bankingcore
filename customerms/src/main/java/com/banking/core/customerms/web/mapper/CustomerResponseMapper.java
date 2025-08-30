package com.banking.core.customerms.web.mapper;

import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.web.model.CustomerResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomerResponseMapper {



    public static List<CustomerResponse> buildListCustomerResponse(List<Customer> listCustomerRepository) {
        List<CustomerResponse> listCustomerResponse = new ArrayList<>();

        listCustomerRepository
                .stream()
                .forEach(row ->
                        listCustomerResponse.add(
                                CustomerResponse.builder()
                                        .firstSecondName(row.getFirstSecondName())
                                        .lastName(row.getLastName())
                                        .dni(row.getDni())
                                        .email(row.getEmail())
                                        .build()
                        )
                );

        return listCustomerResponse;
    }

    public static CustomerResponse buildCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .firstSecondName(customer.getFirstSecondName())
                .lastName(customer.getLastName())
                .dni(customer.getDni())
                .email(customer.getEmail())
                .build();
    }

}
