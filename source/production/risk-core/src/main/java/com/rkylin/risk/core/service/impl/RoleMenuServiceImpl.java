package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.RoleMenuDao;
import com.rkylin.risk.core.entity.RoleMenu;
import com.rkylin.risk.core.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
@Service("roleMenuService")
@Slf4j
public class RoleMenuServiceImpl implements RoleMenuService {
    @Resource
    private RoleMenuDao roleMenuDao;

    @Override
    public List<RoleMenu> queryAll(Short roleid) {
        return roleMenuDao.queryAll(roleid);
    }

    @Override
    public void insert(RoleMenu roleMenu) {
        roleMenuDao.insert(roleMenu);
    }

    @Override
    public void delete(Short roleid) {
        roleMenuDao.delete(roleid);
    }
}
