package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.RoleFunctionDao;
import com.rkylin.risk.core.entity.RoleFunction;
import com.rkylin.risk.core.service.RoleFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/21.
 */
@Service("roleFunctionService")
@Slf4j
public class RoleFunctionServiceImpl implements RoleFunctionService {

  @Resource
  private RoleFunctionDao roleFunctionDao;

  @Override
  public List<RoleFunction> queryAll(Short roleid) {
    return roleFunctionDao.queryAll(roleid);
  }

  @Override
  public void delete(Short roleid) {
    roleFunctionDao.delete(roleid);
  }

  @Override
  public void insert(RoleFunction roleFunction) {
    roleFunctionDao.insert(roleFunction);
  }
}
