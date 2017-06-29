package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.ReptileDao;
import com.rkylin.risk.core.entity.Reptile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Created by lina on 2016-8-5.
 */
@Repository
public class ReptileDaoImpl extends BaseDaoImpl<Reptile> implements ReptileDao {
  @Override public Reptile insert(Reptile reptile) {
    super.add(reptile);
    return reptile;
  }

  @Override public Reptile queryByCheckorderid(String checkorderid, String querytype) {
    Map map = new HashMap();
    map.put("checkorderid", checkorderid);
    map.put("type", querytype);
    List<Reptile> reptiles = super.query("queryByCheckorderid", map);
    if (!reptiles.isEmpty()) {
      return reptiles.get(0);
    } else {
      return null;
    }
  }

  @Override public void update(Reptile reptile) {
    super.modify(reptile);
  }
}
