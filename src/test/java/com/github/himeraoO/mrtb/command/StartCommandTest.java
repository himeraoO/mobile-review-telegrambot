package com.github.himeraoO.mrtb.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.himeraoO.mrtb.command.CommandName.START;
import static com.github.himeraoO.mrtb.command.StartCommand.START_MESSAGE;

@DisplayName("Unit-level testing for StartCommand")
class StartCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService);
    }
}