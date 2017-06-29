package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.CustomerbwgDao;
import com.rkylin.risk.core.dao.CustomerinfoDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.CustomebwgList;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.exception.RiskRestDataConflictException;
import com.rkylin.risk.core.service.CustomerbwgService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508031790 on 2015/9/7.
 */
@Service("customerbwgService")
public class CustomerbwgServiceImpl implements CustomerbwgService {

  @Resource
  private CustomerbwgDao customerbwgDao;
  @Resource
  private CustomerinfoDao customerinfoDao;

  @Override
  public void delCustbwg(String deleteIds, Authorization auth, String reason) {
    String[] idArray = deleteIds.split(",");
    for (int i = 0; i < idArray.length; i++) {
      CustomebwgList bwg = customerbwgDao.queryById(Integer.valueOf(idArray[i]));
      if (bwg != null) {
        bwg.setStatus(Constants.DELETE);
        bwg.setReason(reason);
        bwg.setUserid(auth.getUserId());
        bwg.setUsername(auth.getUsername());
        bwg.setUpdatetime(DateTime.now());
        customerbwgDao.update(bwg);
      } else {
        continue;
      }
    }
  }

  @Override
  public CustomebwgList addCustmebwg(CustomebwgList custbwg) {
    return customerbwgDao.addCustmebwg(custbwg);
  }

  @Override
  public String update(String ids, String opertype, String reason, Authorization auth) {
    String[] idArray = ids.split(",");
    for (int i = 0; i < idArray.length; i++) {
      CustomebwgList bwg = customerbwgDao.queryById(Integer.valueOf(idArray[i]));
      if (bwg != null) {
        if (opertype.equals("remove")) {
          bwg.setStatus(Constants.REMOVE_UNVERIFY);
          bwg.setReason(reason);
        } else if (opertype.equals("recover")) {
          bwg.setStatus(Constants.ACTIVE);
          bwg.setReason(reason);
        } else if (opertype.equals("time")) {
          bwg.setEffecttime(null);
          bwg.setFailuretime(null);
        } else {
          continue;
        }
        bwg.setUserid(auth.getUserId());
        bwg.setUsername(auth.getUsername());
        bwg.setUpdatetime(DateTime.now());
        customerbwgDao.update(bwg);
      }
    }

    return ids;
  }

  @Override
  public CustomebwgList queryById(Integer id) {
    return customerbwgDao.queryById(id);
  }

  @Override
  public CustomebwgList queryByCustomeridAndType(String customerid, String type) {
    return customerbwgDao.queryByCustomeridAndType(customerid, type);
  }

  @Override
  public List<CustomebwgList> queryAll() {
    return customerbwgDao.queryAll();
  }

  @Override
  public void addBWGFromCustom(String ids, CustomebwgList cusbwg, Authorization auth) {
    String[] idArray = ids.split(",");
    for (int i = 0; i < idArray.length; i++) {
      CustomebwgList bwgDB = customerbwgDao.queryByCustomerid(idArray[i]);
      if (bwgDB == null) {
        Customerinfo customer = customerinfoDao.queryOne(idArray[i]);
        if (customer != null) {
          CustomebwgList bwg = new CustomebwgList();
          bwg.setCustid(customer.getId());
          bwg.setCustomerid(customer.getCustomerid());
          bwg.setCustomername(customer.getCustomername());
          //bwg.setAccountnum((customer.getAccount() == null) ? null :
          // customer.getAccount().getAccountid());  无商户账户信息
          bwg.setOpendate(customer.getCreatetime());
          bwg.setSource(Constants.INNER);
          bwg.setStatus(Constants.ADD_UNVERIFY);
          if (cusbwg.getType().equals("black")) {
            bwg.setType(Constants.BLACK);
          } else if (cusbwg.getType().equals("white")) {
            bwg.setType(Constants.WHITE);
          } else if (cusbwg.getType().equals("gray")) {
            bwg.setType(Constants.GRAY);
          }
          bwg.setEffecttime(cusbwg.getEffecttime());
          bwg.setFailuretime(cusbwg.getFailuretime());
          bwg.setUserid(auth.getUserId());
          bwg.setUsername(auth.getUsername());
          bwg.setUpdatetime(DateTime.now());
          bwg.setCreatetime(DateTime.now());
          customerbwgDao.addCustmebwg(bwg);
        }
      } else {
        throw new RiskRestDataConflictException("客户[" + bwgDB.getCustomerid() + "]已存在于名单中，请确认！");
      }
    }
  }

  @Override
  public String verifybwg(String ids, CustomebwgList bwglist, Authorization auth) {
    String[] idArray = ids.split(",");
    for (int i = 0; i < idArray.length; i++) {
      CustomebwgList bwg = customerbwgDao.queryById(Integer.valueOf(idArray[i]));
      if (bwg != null && !bwg.getStatus().equals(Constants.ACTIVE)) {
        bwg.setStatus(
            (bwglist.getStatus().equals(Constants.ACTIVE)) ? Constants.ACTIVE : Constants.UNTHREAD);
        bwg.setReason(bwglist.getReason());
        bwg.setCheckid(auth.getUserId());
        bwg.setCheckname(auth.getUsername());
        bwg.setChecktime(DateTime.now());
        customerbwgDao.update(bwg);
      }
    }
    return ids;
  }
}
