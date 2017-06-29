package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.IdCardBlackDao;
import com.rkylin.risk.core.entity.IdCardBlackList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 201508031790 on 2015/9/19.
 */

@Repository("idCardBlackDao")
public class IdCardBlackDaoImpl extends BaseDaoImpl<IdCardBlackList> implements IdCardBlackDao {

  @Override
  public IdCardBlackList addIdcardBlack(IdCardBlackList entity) {
    super.add(entity);
    return entity;
  }

  @Override
  public IdCardBlackList updateIdcardBlack(IdCardBlackList entity) {
    super.modify(entity);
    return entity;
  }

  @Override
  public IdCardBlackList queryById(Integer id) {
    return super.get(id);
  }

  @Override
  public IdCardBlackList queryByIdentNum(String num) {
    List<IdCardBlackList> list = super.selectList("queryByIdentNum", num);
    if (!list.isEmpty()) {
      return list.get(0);
    } else {
      return null;
    }
  }
}
