package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApprovalStatusTest {

    private static final String NAME_APPROVED = "Approved";

    @Test
    public void testApprovalStatusCreation() {
        ApprovalStatus approvalStatus = new ApprovalStatus();
        approvalStatus.setStatus(NAME_APPROVED);
        approvalStatus.setForProjects((short)1);
        approvalStatus.setForNetworks((short)1);
        approvalStatus.setSequenceNo(1);

        assertEquals(NAME_APPROVED, approvalStatus.getStatus());
        assertEquals((short)1, approvalStatus.getForProjects() );
        assertEquals((short)1, approvalStatus.getForNetworks() );
        assertEquals(1, approvalStatus.getSequenceNo());
    }

    @Test
    public void testEqualsAndHashCode() {
        ApprovalStatus a1 = new ApprovalStatus();
        a1.setId(1L);
        a1.setStatus(NAME_APPROVED);

        ApprovalStatus a2 = new ApprovalStatus();
        a2.setId(1L);
        a2.setStatus(NAME_APPROVED);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testToStringContainsNameAndLabel(){
        ApprovalStatus approvalStatus = new ApprovalStatus();
        approvalStatus.setStatus(NAME_APPROVED);

        String toString = approvalStatus.toString();
        assertNotNull(toString);
        assertTrue(toString.contains(NAME_APPROVED));
    }
}
