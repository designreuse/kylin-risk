package com.rkylin.risk.core.dao;

import com.rkylin.risk.core.entity.IdCardBlackList;

/**
 * Created by 201508031790 on 2015/9/19.
 */
public interface IdCardBlackDao {

  IdCardBlackList addIdcardBlack(IdCardBlackList entity);

  IdCardBlackList updateIdcardBlack(IdCardBlackList entity);

  IdCardBlackList queryById(Integer id);

  IdCardBlackList queryByIdentNum(String num);
}
