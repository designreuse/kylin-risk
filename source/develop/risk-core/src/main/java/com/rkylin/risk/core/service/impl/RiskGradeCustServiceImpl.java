package com.rkylin.risk.core.service.impl;

import com.mysql.jdbc.StringUtils;
import com.rkylin.risk.core.dao.RiskGradeCustDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.RiskGradeCust;
import com.rkylin.risk.core.service.RiskGradeCustService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * Created by 201508240185 on 2015/9/16.
 */
@Slf4j
@Service
public class RiskGradeCustServiceImpl implements RiskGradeCustService {
  @Resource
  private RiskGradeCustDao riskTotalCustomerDao;

  @Override
  public boolean updateRiskGradeList(String ids, String updateGrade, String reason,
      Authorization auth) {
    try {
      if (!StringUtils.isNullOrEmpty(ids)) {
        String[] strs = ids.split(",");
        for (String id : strs) {
          RiskGradeCust grade = new RiskGradeCust();
          switch (updateGrade) {
            case "LOW":
              grade.setCheckgrade("02");
              break;
            case "MIDDLE":
              grade.setCheckgrade("01");
              break;
            case "HIGH":
              grade.setCheckgrade("00");
              break;
            default:
              throw new IllegalArgumentException("无效的" + updateGrade);
          }
          grade.setId(Integer.parseInt(id));
          //修改原因
          grade.setUpdatereason(reason);
          grade.setCommitid(auth.getUserId());
          grade.setCommitname(auth.getRealname());
          grade.setCommittime(DateTime.now());
          //待审核
          grade.setStatus("05");

          riskTotalCustomerDao.update(grade);
        }
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return false;
    }
    return true;
  }

  @Override
  public boolean updateRiskStatusList(String ids, String status, Authorization auth) {
    try {
      if (!StringUtils.isNullOrEmpty(ids)) {
        String[] strs = ids.split(",");
        for (String id : strs) {
          RiskGradeCust grade = riskTotalCustomerDao.queryById(Integer.parseInt(id));
          try {
            switch (status) {
              case "AGREE":
                //审核通过状态该有有效
                grade.setStatus("00");
                //设置上次客户等级为当前等级
                grade.setLastgrade(grade.getGrade());
                //设置当前等级为审核等级
                grade.setGrade(grade.getCheckgrade());
                //审核等级置空
                grade.setCheckgrade("");
                //设置上次时间为createtime
                grade.setLasttime(grade.getCreatetime());
                grade.setCreatetime(DateTime.now());
                break;
              case "DISAGREE":
                grade.setStatus("04"); //审核拒绝状态改为拒绝
                break;
              default:
                throw new IllegalArgumentException("无效的" + status);
            }
          } catch (NullPointerException e) {
            log.info(e.getMessage(), e);
          }
          grade.setCheckid(auth.getUserId());
          grade.setCheckname(auth.getRealname());
          grade.setChecktime(DateTime.now());

          riskTotalCustomerDao.update(grade);
        }
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return false;
    }
    return true;
  }

  @Override
  public RiskGradeCust create(RiskGradeCust grade) {
    return riskTotalCustomerDao.create(grade);
  }

  @Override
  public RiskGradeCust queryByCustRisk(long custid, String riskType) {
    return riskTotalCustomerDao.queryByCustRisk(custid, riskType);
  }

  @Override
  public List<HashMap> queryPayGradeCust(Map<String, Object> map) {
    return riskTotalCustomerDao.queryPayGradeCust(map);
  }

  @Override
  public RiskGradeCust update(RiskGradeCust grade) {
    return riskTotalCustomerDao.update(grade);
  }

  @Override
  public RiskGradeCust queryById(Integer id) {
    return riskTotalCustomerDao.queryById(id);
  }
}
