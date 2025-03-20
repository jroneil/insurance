package com.oneil.insurance.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.oneil.insurance.model.Quote;
import com.oneil.insurance.service.PolicyService;
import com.oneil.insurance.service.QuoteService;

class PolicyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PolicyService policyService;

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private PolicyController policyController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(policyController).build();
    }

    @Test
    void testCreatePolicy() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Use doNothing() for void methods
        doNothing().when(policyService).createPolicy(any(Policy.class), anyDouble());

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/create")
                .param("baseAmount", "1000.00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isOk())
                .andExpect(content().string("Policy created successfully!"));
    }

    @Test
    void testUpdatePolicy() throws Exception {
        // Arrange
        Policy policy = new Policy();
        policy.setId(1L);
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        // Use doNothing() for void methods
        doNothing().when(policyService).updatePolicy(any(Policy.class), anyDouble());

        // Act & Assert
        mockMvc.perform(put("/api/v1/policies/update")
                .param("baseAmount", "1200")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(policy)))
                .andExpect(status().isOk())
                .andExpect(content().string("Policy updated successfully!"));
    }

    @Test
    void testGenerateQuote() throws Exception {
        // Arrange
        Quote quote = new Quote();
        quote.setId(1L);
        quote.setPolicyType("health");
        quote.setPolicyHolder("John Doe");
        quote.setEstimatedPremium(1200.0);
        quote.setQuoteDate(LocalDate.now());

        when(quoteService.generateQuote(anyString(), anyString(), anyDouble())).thenReturn(quote);

        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/quotes/generate")
                .param("policyType", "health")
                .param("policyHolder", "John Doe")
                .param("baseAmount", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.policyType").value("health"))
                .andExpect(jsonPath("$.policyHolder").value("John Doe"))
                .andExpect(jsonPath("$.estimatedPremium").value(1200.0));
    }

    @Test
    void testGetQuoteById() throws Exception {
        // Arrange
        Quote quote = new Quote();
        quote.setId(1L);
        quote.setPolicyType("health");
        quote.setPolicyHolder("John Doe");
        quote.setEstimatedPremium(1200.0);
        quote.setQuoteDate(LocalDate.now());

        when(quoteService.getQuoteById(anyLong())).thenReturn(quote);

        // Act & Assert
        mockMvc.perform(get("/api/v1/policies/quotes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.policyType").value("health"))
                .andExpect(jsonPath("$.policyHolder").value("John Doe"))
                .andExpect(jsonPath("$.estimatedPremium").value(1200.0));
    }

    @Test
    void testGetQuoteByIdNotFound() throws Exception {
        // Arrange
        when(quoteService.getQuoteById(anyLong())).thenThrow(new RuntimeException("Quote not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/policies/quotes/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Quote not found"));
    }
}