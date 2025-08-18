package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class BasicContractTest {

    @Test
    void testBasicContractCreation() {
        // Arrange
        LocalDate confirmationDate = LocalDate.now();
        String contractLabel = "CON-2023-001";
        Integer contractNo = 12345;
        String contractTitle = "Annual Maintenance Contract";
        LocalDate date = LocalDate.now().minusDays(10);
        
        ContractStatus contractStatus = new ContractStatus();
        Customer customer = new Customer();
        FundingProgram fundingProgram = new FundingProgram();
        
        // Act
        BasicContract contract = new BasicContract();
        contract.setConfirmationDate(confirmationDate);
        contract.setContractLabel(contractLabel);
        contract.setContractNo(contractNo);
        contract.setContractStatus(contractStatus);
        contract.setContractTitle(contractTitle);
        contract.setCustomer(customer);
        contract.setDate(date);
        contract.setFundingProgram(fundingProgram);
        
        // Assert
        assertEquals(confirmationDate, contract.getConfirmationDate());
        assertEquals(contractLabel, contract.getContractLabel());
        assertEquals(contractNo, contract.getContractNo());
        assertEquals(contractStatus, contract.getContractStatus());
        assertEquals(contractTitle, contract.getContractTitle());
        assertEquals(customer, contract.getCustomer());
        assertEquals(date, contract.getDate());
        assertEquals(fundingProgram, contract.getFundingProgram());
    }

    @Test
    void testBasicContractWithAuditFields() {
        // Arrange
        BasicContract contract = new BasicContract();
        contract.setContractLabel("CON-2023-002");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        contract.setCreatedAt(now);
        contract.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, contract.getCreatedAt());
        assertEquals(now, contract.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        BasicContract contract1 = new BasicContract();
        contract1.setId(1L);
        contract1.setContractNo(1001);
        contract1.setContractTitle("Service Agreement");
        
        BasicContract contract2 = new BasicContract();
        contract2.setId(1L);
        contract2.setContractNo(1001);
        contract2.setContractTitle("Service Agreement");
        
        BasicContract contract3 = new BasicContract();
        contract3.setId(2L); // Different ID
        contract3.setContractNo(1001);
        contract3.setContractTitle("Service Agreement");
        
        // Assert
        assertEquals(contract1, contract2);
        assertEquals(contract1.hashCode(), contract2.hashCode());
        assertNotEquals(contract1, contract3);
        assertNotEquals(contract1.hashCode(), contract3.hashCode());
    }
}