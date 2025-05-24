package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyTypeTest {

    CompanyType companyType = new CompanyType();
    String typeName = "Public";


    @Test
    void testCompanyTypeCreation(){
        //Act
        companyType.setName(typeName);

        //Assert
        assertEquals(typeName, companyType.getName());
    }

    @Test
    void testCompanyTypeWithAuditFields(){
        //Arrange 
        companyType.setName("Private");
        LocalDateTime now = LocalDateTime.now();

        //Act
        companyType.setCreatedAt(now);
        companyType.setUpdatedAt(now);

        //Assert
        assertEquals(now, companyType.getCreatedAt());
        assertEquals(now, companyType.getUpdatedAt());
    }
    
    @Test
    void testEqualsAndHashCode(){
        //Arrange
        companyType.setName(typeName);
        companyType.setId(1L);

        CompanyType companyType2 = new CompanyType();
        companyType2.setName(typeName);
        companyType2.setId(1L);

        //Assert
        assertEquals(companyType, companyType2);
        assertEquals(companyType.hashCode(), companyType2.hashCode());
    }
}
