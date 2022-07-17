package com.github.himeraoO.mrtb.command;

import com.github.himeraoO.mrtb.mrtbclient.MRGroupClient;
import com.github.himeraoO.mrtb.service.GroupSubService;
import com.github.himeraoO.mrtb.service.SendBotMessageService;
import com.github.himeraoO.mrtb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        MRGroupClient mrGroupClient = Mockito.mock(MRGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, mrGroupClient, groupSubService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/fgjhdfgdfg";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}