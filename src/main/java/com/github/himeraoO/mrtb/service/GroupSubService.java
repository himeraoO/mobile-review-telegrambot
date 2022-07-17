package com.github.himeraoO.mrtb.service;

import com.github.himeraoO.mrtb.mrtbclient.dto.GroupInfo;
import com.github.himeraoO.mrtb.repository.entity.GroupSub;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(Long chatId, GroupInfo groupInfo);
}