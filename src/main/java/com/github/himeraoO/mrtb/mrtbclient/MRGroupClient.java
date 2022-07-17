package com.github.himeraoO.mrtb.mrtbclient;

import com.github.himeraoO.mrtb.mrtbclient.dto.GroupInfo;

import java.util.List;

/**
 * Client for Mobile-Review Groups.
 */
public interface MRGroupClient {

    List<GroupInfo> getGroupList();

    Integer getGroupCount();

    GroupInfo getGroupById(Integer id);
}
