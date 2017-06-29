package com.rkylin.risk.batch.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 201508240185 on 2015/11/16.
 */
@Slf4j
@Component("custRiskSchedul")
public class CustRiskSchedule {
  @Resource
  private JobLauncher jobLauncher;

  @Resource
  private Job custRiskJob;

  public void runCustRiskSchedule() {
    try {
            /* 运行Job */
      log.info(LocalDate.now().minusDays(1).toString("YYYY-MM-dd") + ",客户历史高风险跑批开始：");
      jobLauncher.run(custRiskJob, new JobParameters());
            /* 处理结束，控制台打印处理结果 */
      log.info(LocalDate.now().minusDays(1).toString("YYYY-MM-dd") + ",客户历史高风险跑批结束！");
    } catch (Exception e) {
      log.info(e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
