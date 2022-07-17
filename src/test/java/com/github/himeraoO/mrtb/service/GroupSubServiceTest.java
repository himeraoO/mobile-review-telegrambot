package com.github.himeraoO.mrtb.service;

import com.github.himeraoO.mrtb.mrtbclient.dto.GroupInfo;
import com.github.himeraoO.mrtb.repository.GroupSubRepository;
import com.github.himeraoO.mrtb.repository.entity.GroupSub;
import com.github.himeraoO.mrtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for GroupSubService")
public class GroupSubServiceTest {

    private GroupSubService groupSubService;
    private GroupSubRepository groupSubRepository;
    private TelegramUser newUser;

    private final static Long CHAT_ID = 1L;

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService = new GroupSubServiceImpl(groupSubRepository, telegramUserService);

        newUser = new TelegramUser();
        newUser.setActive(true);
        newUser.setChatId(CHAT_ID);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveGroup() {
        //given

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(1);
        groupInfo.setTitle("g1");

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupInfo.getId());
        expectedGroupSub.setTitle(groupInfo.getTitle());
        expectedGroupSub.addUser(newUser);

        //when
        groupSubService.save(CHAT_ID, groupInfo);

        //then
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }

    @Test
    public void shouldProperlyAddUserToExistingGroup() {
        //given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId(2L);
        oldTelegramUser.setActive(true);

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(1);
        groupInfo.setTitle("g1");

        GroupSub groupFromDB = new GroupSub();
        groupFromDB.setId(groupInfo.getId());
        groupFromDB.setTitle(groupInfo.getTitle());
        groupFromDB.addUser(oldTelegramUser);

        Mockito.when(groupSubRepository.findById(groupInfo.getId())).thenReturn(Optional.of(groupFromDB));

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupInfo.getId());
        expectedGroupSub.setTitle(groupInfo.getTitle());
        expectedGroupSub.addUser(oldTelegramUser);
        expectedGroupSub.addUser(newUser);

        //when
        groupSubService.save(CHAT_ID, groupInfo);

        //then
        Mockito.verify(groupSubRepository).findById(groupInfo.getId());
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }

}
