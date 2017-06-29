package com.rkylin.risk.batch.write;

import com.rkylin.risk.core.entity.RiskGradeCust;
import com.rkylin.risk.core.service.RiskGradeCustService;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 201508240185 on 2015/10/27.
 */
public class CustomerFactorWirte implements ItemWriter<RiskGradeCust> {
  @Resource
  private RiskGradeCustService riskGradeCustService;

  @Override
  public void write(List list) throws Exception {
    for (int i = 0; i < list.size(); i++) {
      RiskGradeCust grade = (RiskGradeCust) list.get(i);
      //创建客户总得分
      if (grade.getId() == null) {
        riskGradeCustService.create(grade);
      } else {
        riskGradeCustService.update(grade);
      }
    }
  }
}
