package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.AmlDubiousTxnDao;
import com.rkylin.risk.core.entity.AmlDubiousTxn;
import com.rkylin.risk.core.service.AmlDubiousTxnService;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 201507270241 on 2015/9/17.
 */
@Service
public class AmlDubiousTxnServiceImpl implements AmlDubiousTxnService {
  @Resource
  private AmlDubiousTxnDao amlDubiousTxnDao;

  @Override
  public List<AmlDubiousTxn> queryAll(AmlDubiousTxn amlDubiousTxn) {
    return amlDubiousTxnDao.queryAll(amlDubiousTxn);
  }

  @Override
  public AmlDubiousTxn queryOne(Integer id) {
    return amlDubiousTxnDao.queryOne(id);
  }

  @Override
  public void add(AmlDubiousTxn amlDubiousTxn) {
    amlDubiousTxnDao.insert(amlDubiousTxn);
  }

  @Override
  public void modify(AmlDubiousTxn amlDubiousTxn) {
    amlDubiousTxnDao.update(amlDubiousTxn);
  }

  @Override
  public void delete(Integer id) {
    amlDubiousTxnDao.delete(id);
  }

  @Override
  public List<Map> queryByCondition(AmlDubiousTxn adt, LocalDate begin, LocalDate end) {
    Map map = new HashMap();
    map.put("adt", adt);
    map.put("warndatebeg", begin);
    map.put("warndateend", end);
    return amlDubiousTxnDao.querybycondition(map);
  }
}
