package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.ModuelsChannel;

import java.util.List;

/**
 * Created by 201508240185 on 2015/10/12.
 */
public interface ModuelChannelDao {
  void insert(ModuelsChannel moduelsChannel);

  ModuelsChannel update(ModuelsChannel moduelsChannel);

  List<ModuelsChannel> queryByModuel(String moduelType);

  void deleteByModule(String moduletype);
}
