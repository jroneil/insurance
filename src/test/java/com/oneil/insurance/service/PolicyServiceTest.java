package com.oneil.insurance.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oneil.insurance.command.CommandExecutor;
import com.oneil.insurance.command.CreatePolicyCommand;
import com.oneil.insurance.command.UpdatePolicyCommand;
import com.oneil.insurance.model.Policy;
import com.oneil.insurance.repository.PolicyRepository;

class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private CreatePolicyCommand createPolicyCommand;

    @Mock
    private UpdatePolicyCommand updatePolicyCommand;

    @Mock
    private CommandExecutor commandExecutor;

    @InjectMocks
    private PolicyService policyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePolicy() {
        // Arrange
        Policy policy = new Policy();
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        doNothing().when(createPolicyCommand).setPolicy(policy, 1000);
        doNothing().when(commandExecutor).executeCommand(createPolicyCommand);

        // Act
        policyService.createPolicy(policy, 1000);

        // Assert
        verify(createPolicyCommand, times(1)).setPolicy(policy, 1000);
        verify(commandExecutor, times(1)).executeCommand(createPolicyCommand);
    }

    @Test
    void testUpdatePolicy() {
        // Arrange
        Policy policy = new Policy();
        policy.setId(1L);
        policy.setPolicyType("health");
        policy.setPolicyHolder("John Doe");
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        doNothing().when(updatePolicyCommand).setPolicy(policy, 1200);
        doNothing().when(commandExecutor).executeCommand(updatePolicyCommand);

        // Act
        policyService.updatePolicy(policy, 1200);

        // Assert
        verify(updatePolicyCommand, times(1)).setPolicy(policy, 1200);
        verify(commandExecutor, times(1)).executeCommand(updatePolicyCommand);
    }
}