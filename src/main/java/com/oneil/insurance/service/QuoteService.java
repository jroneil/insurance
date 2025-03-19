package com.oneil.insurance.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	        // Set the strategy based on the policy type
	        premiumContext.setStrategy(policyType);
	        log.info("-------------1");
	        // Calculate the premium
	        double estimatedPremium = premiumContext.calculatePremium(baseAmount);
	        log.info("------------- estimatedPremium="+estimatedPremium);
	        // Create and save the quote
	        Quote quote = new Quote();
	        quote.setPolicyType(policyType);
	        quote.setPolicyHolder(policyHolder);
	        quote.setEstimatedPremium(estimatedPremium);
	        quote.setQuoteDate(LocalDate.now());
	        log.info("------------- estimatedPremium="+quote.getEstimatedPremium());
	        Quote savedQuote=quoteRepository.save(quote);
	        System.out.println("Saved Quote: " + savedQuote); 
	        return savedQuote;
	    }

	    public Quote getQuoteById(Long id) {
	        return quoteRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Quote not found"));
	    }
}