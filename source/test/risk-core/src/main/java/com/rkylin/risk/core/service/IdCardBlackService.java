package com.rkylin.risk.core.service;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.IdCardBlackList;

import java.util.List;

/**
 * Created by 201508031790 on 2015/9/19.
 */

public interface IdCardBlackService {

  IdCardBlackList addIdcardBlack(IdCardBlackList entity);

  IdCardBlackList updateIdcardBlack(IdCardBlackList entity);

  IdCardBlackList queryById(Integer id);

  IdCardBlackList queryByIdentnum(String num);

  void addBatch(List<IdCardBlackList> list, Authorization auth);

  String verifyBlackcard(String ids, IdCardBlackList entity, Authorization auth);

  String modifyBatch(String reason, String opertype, String ids, Authorization auth);
}
