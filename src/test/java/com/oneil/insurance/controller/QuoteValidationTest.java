package com.oneil.insurance.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oneil.insurance.model.Quote;
import com.oneil.insurance.service.QuoteService;

class QuoteValidationTest {

    private MockMvc mockMvc;

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private PolicyController policyController;

    @SuppressWarnings("unused")
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(policyController)
                .setControllerAdvice(new AdviceController()) // Add this if you have a global exception handler
                .build();
    }

    @Test
    void testGenerateQuote_WithValidData_ShouldSucceed() throws Exception {
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
                .andExpect(status().isOk());
    }

    @Test
    void testGenerateQuote_WithBlankPolicyType_ShouldFail() throws Exception {
        // Act & Assert
    	String polyType="";
        mockMvc.perform(post("/api/v1/policies/quotes/generate")
                .param("policyType", polyType.trim()) // Blank policy type
                .param("policyHolder", "John Doe")
                .param("baseAmount", "1000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGenerateQuote_WithNullPolicyType_ShouldFail() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/quotes/generate")
                .param("policyHolder", "John Doe")
                .param("baseAmount", "1000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGenerateQuote_WithBlankPolicyHolder_ShouldFail() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/quotes/generate")
                .param("policyType", "health")
                .param("policyHolder", "") // Blank policy holder
                .param("baseAmount", "1000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGenerateQuote_WithNegativeBaseAmount_ShouldFail() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/policies/quotes/generate")
                .param("policyType", "health")
                .param("policyHolder", "John Doe")
                .param("baseAmount", "-1000")) // Negative amount
                .andExpect(status().isBadRequest());
    }
}