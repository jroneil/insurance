package com.oneil.insurance.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oneil.insurance.model.Quote;
import com.oneil.insurance.repository.QuoteRepository;
import com.oneil.insurance.strategy.PremiumContext;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class QuoteService {

	  @Autowired
	    private PremiumContext premiumContext;

	    @Autowired
	    private QuoteRepository quoteRepository;

	    public Quote generateQuote(String policyType, String policyHolder, double baseAmount) {
		    log.info("I am her");
	    	log.info("policyType"+policyType); 
	    	if (policyType == null || policyType.trim().isEmpty()) {
	            log.warn("Policy type is null or empty. Returning Bad Request.");
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Policy type is required");
	        }

	        try {
	            premiumContext.setStrategy(policyType);
	            log.info("Setting strategy for policy type: {}", policyType); // Use placeholders for logging
	            double estimatedPremium = premiumContext.calculatePremium(baseAmount);
	            log.info("Calculated estimated premium: {}", estimatedPremium);

	            Quote quote = new Quote();
	            quote.setPolicyType(policyType);
	            quote.setPolicyHolder(policyHolder);
	            quote.setEstimatedPremium(estimatedPremium);
	            quote.setQuoteDate(LocalDate.now());
	            log.info("Created quote: {}", quote);

	            Quote savedQuote = quoteRepository.save(quote);
	            log.info("Saved quote: {}", savedQuote);
	            return savedQuote;

	        } catch (NullPointerException e) {
	            log.error("NullPointerException during premium calculation.", e);
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error processing premium calculation", e);
	        } catch (Exception e) { // Catch other potential exceptions
	            log.error("An unexpected error occurred during quote generation.", e);
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
	        }
	    }


	    public Quote getQuoteById(Long id) {
	        return quoteRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Quote not found"));
	    }
}