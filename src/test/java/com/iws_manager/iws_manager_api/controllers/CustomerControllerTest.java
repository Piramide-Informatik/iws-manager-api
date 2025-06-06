package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.services.interfaces.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setCustomername1("Test Customer");
    }

    @Test
    void createCustomerShouldReturnCreated() {
        when(customerService.create(any(Customer.class))).thenReturn(testCustomer);

        ResponseEntity<?> response = customerController.createCustomer(testCustomer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testCustomer, response.getBody());
        verify(customerService, times(1)).create(testCustomer);
    }

    @Test
    void getCustomerByIdShouldReturnCustomer() {
        when(customerService.findById(1L)).thenReturn(Optional.of(testCustomer));

        ResponseEntity<Customer> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCustomer, response.getBody());
    }

    @Test
    void getCustomerByIdShouldReturnNotFound() {
        when(customerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllCustomersShouldReturnAllCustomers() {
        List<Customer> customers = Arrays.asList(testCustomer, new Customer());
        when(customerService.findAll()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateCustomerShouldReturnUpdatedCustomer() {
        Customer updatedDetails = new Customer();
        updatedDetails.setCustomername1("Updated Name");
        
        when(customerService.update(1L, updatedDetails)).thenReturn(testCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCustomer, response.getBody());
    }

    @Test
    void updateCustomerShouldReturnNotFound() {
        Customer updatedDetails = new Customer();
        when(customerService.update(1L, updatedDetails)).thenThrow(new RuntimeException());

        ResponseEntity<Customer> response = customerController.updateCustomer(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteCustomerShouldReturnNoContent() {
        doNothing().when(customerService).delete(1L);

        ResponseEntity<Void> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerService, times(1)).delete(1L);
    }
}