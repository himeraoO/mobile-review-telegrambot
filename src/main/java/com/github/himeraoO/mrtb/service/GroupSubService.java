package com.github.himeraoO.mrtb.service;

import com.github.himeraoO.mrtb.mrtbclient.dto.GroupInfo;
import com.github.himeraoO.mrtb.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(Long chatId, GroupInfo groupInfo);

    GroupSub save(GroupSub groupSub);

    Optional<GroupSub> findById(Integer id);

    List<GroupSub> findAll();
}