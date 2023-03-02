package com.github.himeraoO.mrtb.service;

import com.github.himeraoO.mrtb.mrtbclient.MRPostClient;
import com.github.himeraoO.mrtb.mrtbclient.dto.PostInfo;
import com.github.himeraoO.mrtb.repository.entity.GroupSub;
import com.github.himeraoO.mrtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewPostServiceImpl implements FindNewPostService {

    private final GroupSubService groupSubService;
    private final MRPostClient mrPostClient;
    private final SendBotMessageService sendMessageService;

    @Autowired
    public FindNewPostServiceImpl(GroupSubService groupSubService,
                                  MRPostClient mrPostClient,
                                  SendBotMessageService sendMessageService) {
        this.groupSubService = groupSubService;
        this.mrPostClient = mrPostClient;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void findNewPosts() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = mrPostClient.findNewPosts(gSub.getId(), gSub.getLastArticleId());
            setNewLastPostId(gSub, newPosts);
            notifySubscribersAboutNewPosts(gSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewPosts(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                                "<b>Описание:</b> %s\n\n" +
                                "<b>Ссылка:</b> %s\n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(), post.getLink()))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(telegramUser -> sendMessageService.sendMessage(telegramUser.getChatId(), messagesWithNewArticles));
    }

    private void setNewLastPostId(GroupSub gSub, List<PostInfo> newPosts) {
        if(CollectionUtils.isEmpty(newPosts)){
            return;
        }

        PostInfo postInfo = newPosts.get(0);
        gSub.setLastArticleId(postInfo.getId());
        groupSubService.save(gSub);
    }
}
