package com.rkylin.risk.batch.hello;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/10/27 version: 1.0
 */
public class HelloJobExecutionListener implements JobExecutionListener {
  @Override
  public void beforeJob(JobExecution jobExecution) {
    System.out.println("job start");
    System.out.println("getJobConfigurationName:" + jobExecution.getJobConfigurationName());
    System.out.println("getExitStatus:" + jobExecution.getExitStatus());
    System.out.println("getStatus:" + jobExecution.getStatus());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    System.out.println("job end");
    System.out.println("getJobConfigurationName:" + jobExecution.getJobConfigurationName());
    System.out.println("getExitStatus:" + jobExecution.getExitStatus());
    System.out.println("getStatus:" + jobExecution.getStatus());
  }
}
