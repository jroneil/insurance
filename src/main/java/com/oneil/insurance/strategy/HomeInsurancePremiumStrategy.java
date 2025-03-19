package com.oneil.insurance.strategy;

import org.springframework.stereotype.Component;

@Component
public class HomeInsurancePremiumStrategy implements PremiumCalculationStrategy {
    @Override
    public double calculatePremium(double baseAmount) {
        return baseAmount * 1.15; // 15% higher for home insurance
    }
}