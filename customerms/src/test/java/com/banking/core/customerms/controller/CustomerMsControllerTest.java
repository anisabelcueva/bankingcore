package com.banking.core.customerms.controller;

import com.banking.core.customerms.mocks.MockUtils;
import com.banking.core.customerms.service.impl.CustomerServiceImpl;
import com.banking.core.customerms.web.model.CustomerRequest;
import com.banking.core.customerms.web.model.CustomerResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerMsControllerTest {

    @InjectMocks CustomerMsController customerMsController;
    @Mock private CustomerServiceImpl customerServiceImpl;

    @Test
    void getCustomers() {
        Mockito.when(this.customerServiceImpl.getListCustomers())
                .thenReturn(MockUtils.buildListCustomerResponse());

        ResponseEntity<List<CustomerResponse>> response = this.customerMsController.getCustomers();

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(MockUtils.buildListCustomerResponse().size(), response.getBody().size());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCustomersNotContent() {
        Mockito.when(this.customerServiceImpl.getListCustomers())
                .thenReturn(List.of());

        ResponseEntity<List<CustomerResponse>> response = this.customerMsController.getCustomers();

        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void saveCustomer() {
        Mockito.when(this.customerServiceImpl.saveCustomer(any(CustomerRequest.class)))
                .thenReturn(MockUtils.buildCustomerResponse());

        ResponseEntity<CustomerResponse> response = this.customerMsController.saveCustomer(MockUtils.buildCustomerRequest());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getCustomer() {
        Mockito.when(this.customerServiceImpl.getCustomer(any(long.class)))
                .thenReturn(MockUtils.buildCustomerResponse());

        ResponseEntity<CustomerResponse> response = this.customerMsController.getCustomer(0L);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCustomerNoContent() {
        ResponseEntity<CustomerResponse> response = this.customerMsController.getCustomer(0L);

        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void updateCustomer() {
        CustomerResponse customerResponse = MockUtils.buildUpdateCustomerResponse();

        Mockito.when(this.customerServiceImpl.updateCustomer(any(CustomerRequest.class), any(long.class)))
                .thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = this.customerMsController.updateCustomer(0L, new CustomerRequest());

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getFirstSecondName(), customerResponse.getFirstSecondName());
        Assertions.assertEquals(response.getBody().getLastName(), customerResponse.getLastName());
        Assertions.assertEquals(response.getBody().getDni(), customerResponse.getDni());
        Assertions.assertEquals(response.getBody().getEmail(), customerResponse.getEmail());
    }

    @Test
    void updateCustomerNotFound() {
        CustomerResponse customerResponse = MockUtils.buildUpdateCustomerResponse();

        Mockito.when(this.customerServiceImpl.updateCustomer(any(CustomerRequest.class), any(long.class)))
                .thenReturn(null);

        ResponseEntity<CustomerResponse> response = this.customerMsController.updateCustomer(0L, new CustomerRequest());

        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteCustomer() {
        Mockito.when(this.customerServiceImpl.deleteCustomer(any(long.class)))
                .thenReturn(MockUtils.buildCustomerResponse());

        ResponseEntity<CustomerResponse> response = this.customerMsController.deleteCustomer(0L);

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void deleteCustomerNotFound() {
        ResponseEntity<CustomerResponse> response = this.customerMsController.deleteCustomer(0L);

        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}