package com.oneil.insurance.command;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oneil.insurance.model.Quote;
import com.oneil.insurance.repository.QuoteRepository;
import com.oneil.insurance.strategy.PremiumContext;

@Component
public class GenerateQuoteCommand implements PolicyCommand {
    private String policyType;
    private String policyHolder;
    private double baseAmount;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private PremiumContext premiumContext;

    public void setDetails(String policyType, String policyHolder, double baseAmount) {
        this.policyType = policyType;
        this.policyHolder = policyHolder;
        this.baseAmount = baseAmount;
    }

    @Override
    public void execute() {
        premiumContext.setStrategy(policyType);
        double estimatedPremium = premiumContext.calculatePremium(baseAmount);

        Quote quote = new Quote();
        quote.setPolicyType(policyType);
        quote.setPolicyHolder(policyHolder);
        quote.setEstimatedPremium(estimatedPremium);
        quote.setQuoteDate(LocalDate.now());

        quoteRepository.save(quote);
    }
}