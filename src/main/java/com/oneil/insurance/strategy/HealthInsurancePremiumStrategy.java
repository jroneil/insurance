package com.oneil.insurance.strategy;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class HealthInsurancePremiumStrategy implements PremiumCalculationStrategy {
    @Override
    public double calculatePremium(double baseAmount) {
    	
    	double prem= baseAmount * 1.2;
    	log.info("prem"+prem);
        return prem; // 20% higher for health insurance
    }
}