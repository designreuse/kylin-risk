package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.RoleFunction;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
public interface RoleFunctionService {

    List<RoleFunction> queryAll(Short roleid);

    void delete(Short roleid);

    void insert(RoleFunction roleFunction);
}
