package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.FunctionDao;
import com.rkylin.risk.core.entity.Functions;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/12.
 */
@Repository("functionDao")
public class FunctionDaoImpl extends BaseDaoImpl<Functions> implements FunctionDao {
  @Override
  public Functions insert(Functions function) {
    super.add(function);
    return function;
  }

  @Override
  public void delete(Short id) {
    super.del(id);
  }

  @Override
  public void update(Functions function) {
    super.modify(function);
  }

  @Override
  public Functions queryOne(Short id) {
    return super.selectOne(id);
  }

  @Override
  public List<Functions> query(Functions function) {
    return super.selectList("query", function);
  }

  @Override
  public List<Functions> queryFunByRole(Short roleid) {
    return super.selectList("queryFunByRole", roleid);
  }

  @Override
  public List<Functions> queryAll() {
    return super.selectAllList();
  }
}

