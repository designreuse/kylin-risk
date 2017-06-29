package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dao.RiskLevelDao;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Risklevel;
import com.rkylin.risk.core.service.RiskLevelService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 201508240185 on 2015/9/11.
 */
@Slf4j
@Service("riskLevelService")
public class RiskLevelServiceImpl implements RiskLevelService {
  @Resource
  private RiskLevelDao riskLevelDao;

  @Override
  public List<Risklevel> queryByRiskAndCustType(String riskType, String customType) {
    return riskLevelDao.queryByRiskAndCustType(riskType, customType);
  }

  @Override
  @Transactional
  public List<Risklevel> insertOrUpdateRiskGrade(List<Risklevel> levels, Authorization auth,
      String operType, String riskType, String customerType) {
    try {
      for (Risklevel level : levels) {
        level.setUserid(auth.getUserId());
        level.setUsername(auth.getUsername());
        level.setUpdatetime(DateTime.now());
        //风控类型
        level.setRisktype(riskType);
        //客户类型
        level.setCustomertype(customerType);
        //如果操作类型是提交（包括第一次提交和修改）
        if ("COMMIT".equals(operType)) {
          //如果第一次提交，ID为空，生成信息
          if (level.getId() == null) {
            riskLevelDao.insert(level);
          } else {
            //非第一次提交，设置修改信息
            //将修改的分数存入待审核分数
            level.setCheckscore(level.getScore());
            //原分数置为null，不做修改
            level.setScore(null);
            //原计算分数置为null，不做修改
            level.setAccount(null);
            riskLevelDao.update(level);
          }
        }
        //如果操作类型是审核通过
        if ("AGREE".equals(operType)) {
          //将待审核分数存入分数
          level.setScore(level.getCheckscore());
          //设置计算分数为评分*权重
          level.setAccount(
              new Amount(level.getCheckscore()).multiply(new Amount(level.getWeight())).toString());
          //待审核分数清空
          level.setCheckscore("");
          riskLevelDao.update(level);
        }
        //如果操作类型是审核拒绝
        if ("DISAGREE".equals(operType)) {
          //待审核分数清空
          level.setCheckscore("");
          riskLevelDao.update(level);
        }
      }
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      return new ArrayList<>();
    }
    return levels;
  }

  @Override
  public Risklevel insert(Risklevel level) {
    return riskLevelDao.insert(level);
  }

  @Override
  public Risklevel update(Risklevel level) {
    return riskLevelDao.update(level);
  }
}
