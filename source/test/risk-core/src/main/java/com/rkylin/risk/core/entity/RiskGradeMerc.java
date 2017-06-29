package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RiskGradeMerc implements Entity {
  private Integer id;

  private String merchid;

  private String merchantid;

  private String merchantname;

  private String totalscore;

  private String grade;

  private String checkgrade;

  private String lastgrade;

  private DateTime lasttime;

  private String ototalscore;

  private String status;

  private String risktype;

  private Short commitid;

  private String commitname;

  private DateTime committime;

  private Short checkid;

  private String checkname;

  private DateTime checktime;

  private String updatereason;

  private DateTime createtime;

  private DateTime updatetime;

  private static final long serialVersionUID = 1L;
}