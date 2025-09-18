package com.banking.core.customerms.mocks;

import com.banking.core.customerms.entity.Customer;
import com.banking.core.customerms.web.model.CustomerRequest;
import com.banking.core.customerms.web.model.CustomerResponse;

import java.util.Arrays;
import java.util.List;

public class MockUtils {

    public static List<Customer> buildListCustomer() {
        Customer firstCustomer = Customer.builder()
                .firstSecondName("Ana")
                .lastName("Cueva")
                .dni("23222211")
                .email("anita@gmail.com")
                .build();

        Customer secondCustomer = Customer.builder()
                .firstSecondName("Isabel")
                .lastName("Castillo")
                .dni("44454511")
                .email("isabel@gmail.com")
                .build();

        return Arrays.asList(firstCustomer, secondCustomer);
    }

    public static List<CustomerResponse> buildListCustomerResponse() {
        CustomerResponse firstCustomer = CustomerResponse.builder()
                .firstSecondName("Ana")
                .lastName("Cueva")
                .dni("23222211")
                .email("anita@gmail.com")
                .build();

        CustomerResponse secondCustomer = CustomerResponse.builder()
                .firstSecondName("Isabel")
                .lastName("Castillo")
                .dni("44454511")
                .email("isabel@gmail.com")
                .build();

        return Arrays.asList(firstCustomer, secondCustomer);
    }

    public static CustomerResponse buildCustomerResponse() {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("23222211")
                .email("anita@gmail.com")
                .build();
        return customerResponse;
    }

    public static CustomerRequest buildCustomerRequest() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstSecondName("Ana Isabel")
                .lastName("Cueva")
                .dni("23222211")
                .email("anita@gmail.com")
                .build();
        return customerRequest;
    }

}
