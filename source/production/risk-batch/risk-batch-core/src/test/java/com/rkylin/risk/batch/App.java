package com.rkylin.risk.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/23 version: 1.0
 */
public class App {
  public static void main(String[] args) {
    AbstractApplicationContext context =
        new ClassPathXmlApplicationContext("/risk-spring-test/spring-batch-customer.xml");
    JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
    Job job = (Job) context.getBean("customerFactorJob");
    try {
            /* 运行Job */
      JobExecution result = launcher.run(job, new JobParameters());
            /* 处理结束，控制台打印处理结果 */
      System.out.println(result.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
