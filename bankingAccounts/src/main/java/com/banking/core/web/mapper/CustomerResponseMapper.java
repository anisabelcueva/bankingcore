package com.banking.core.web.mapper;

import com.banking.core.entity.Customer;
import com.banking.core.web.model.CustomerResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomerResponseMapper {

    public static List<CustomerResponse> buildCustomerResponse (List<Customer> listCustomerRepository) {
        List<CustomerResponse> listCustomerResponse = new ArrayList<>();

        listCustomerRepository
                .stream()
                .forEach(vvvv ->
                        listCustomerResponse.add(
                                CustomerResponse.builder()
                                        .firsSecondName(vvvv.getFirsSecondName())
                                        .lastName(vvvv.getLastName())
                                        .dni(vvvv.getDni())
                                        .email(vvvv.getEmail())
                                        .build()
                        )
                );

        return listCustomerResponse;
    }

}
