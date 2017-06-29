package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OperateFlow implements Entity {
  private Long id;

  private String checkorderid;

  private String customerid;

  private String resultstatus;

  private String reason;

  private String riskmsg;

  private String ruleid;

  /**
   * 课程名称
   */
  private String classname;

  /**
   * 课程单价
   */
  private Amount classprice;

  /**
   * 课程Id
   */
  private String courseId;

  /**
   * 学校Id
   */
  private String corporationId;

  /**
   * 学校名称
   */
  private String corporationname;

  /**
   * 课程一级分类
   */
  private String couserStairClassify;

  /**
   * 课程二级分类
   */
  private String courseSecondaryClassify;

  //机构号
  private String constId;

  //是否获取米投报告
  private String mtApiStatus;

  private String mtMsg;

  private String mtReport;

  private String mtAvailableAmount;

  private String mtcreditAmount;

  private String mtcreidtScore;

  private String mtmainstatus;

  private String mtverifystatus;

  private String mtverifyreason;

  private DateTime createtime;

  private DateTime updatetime;

  private String isstudent;

  private String schoolarea;

  private String enroldate;

  private String finishschool;

  private String workcompanyname;

  private String workcompanynature;

  private String workcompanyaddress;

  private String worktitle;

  private String urlkeys;

  private String creditcard;
  private static final long serialVersionUID = 1L;
}