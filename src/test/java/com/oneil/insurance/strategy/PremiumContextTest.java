package com.oneil.insurance.strategy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PremiumContextTest {
	@Autowired
    private PremiumContext premiumContext;

    @Test
    void testHealthInsurancePremium() {
    	
        premiumContext.setStrategy("health");
        double premium = premiumContext.calculatePremium(1000);
        assertEquals(1200, premium); // 20% higher
    }

    @Test
    void testCarInsurancePremium() {
        premiumContext.setStrategy("car");
        double premium = premiumContext.calculatePremium(1000);
        assertEquals(1100, premium); // 10% higher
    }

    @Test
    void testHomeInsurancePremium() {
        premiumContext.setStrategy("home");
        double premium = premiumContext.calculatePremium(1000);
        assertEquals(1150, premium); // 15% higher
    }

    @Test
    void testInvalidStrategy() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            premiumContext.setStrategy("invalid");
        });
        assertEquals("Invalid policy type: invalid", exception.getMessage()); 
    }
}
