package com.oneil.insurance.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oneil.insurance.model.Quote;
import com.oneil.insurance.repository.QuoteRepository;
import com.oneil.insurance.strategy.PremiumContext;

class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private PremiumContext premiumContext;

    @InjectMocks
    private QuoteService quoteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateQuote() {
        // Arrange
        Quote expectedQuote = new Quote();
        expectedQuote.setPolicyType("health");
        expectedQuote.setPolicyHolder("John Doe");
        expectedQuote.setEstimatedPremium(1200);
        expectedQuote.setQuoteDate(LocalDate.now());

        when(premiumContext.calculatePremium(1000)).thenReturn(1200.0);
        when(quoteRepository.save(any(Quote.class))).thenReturn(expectedQuote); // Use any(Quote.class)

        // Act
        Quote result = quoteService.generateQuote("health", "John Doe", 1000);

        // Assert
        assertNotNull(result);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedQuote);

        verify(premiumContext, times(1)).setStrategy("health");
        verify(premiumContext, times(1)).calculatePremium(1000);

        ArgumentCaptor<Quote> quoteCaptor = ArgumentCaptor.forClass(Quote.class);
        verify(quoteRepository, times(1)).save(quoteCaptor.capture());
        assertThat(quoteCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedQuote); // verify the captured object.
    }
    @Test
    void simpleSaveTest(){
        Quote expectedQuote = new Quote();
        when(quoteRepository.save(expectedQuote)).thenReturn(expectedQuote);
        Quote result = quoteRepository.save(expectedQuote);
        assertEquals(expectedQuote, result);
    }
    @Test
    void testGetQuoteById() {
        // Arrange
        Quote quote = new Quote();
        quote.setId(1L);
        quote.setPolicyType("health");
        quote.setPolicyHolder("John Doe");
        quote.setEstimatedPremium(1200);
        quote.setQuoteDate(LocalDate.now());

        when(quoteRepository.findById(1L)).thenReturn(Optional.of(quote));

        // Act
        Quote result = quoteService.getQuoteById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("health", result.getPolicyType());
        assertEquals("John Doe", result.getPolicyHolder());
        assertEquals(1200, result.getEstimatedPremium());

        verify(quoteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuoteByIdNotFound() {
        // Arrange
        when(quoteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            quoteService.getQuoteById(1L);
        });
        assertEquals("Quote not found", exception.getMessage());

        verify(quoteRepository, times(1)).findById(1L);
    }
}