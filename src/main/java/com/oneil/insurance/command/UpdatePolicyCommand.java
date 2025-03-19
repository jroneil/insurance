package com.oneil.insurance.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oneil.insurance.model.Policy;
import com.oneil.insurance.repository.PolicyRepository;
import com.oneil.insurance.strategy.PremiumContext;

@Component
public class UpdatePolicyCommand implements PolicyCommand {
    private Policy policy;
    private double baseAmount;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PremiumContext premiumContext;

    public void setPolicy(Policy policy, double baseAmount) {
        this.policy = policy;
        this.baseAmount = baseAmount;
    }

    @Override
    public void execute() {
        premiumContext.setStrategy(policy.getPolicyType());
        double premium = premiumContext.calculatePremium(baseAmount);
        policy.setPremium(premium);
        policyRepository.save(policy);
    }
}