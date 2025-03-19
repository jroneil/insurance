package com.oneil.insurance.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneil.insurance.command.CommandExecutor;
import com.oneil.insurance.command.CreatePolicyCommand;
import com.oneil.insurance.command.UpdatePolicyCommand;
import com.oneil.insurance.model.Policy;

@Service
public class PolicyService {

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private CreatePolicyCommand createPolicyCommand;

    @Autowired
    private UpdatePolicyCommand updatePolicyCommand;

    public void createPolicy(Policy policy, double baseAmount) {
        createPolicyCommand.setPolicy(policy, baseAmount);
        commandExecutor.executeCommand(createPolicyCommand);
    }

    public void updatePolicy(Policy policy, double baseAmount) {
        updatePolicyCommand.setPolicy(policy, baseAmount);
        commandExecutor.executeCommand(updatePolicyCommand);
    }
}