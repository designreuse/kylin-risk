package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.ModuelChannelDao;
import com.rkylin.risk.core.entity.ModuelsChannel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508240185 on 2015/10/12.
 */
@Repository
public class ModuelChannelDaoImpl extends BaseDaoImpl<ModuelsChannel> implements ModuelChannelDao {
  @Override
  public List<ModuelsChannel> queryByModuel(String moduelType) {
    return super.selectList("queryByModuel", moduelType);
  }

  @Override
  public void deleteByModule(String moduletype) {
    super.del("deleteByModule", moduletype);
  }

  @Override
  public void insert(ModuelsChannel moduelsChannel) {
    super.add(moduelsChannel);
  }

  @Override
  public ModuelsChannel update(ModuelsChannel moduelsChannel) {
    super.modify(moduelsChannel);
    return moduelsChannel;
  }
}
