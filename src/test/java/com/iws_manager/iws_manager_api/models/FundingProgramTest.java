package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FundingProgramTest {

    @Test
    void testFundingProgramCreation() {
        FundingProgram fp = new FundingProgram();
        fp.setDefaultFundingRate(12.5);
        fp.setDefaultHoursPerYear(1800.0);
        fp.setDefaultResearchShare(30.0);
        fp.setDefaultStuffFlat(15.0);
        fp.setFundingProgram("ZIM");

        assertEquals(12.5, fp.getDefaultFundingRate());
        assertEquals(1800.0, fp.getDefaultHoursPerYear());
        assertEquals(30.0, fp.getDefaultResearchShare());
        assertEquals(15.0, fp.getDefaultStuffFlat());
        assertEquals("ZIM", fp.getFundingProgram());
    }

    @Test
    void testEqualsAndHashCode() {
        FundingProgram fp1 = new FundingProgram();
        fp1.setId(1L);
        fp1.setFundingProgram("ZIM");

        FundingProgram fp2 = new FundingProgram();
        fp2.setId(1L);
        fp2.setFundingProgram("ZIM");

        assertEquals(fp1, fp2);
        assertEquals(fp1.hashCode(), fp2.hashCode());
    }
}

