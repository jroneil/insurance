package com.oneil.insurance.strategy;

import org.springframework.stereotype.Component;

@Component
public interface PremiumCalculationStrategy {
    double calculatePremium(double baseAmount);
}