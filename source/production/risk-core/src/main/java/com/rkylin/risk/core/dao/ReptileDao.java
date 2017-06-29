package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.Reptile;

/**
 * Created by lina on 2016-8-5.
 */
public interface ReptileDao {
  Reptile insert(Reptile reptile);

  Reptile queryByCheckorderid(String checkorderid, String queryType);

  void update(Reptile reptile);
}
