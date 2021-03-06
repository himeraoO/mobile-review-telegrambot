package com.github.himeraoO.mrtb.bot;

import com.github.himeraoO.mrtb.command.CommandContainer;
import com.github.himeraoO.mrtb.mrtbclient.MRGroupClient;
import com.github.himeraoO.mrtb.service.GroupSubService;
import com.github.himeraoO.mrtb.service.SendBotMessageServiceImpl;
import com.github.himeraoO.mrtb.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.himeraoO.mrtb.command.CommandName.NO;

/**
 * Telegrambot for Mobile-review.com for me
 */
@Component
public class MobileReviewTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    @Autowired
    public MobileReviewTelegramBot(TelegramUserService telegramUserService, MRGroupClient mrGroupClient, GroupSubService groupSubService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService, mrGroupClient, groupSubService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}

