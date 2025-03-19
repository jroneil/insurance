package com.oneil.insurance.command;

import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {
    public void executeCommand(PolicyCommand command) {
        command.execute();
    }
}