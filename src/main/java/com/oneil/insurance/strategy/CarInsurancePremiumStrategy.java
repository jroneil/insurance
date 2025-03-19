package com.oneil.insurance.strategy;

import org.springframework.stereotype.Component;

@Component
public class CarInsurancePremiumStrategy implements PremiumCalculationStrategy {
    @Override
    public double calculatePremium(double baseAmount) {
        return baseAmount * 1.1; // 10% higher for car insurance
    }
}