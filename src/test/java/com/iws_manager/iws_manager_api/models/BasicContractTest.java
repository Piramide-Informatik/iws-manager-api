package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class BasicContractTest {

    private static final Long TEST_ID = 1L;
    private static final Integer CONTRACT_NO = 1001;
    private static final String CONTRACT_TITLE = "Service Agreement";

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
        contract1.setId(TEST_ID);
        contract1.setContractNo(CONTRACT_NO);
        contract1.setContractTitle(CONTRACT_TITLE);
        
        BasicContract contract2 = new BasicContract();
        contract2.setId(TEST_ID);
        contract2.setContractNo(CONTRACT_NO);
        contract2.setContractTitle(CONTRACT_TITLE);
        
        BasicContract contract3 = new BasicContract();
        contract3.setId(2L); 
        contract3.setContractNo(CONTRACT_NO);
        contract3.setContractTitle(CONTRACT_TITLE);
        
        // Assert
        assertEquals(contract1, contract2);
        assertEquals(contract1.hashCode(), contract2.hashCode());
        assertNotEquals(contract1, contract3);
        assertNotEquals(contract1.hashCode(), contract3.hashCode());
    }
}