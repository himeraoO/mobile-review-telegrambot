package com.github.himeraoO.mrtb.command;

import com.github.himeraoO.mrtb.mrtbclient.MRGroupClient;
import com.github.himeraoO.mrtb.mrtbclient.dto.GroupInfo;
import com.github.himeraoO.mrtb.repository.entity.GroupSub;
import com.github.himeraoO.mrtb.service.GroupSubService;
import com.github.himeraoO.mrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.himeraoO.mrtb.command.CommandName.ADD_GROUP_SUB;
import static com.github.himeraoO.mrtb.command.CommandUtils.getChatId;
import static com.github.himeraoO.mrtb.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Add Group subscription {@link Command}.
 */
public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final MRGroupClient mrGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, MRGroupClient mrGroupClient,
                              GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.mrGroupClient = mrGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            GroupInfo groupById = mrGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendNotValidGroupID(chatId, groupId);
        }
    }

    private void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendNotValidGroupID(Long chatId, String groupId) {
        String groupNotFoundMessage = "Неправильный ID группы = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(Long chatId) {
        String groupIds = mrGroupClient.getGroupList().stream().map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай команду вместе с ID группы. \n" +
                "Например: /addgroupsub 1. \n\n" +
                "я подготовил список всех групп - выбирай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}
