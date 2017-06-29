package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.CaseDao;
import com.rkylin.risk.core.dao.CaseDubTxnDao;
import com.rkylin.risk.core.dao.CustomerbwgDao;
import com.rkylin.risk.core.dao.CustomerinfoDao;
import com.rkylin.risk.core.dao.DubioustxnDao;
import com.rkylin.risk.core.dao.IdCardBlackDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.entity.CaseDubTxn;
import com.rkylin.risk.core.entity.CustomebwgList;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.entity.IdCardBlackList;
import com.rkylin.risk.core.service.DubioustxnService;
import com.rkylin.risk.core.service.OperatorLogService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Created by v-wangwei on 2015/9/17 0017.
 */
@Service
public class DubioustxnServiceImpl implements DubioustxnService {
  @Resource
  DubioustxnDao dubioustxnDao;
  @Resource
  CustomerinfoDao customerinfoDao;
  @Resource
  private OperatorLogService operatorLogService;
  @Resource
  private IdCardBlackDao idCardBlackDao;
  @Resource
  private CustomerbwgDao customerbwgDao;
  @Resource
  private CaseDao caseDao;
  @Resource
  private CaseDubTxnDao caseDubTxnDao;

  @Override
  public Dubioustxn insert(Dubioustxn dubioustxn) {
    return dubioustxnDao.insert(dubioustxn);
  }

  @Override
  public Dubioustxn queryById(Integer id) {
    return dubioustxnDao.getById(id);
  }

  @Override
  public Dubioustxn queryByDictCode(String dictcode) {
    return null;
  }

  @Override
  public void update(Dubioustxn dubioustxn) {
    dubioustxnDao.update(dubioustxn);
  }

  @Override
  public List<Dubioustxn> queryAllDubioustxn(Dubioustxn dubioustxn) {
    return dubioustxnDao.queryAllDubioustxn(dubioustxn);
  }

  @Override
  public List<Dubioustxn> queryByCaseId(Integer caseid) {
    return dubioustxnDao.queryByCaseId(caseid);
  }

  @Override
  public List<Dubioustxn> queryAllDubioustxnMap(Dubioustxn dubioustxn, Authorization auth) {
    return dubioustxnDao.queryAllDubioustxnMap(dubioustxn, auth);
  }

  @Override
  public List<Dubioustxn> queryByRiskLevel(String riskLevel) {
    return dubioustxnDao.queryByRiskLevel(riskLevel);
  }

  @Override
  public void updateWarnstatus(String warnstatus, Integer id, Authorization auth) {
    String dealopinion = "";
    if (Constants.WARN_STATUS_01.equals(warnstatus)) {
      dealopinion = "关闭风险预警";
    }
    dubioustxnDao.updateWarnstatus(warnstatus, id, dealopinion);
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, "Dubioustxn", "处理可疑交易(通过/拒绝)");
  }

  /**
   * 添加身份证黑名单
   */
  @Override
  public void addIdCardBlackList(Integer id, Authorization auth) {
    Dubioustxn dubioustxn = dubioustxnDao.getById(id);
    //添加身份证黑名单
    Customerinfo customer = customerinfoDao.queryOne(dubioustxn.getCustomnum());
    IdCardBlackList idCardBlackList = new IdCardBlackList();
    idCardBlackList.setName(customer.getCustomername());
    idCardBlackList.setIdenttype(Constants.IDCARDTYPE);
    idCardBlackList.setIdentnum(customer.getCertificateid());
    idCardBlackList.setStatus(Constants.ACTIVE);
    idCardBlackList.setUserid(auth.getUserId());
    idCardBlackList.setUsername(auth.getUsername());
    idCardBlackDao.addIdcardBlack(idCardBlackList);

    //更新可疑交易处理意见
    dubioustxnDao.updateWarnstatus(Constants.WARN_STATUS_01, dubioustxn.getId(), "添加身份证黑名单");

    //添加操作日志
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, "IdCardBlackList", "加入身份证名单");
  }

  /**
   * 添加客户黑名单
   */
  @Override
  public void addCustomeBlackList(Integer id, Authorization auth) {
    Dubioustxn dubioustxn = dubioustxnDao.getById(id);

    //添加客户黑名单(客户信息暂时无法关联)
    //Customerinfo customer= customerinfoDao.queryOne(dubioustxn.getCustomnum());
    CustomebwgList customebwgList = new CustomebwgList();
    //customebwgList.setCustid(customer.getId());
    customebwgList.setCustomerid(dubioustxn.getCustomnum());
    customebwgList.setCustomername(dubioustxn.getCustomname());
    //customebwgList.setOpendate(customer.getCreatetime());
    customebwgList.setFailuretime(null);
    customebwgList.setEffecttime(null);
    customebwgList.setStatus(Constants.ACTIVE);
    customebwgList.setType(Constants.BLACK);
    customebwgList.setSource(Constants.ACTIVE);
    customebwgList.setUserid(auth.getUserId());
    customebwgList.setUsername(auth.getUsername());
    customebwgList.setReason(null);
    customerbwgDao.addCustmebwg(customebwgList);

    //更新可疑交易处理意见
    dubioustxnDao.updateWarnstatus(Constants.WARN_STATUS_01, dubioustxn.getId(), "添加客户黑名单");

    //添加操作日志
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, "CustomebwgList", "加入客户黑名单");
  }

  @Override
  public void addCase(Case cases, String addIds, Authorization auth) {
    String[] idsarray = addIds.split(",");

    //添加案例
    CaseDubTxn caseDubTxn = new CaseDubTxn();
    cases.setCasetype(Constants.MANUAL);
    cases.setStatus(Constants.ACTIVE);
    cases.setCreatetime(new DateTime());
    cases.setOperatorid(auth.getUserId());
    cases.setOperatorname(auth.getUsername());
    caseDao.insert(cases);

    //添加案例可疑交易关系表
    Integer caseId = cases.getId();
    for (int i = 0; i < idsarray.length; i++) {
      caseDubTxn.setCaseid(caseId);
      caseDubTxn.setTxnid(Integer.parseInt(idsarray[i]));
      //默认有效
      caseDubTxn.setStatus(Constants.ACTIVE);
      caseDubTxnDao.insert(caseDubTxn);
    }

    //添加操作日志
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, "IdCardBlackList", "可疑交易查询-添加案例");
  }

  @Override
  public List<Map> exportDubiousExcel(String productall,
      String[] products, String productnull,
      String customnum, String warnstatus, LocalDate beginWarnDate,
      LocalDate endWarnDate) {
    Map map = new HashMap();
    map.put("productall", productall);
    map.put("products", products);
    map.put("productnull", productnull);
    map.put("customnum", customnum);
    map.put("warnstatus", warnstatus);
    map.put("warndates", beginWarnDate);
    map.put("warndatee", endWarnDate);
    return dubioustxnDao.exportDubiousExcel(map);
  }

  @Override
  public Dubioustxn queryByTxnum(String orderId) {
    List<Dubioustxn> dubioustxns = dubioustxnDao.queryByTxnum(orderId);
    return (dubioustxns == null || dubioustxns.isEmpty()) ? null : dubioustxns.get(0);
  }
}
