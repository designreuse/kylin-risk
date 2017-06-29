package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.IdCardBlackDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.IdCardBlackList;
import com.rkylin.risk.core.exception.RiskRestDataConflictException;
import com.rkylin.risk.core.service.IdCardBlackService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/19.
 */
@Service("idCardBlackService")
public class IdCardBlackServiceImpl implements IdCardBlackService {

  @Resource
  private IdCardBlackDao idCardBlackDao;

  @Override
  public IdCardBlackList addIdcardBlack(IdCardBlackList entity) {
    return idCardBlackDao.addIdcardBlack(entity);
  }

  @Override
  public IdCardBlackList updateIdcardBlack(IdCardBlackList entity) {
    return idCardBlackDao.updateIdcardBlack(entity);
  }

  @Override
  public IdCardBlackList queryById(Integer id) {
    return idCardBlackDao.queryById(id);
  }

  @Override
  public IdCardBlackList queryByIdentnum(String num) {
    return idCardBlackDao.queryByIdentNum(num);
  }

  @Override
  public void addBatch(List<IdCardBlackList> list, Authorization auth) {
    for (int i = 0; i < list.size(); i++) {
      IdCardBlackList idcard = idCardBlackDao.queryByIdentNum(list.get(i).getIdentnum());
      if (idcard != null) {
        throw new RiskRestDataConflictException("身份证号[" + idcard.getIdentnum() + "]已经存在,请核实");
      } else {
        idcard = new IdCardBlackList();
        idcard.setName(list.get(i).getName());
        idcard.setIdenttype(Constants.IDCARDTYPE);
        idcard.setIdentnum(list.get(i).getIdentnum());
        idcard.setStatus(Constants.ADD_UNVERIFY);
        idcard.setUserid(auth.getUserId());
        idcard.setUsername(auth.getUsername());
        idcard.setCreatetime(DateTime.now());
        idCardBlackDao.addIdcardBlack(idcard);
      }
    }
  }

  @Override
  public String verifyBlackcard(String ids, IdCardBlackList idcard, Authorization auth) {
    String[] idArray = ids.split(",");
    for (int i = 0; i < idArray.length; i++) {
      IdCardBlackList idcardlist = idCardBlackDao.queryById(Integer.valueOf(idArray[i]));
      if (idcardlist != null && !idcardlist.getStatus().equals(Constants.ACTIVE)) {
        idcardlist.setStatus(idcard.getStatus());
        idcardlist.setReason(idcard.getReason());
        idcardlist.setCheckid(auth.getUserId());
        idcardlist.setCheckname(auth.getUsername());
        idcardlist.setChecktime(DateTime.now());
        idCardBlackDao.updateIdcardBlack(idcardlist);
      }
    }
    return ids;
  }

  @Override
  public String modifyBatch(String reason, String opertype, String ids, Authorization auth) {
    String[] idArray = ids.split(",");
    for (int i = 0; i < idArray.length; i++) {
      IdCardBlackList blackList = idCardBlackDao.queryById(Integer.valueOf(idArray[i]));
      if (blackList != null) {
        if ("remove".equals(opertype)) {
          blackList.setStatus(Constants.REMOVE_UNVERIFY);
          blackList.setReason(reason);
          blackList.setUserid(auth.getUserId());
          blackList.setUsername(auth.getUsername());
          idCardBlackDao.updateIdcardBlack(blackList);
        } else if ("delete".equals(opertype)) {
          blackList.setStatus(Constants.DELETE);
          blackList.setReason(reason);
          blackList.setUserid(auth.getUserId());
          blackList.setUsername(auth.getUsername());
          idCardBlackDao.updateIdcardBlack(blackList);
        }
      }
    }
    return ids;
  }
}
