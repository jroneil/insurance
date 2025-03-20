package com.oneil.insurance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oneil.insurance.model.Policy;
import com.oneil.insurance.service.PolicyService;
import com.oneil.insurance.service.QuoteService;

class PolicyControllerValidationTest {

    private MockMvc mockMvc;

    @Mock
    private PolicyService policyService;

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private PolicyController policyController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(policyController)
                .setControllerAdvice(new AdviceController()) // Add this if you have a global exception handler
                .build();
        
        // Setup for successful case
        doNothing().when(policyService).createPolicy(any(Policy.class), anyDouble());
    }

    @Test
    void testCreatePolicy_WithValidData_ShouldSucceed() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePolicy_WithBlankPolicyType_ShouldFail() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType(""); // Blank policy type
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePolicy_WithNullPolicyType_ShouldFail() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType(null); // Null policy type
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePolicy_WithTooLongPolicyType_ShouldFail() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("a".repeat(51)); // 51 characters, exceeding the 50 character limit
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePolicy_WithBlankPolicyHolder_ShouldFail() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("health");
        policy.setPolicyHolder(""); // Blank policy holder
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePolicy_WithNullStartDate_ShouldFail() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(null); // Null start date
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePolicy_WithNullEndDate_ShouldFail() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(null); // Null end date

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isBadRequest());
    }
}
