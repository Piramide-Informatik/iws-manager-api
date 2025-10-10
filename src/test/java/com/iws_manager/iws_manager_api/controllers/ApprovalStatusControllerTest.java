package com.iws_manager.iws_manager_api.controllers;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.ApprovalStatus;
import com.iws_manager.iws_manager_api.services.interfaces.ApprovalStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ApprovalStatusControllerTest {
    private static final String FIRST_NAME_STATUS = "Approved";
    private static final String UPDATE_STATUS = "Approved updated";
    private static final String SECOND_NAME_STATUS = "Rejected";
    private MockMvc mockMvc;
    private String uri = "/api/v1/approvalstatuses";
    private String status = "$.status";

    @Mock
    private ApprovalStatusService approvalStatusService;

    @InjectMocks
    private ApprovalStatusController approvalStatusController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ApprovalStatus approvalStatus1;
    private ApprovalStatus approvalStatus2;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(approvalStatusController).build();

        approvalStatus1 = new ApprovalStatus();
        approvalStatus1.setId(1L);
        approvalStatus1.setStatus(FIRST_NAME_STATUS);

        approvalStatus2 = new ApprovalStatus();
        approvalStatus2.setId(2L);
        approvalStatus2.setStatus(SECOND_NAME_STATUS);
    }

    @Test
    void createApprovalStatusShouldReturnCreatedStatus() throws Exception {
        given(approvalStatusService.create(any(ApprovalStatus.class))).willReturn(approvalStatus1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(approvalStatus1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(status).value(FIRST_NAME_STATUS));
    }

    @Test
    void getApprovalStatusByIdShouldReturnApprovalStatus() throws Exception {
        given(approvalStatusService.findById(1L)).willReturn(Optional.of(approvalStatus1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(status).value(FIRST_NAME_STATUS));
    }

    @Test
    void getApprovalStatusByIdShouldReturnNotFound() throws Exception {
        given(approvalStatusService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllApprovalStatusShouldReturnAllApprovalStatus() throws Exception {
        List<ApprovalStatus> approvalStatuses = Arrays.asList(approvalStatus1, approvalStatus2);
        given(approvalStatusService.findAll()).willReturn(approvalStatuses);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].status").value(FIRST_NAME_STATUS))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].status").value(SECOND_NAME_STATUS));
    }

    @Test
    void updateApprovalStatusShouldReturnUpdatedApprovalStatus() throws Exception {
        ApprovalStatus updatedApprovalStatus = new ApprovalStatus();
        updatedApprovalStatus.setStatus(UPDATE_STATUS);

        given(approvalStatusService.update(1L, updatedApprovalStatus)).willReturn(updatedApprovalStatus);

        mockMvc.perform(put(uri+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedApprovalStatus)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(status).value(UPDATE_STATUS));
    }

    @Test
    void updateApprovalStatusShouldReturnNotFound() throws Exception {
        given(approvalStatusService.update(anyLong(),any(ApprovalStatus.class)))
                .willThrow(new RuntimeException("ApprovalStatus not found"));

        mockMvc.perform(put(uri+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(approvalStatus1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteApprovalStatusShouldReturnNoContent() throws Exception {
        doNothing().when(approvalStatusService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createAbsenceTypeShouldReturnCreatedContractor() throws Exception {
        ApprovalStatus validApprovalStatus = new ApprovalStatus();
        validApprovalStatus.setStatus(FIRST_NAME_STATUS);

        when(approvalStatusService.create(any(ApprovalStatus.class))).thenReturn(validApprovalStatus);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validApprovalStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(status).value(FIRST_NAME_STATUS));
    }
}
