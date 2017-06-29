package com.rkylin.risk.batch.web.entity;

/**
 * Created by tomalloc on 16-2-26.
 */
public class ScheduleJob {
  /** 任务id */
  private String jobId;
  /** 任务名称 */
  private String jobName;
  /** 任务分组 */
  private String jobGroup;
  /** 任务状态 0禁用 1启用 2删除*/
  private String jobStatus;
  /** 任务运行时间表达式 */
  private String cronExpression;
  /** 任务描述 */
  private String desc;
  /**
   * 是否并行执行
   */
  private boolean concurrent = true;



}
