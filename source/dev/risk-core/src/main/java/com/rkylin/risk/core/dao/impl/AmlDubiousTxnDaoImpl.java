package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.AmlDubiousTxnDao;
import com.rkylin.risk.core.entity.AmlDubiousTxn;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/17.
 */
@Repository
public class AmlDubiousTxnDaoImpl extends BaseDaoImpl<AmlDubiousTxn> implements AmlDubiousTxnDao {
  @Override
  public List<AmlDubiousTxn> queryAll(AmlDubiousTxn amlDubiousTxn) {
    return super.selectList("queryAll", amlDubiousTxn);
  }

  @Override
  public AmlDubiousTxn queryOne(Integer id) {
    return super.selectOne(id);
  }

  @Override
  public void insert(AmlDubiousTxn amlDubiousTxn) {
    super.add(amlDubiousTxn);
  }

  @Override
  public int update(AmlDubiousTxn amlDubiousTxn) {
    return super.modify(amlDubiousTxn);
  }

  @Override
  public void delete(Integer id) {
    super.del(id);
  }



  @Override
  public List querybycondition(Map map) {
    return super.selectList("exportBycondition", map);
  }
}
