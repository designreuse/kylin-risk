package com.rkylin.risk.core.service;

import com.rkylin.risk.core.dto.ChannelsBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Group;

import java.util.List;

/**
 * Created by lina on 2016-7-11.
 */
public interface GroupService {
    void delGroup(String ids);

    Group queryById(Short groupid);

    void updateGroup(Group group, ChannelsBean channels, Authorization auth);

    Group addGroup(Group group, ChannelsBean channels, Authorization auth);

    List<Group> queryAllGroup();

    Group queryByTimeAndStatus(String issueartifactid, String issuegroupid);


}
