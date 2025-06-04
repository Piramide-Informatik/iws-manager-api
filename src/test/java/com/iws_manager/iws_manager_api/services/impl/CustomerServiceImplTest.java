package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.repositories.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer testCustomer;

    @BeforeEach
    public void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setCustomername1("Test Customer");
        testCustomer.setEmail1("test@example.com");
    }

    @Test
    public void createShouldSaveAndReturnCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer result = customerService.create(testCustomer);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    public void createShouldThrowExceptionWhenCustomerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> customerService.create(null));
    }

    @Test
    public void findByIdShouldReturnCustomerWhenExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Optional<Customer> result = customerService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Customer", result.get().getCustomername1());
    }

    @Test
    public void findAllShouldReturnAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(testCustomer, new Customer()));

        List<Customer> result = customerService.findAll();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void updateShouldUpdateCustomer() {
        Customer updatedDetails = new Customer();
        updatedDetails.setCustomername1("Updated Name");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer result = customerService.update(1L, updatedDetails);

        assertEquals("Updated Name", result.getCustomername1());
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    public void deleteShouldDeleteCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        
        customerService.delete(1L);
        
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteShouldThrowExceptionWhenCustomerNotExists() {
        when(customerRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> customerService.delete(1L));
    }
}