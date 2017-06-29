package com.rkylin.risk.batch.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 201508240185 on 2015/10/28.
 */
@Slf4j
@Component("customerFactorSchedul")
public class CustomerFactorSchedul {

  @Resource
  private JobLauncher jobLauncher;

  @Resource
  private Job customerFactorJob;

  public void runCustomerFactor() {

    JobParametersBuilder paras = new JobParametersBuilder();
    Date createtime = LocalDate.now().minusDays(1).toDate();
    paras.addDate("createtime", createtime);
    try {
            /* 运行Job */
      log.info(LocalDate.now().minusDays(1).toString("YYYY-MM-dd") + ",客户基本信息跑批开始：");
      jobLauncher.run(customerFactorJob, paras.toJobParameters());
            /* 处理结束，控制台打印处理结果 */
      log.info(LocalDate.now().minusDays(1).toString("YYYY-MM-dd") + ",客户基本信息跑批结束！");
    } catch (Exception e) {
      log.info(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
