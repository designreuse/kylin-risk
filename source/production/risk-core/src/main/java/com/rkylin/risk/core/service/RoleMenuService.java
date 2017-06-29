package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.RoleMenu;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
public interface RoleMenuService {
    List<RoleMenu> queryAll(Short roleid);

    void insert(RoleMenu roleMenu);

    void delete(Short roleid);
}
