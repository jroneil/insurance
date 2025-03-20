package com.oneil.insurance.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PremiumContextTest {

    @Autowired
    private PremiumContext premiumContext;

    @Mock
    private HealthInsurancePremiumStrategy healthInsuranceStrategy;

    @Mock
    private CarInsurancePremiumStrategy carInsuranceStrategy;

    @Mock
    private HomeInsurancePremiumStrategy homeInsuranceStrategy;

    @BeforeEach
    void setUp() {
        when(healthInsuranceStrategy.calculatePremium(1000)).thenReturn(1200.0);
        when(carInsuranceStrategy.calculatePremium(1000)).thenReturn(1100.0);
        when(homeInsuranceStrategy.calculatePremium(1000)).thenReturn(1150.0);
    }

    @Test
    void testHealthInsurancePremium() {
        premiumContext.setStrategy("health");
        double premium = premiumContext.calculatePremium(1000);
        assertEquals(1200, premium);
    }

    @Test
    void testCarInsurancePremium() {
        premiumContext.setStrategy("car");
        double premium = premiumContext.calculatePremium(1000);
        assertEquals(1100, premium);
    }

    @Test
    void testHomeInsurancePremium() {
        premiumContext.setStrategy("home");
        double premium = premiumContext.calculatePremium(1000);
        assertEquals(1150, premium);
    }

    @Test
    void testInvalidStrategy() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            premiumContext.setStrategy("invalid");
        });
        assertEquals("Invalid policy type: invalid", exception.getMessage());
    }
}
