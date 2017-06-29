package com.rkylin.risk.batch.scheduleTest;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 201508031790 on 2015/10/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/risk-spring.xml"})
@ActiveProfiles("develop")
@Slf4j
public class CustomerTest extends AbstractJUnit4SpringContextTests {

  @Test
  public void depoitTest() {
    JobLauncher launcher = (JobLauncher) applicationContext.getBean("jobLauncher");
    Job job = (Job) applicationContext.getBean("customerFactorJob");
    JobParametersBuilder paras = new JobParametersBuilder();
    String tim = LocalDate.now().minusDays(1).toString("YYYY-MM-dd");
    paras.addString("createtime", tim);
    try {
            /* 运行Job */
      JobExecution result = launcher.run(job, paras.toJobParameters());
            /* 处理结束，控制台打印处理结果 */
      System.out.println(result.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
