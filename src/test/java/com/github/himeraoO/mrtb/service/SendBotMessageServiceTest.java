package com.github.himeraoO.mrtb.service;

import com.github.himeraoO.mrtb.bot.MobileReviewTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private MobileReviewTelegramBot mobileReviewTelegramBot;

    @BeforeEach
    public void init() {
        mobileReviewTelegramBot = Mockito.mock(MobileReviewTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(mobileReviewTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 111L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(mobileReviewTelegramBot).execute(sendMessage);
    }
}