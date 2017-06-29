package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Group;

import java.util.List;

/**
 * Created by lina on 2016-7-11.
 */
public interface GroupDao {
    void delete(Short id);

    Group queryById(Short id);

    void update(Group group);

    Group addGroup(Group group);

    List<Group> queryAllGroup();

    Group queryByTimeAndStatus(String issueartifactid, String issuegroupid);
}
