package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.FunctionDao;
import com.rkylin.risk.core.entity.Functions;
import com.rkylin.risk.core.service.FunctionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/12.
 */
@Service("functionService")
public class FunctionServiceImpl implements FunctionService {
  @Resource
  private FunctionDao functionDao;

  @Override
  public Functions insert(Functions function) {
    return functionDao.insert(function);
  }

  @Override
  public void delete(Short[] ids) {
    if (ids.length > 0) {
      List<Short> funIds = Arrays.asList(ids);
      for (Short id : funIds) {
        functionDao.delete(id);
      }
    }
  }

  @Override
  public void update(Functions function) {
    functionDao.update(function);
  }

  @Override
  public Functions queryOne(Short id) {
    return functionDao.queryOne(id);
  }

  @Override
  public List<Functions> query(Functions function) {
    return functionDao.query(function);
  }

  @Override
  public List<Functions> queryAll() {
    return functionDao.queryAll();
  }

  @Override
  public List<Functions> queryByRole(Short roleid) {
    return functionDao.queryFunByRole(roleid);
  }
}
