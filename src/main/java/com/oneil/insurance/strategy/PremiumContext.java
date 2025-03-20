package com.oneil.insurance.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class PremiumContext {

   
    private final HealthInsurancePremiumStrategy healthInsurancePremiumStrategy;

    private final CarInsurancePremiumStrategy carInsurancePremiumStrategy;

    private final HomeInsurancePremiumStrategy homeInsurancePremiumStrategy;

    private  PremiumCalculationStrategy premiumCalculationStrategy;

    

	public PremiumContext(HealthInsurancePremiumStrategy healthInsurancePremiumStrategy,
			CarInsurancePremiumStrategy carInsurancePremiumStrategy,
			HomeInsurancePremiumStrategy homeInsurancePremiumStrategy) {
		super();
		this.healthInsurancePremiumStrategy = healthInsurancePremiumStrategy;
		this.carInsurancePremiumStrategy = carInsurancePremiumStrategy;
		this.homeInsurancePremiumStrategy = homeInsurancePremiumStrategy;
	}

	public void setStrategy(String policyType) {
        if (policyType == null) {
            throw new IllegalArgumentException("Policy type cannot be null");
        }
        log.info("(policyType.toLowerCase()"+policyType.toLowerCase());
        switch (policyType.toLowerCase()) {
       
            case "health":
            	log.info("I am in health");
                this.premiumCalculationStrategy = healthInsurancePremiumStrategy;
                break;
            case "car":
                this.premiumCalculationStrategy = carInsurancePremiumStrategy;
                break;
            case "home":
                this.premiumCalculationStrategy = homeInsurancePremiumStrategy;
                break;
            default:
            	log.info("Invalid policy type: " + policyType);
                throw new IllegalArgumentException("Invalid policy type: " + policyType);
        }
    }

    public double calculatePremium(double baseAmount) {
        if (premiumCalculationStrategy == null) {
            throw new IllegalStateException("Premium calculation strategy not set. Call setStrategy() first.");
        }
        return premiumCalculationStrategy.calculatePremium(baseAmount);
    }
}