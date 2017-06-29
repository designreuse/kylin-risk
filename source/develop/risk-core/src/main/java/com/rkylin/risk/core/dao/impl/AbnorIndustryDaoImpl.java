package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.AbnorIndustryDao;
import com.rkylin.risk.core.entity.AbnorIndustry;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201507270241 on 2015/8/28.
 */
@Repository("abnorIndustryDao")
public class AbnorIndustryDaoImpl extends BaseDaoImpl<AbnorIndustry> implements AbnorIndustryDao {
  @Override
  public AbnorIndustry queryOne(Integer id) {
    return super.queryOne(id);
  }

  @Override
  public List<AbnorIndustry> queryAll(AbnorIndustry abnorIndustry) {
    return super.query("queryAll", abnorIndustry);
  }

  @Override
  public void insert(AbnorIndustry abnorIndustry) {
    super.add(abnorIndustry);
  }

  @Override
  public void delete(Integer id) {
    super.del(id);
  }

  @Override
  public void update(AbnorIndustry abnorIndustry) {
    super.modify(abnorIndustry);
  }
}
