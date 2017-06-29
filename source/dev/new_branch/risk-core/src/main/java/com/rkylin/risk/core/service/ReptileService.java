package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Reptile;

/**
 * Created by lina on 2016-8-5.
 */
public interface ReptileService {
  Reptile insert(Reptile reptile);

  Reptile queryByCheckorderid(String checkorderid, String queryType);

  void update(Reptile reptile);
}
