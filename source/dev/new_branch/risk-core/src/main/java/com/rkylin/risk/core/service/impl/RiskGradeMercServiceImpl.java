package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dao.RiskGradeMercDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.RiskGradeMerc;
import com.rkylin.risk.core.service.RiskGradeMercService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * Created by 201508240185 on 2015/9/16.
 */
@Slf4j
@Service("riskGradeMercService")
public class RiskGradeMercServiceImpl implements RiskGradeMercService {
  @Resource
  private RiskGradeMercDao riskGradeMercDao;

  @Override
  public boolean updateRiskGradeList(String ids, String updateGrade, String reason,
      Authorization auth) {
    try {
      if (StringUtils.isNotEmpty(ids)) {
        String[] strs = ids.split(",");
        for (String id : strs) {
          RiskGradeMerc grade = new RiskGradeMerc();
          switch (updateGrade) {
            case "LOW":
              grade.setCheckgrade(Constants.LOW_LEVEL);
              break;
            case "MIDDLE":
              grade.setCheckgrade(Constants.MIDDLE_LEVEL);
              break;
            case "HIGH":
              grade.setCheckgrade(Constants.HIGH_LEVEL);
              break;
            default:
              grade.setCheckgrade(null);
          }
          grade.setId(Integer.parseInt(id));
          grade.setUpdatereason(reason);
          grade.setCommitid(auth.getUserId());
          grade.setCommitname(auth.getRealname());
          grade.setCommittime(DateTime.now());
          grade.setStatus(Constants.MODIFY_UNVERIFY);
          riskGradeMercDao.update(grade);
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
      if (StringUtils.isNotEmpty(ids)) {
        String[] strs = ids.split(",");
        for (String id : strs) {
          RiskGradeMerc grade = riskGradeMercDao.queryById(Integer.parseInt(id));
          switch (status) {
            case "AGREE":
              grade.setStatus(Constants.ACTIVE);
              grade.setLastgrade(grade.getGrade());
              grade.setGrade(grade.getCheckgrade());
              grade.setCheckgrade("");
              grade.setLasttime(grade.getCreatetime());
              grade.setCreatetime(DateTime.now());
              break;
            case "DISAGREE":
              grade.setStatus(Constants.UNTHREAD);
              break;
            default:break;
          }
          grade.setCheckid(auth.getUserId());
          grade.setCheckname(auth.getRealname());
          grade.setChecktime(DateTime.now());
          riskGradeMercDao.update(grade);
        }
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return false;
    }
    return true;
  }

  @Override
  public List<HashMap> queryPayGradeMerc(Map<String, Object> map) {
    return riskGradeMercDao.queryPayGradeMerc(map);
  }

  @Override
  public RiskGradeMerc update(RiskGradeMerc grade) {
    return riskGradeMercDao.update(grade);
  }

  @Override
  public RiskGradeMerc queryById(Integer id) {
    return riskGradeMercDao.queryById(id);
  }
}
