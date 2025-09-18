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

        ResponseEntity<CustomerResponse> response = this.customerMsController.getCustomer(111);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCustomer_NoContent() {
        ResponseEntity<CustomerResponse> response = this.customerMsController.getCustomer(111);

        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void updateCustomer() {

    }

    @Test
    void deleteCustomer() {
    }
}