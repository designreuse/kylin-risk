package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Reptile implements Entity {
  private Long id;

  private Long verifyId;

  private String checkorderid;

  private String type;

  private String name;

  private String identity;

  private Integer timeout;

  private Integer lainum;

  private Integer loanblacknum;

  private Integer creditnetnum;

  private Integer executionnetnum;

  private Integer refereenetnum;

  private Integer baidunum;

  private Integer sogounum;

  private Integer threesearchnum;

  private Integer baseinfonum;

  private Integer shareholdernum;

  private Integer keypersionnum;

  private Integer buschangenum;

  private Integer judicialnum;

  private Integer courtnum;

  private Integer execupersonnum;

  private Integer dishonestnum;

  private Integer judicialsalenum;

  private Integer operabnormalnum;

  private Integer taxinfonum;

  private Integer outstocknum;

  private Integer chattelmortnum;

  private Integer announnum;

  private Integer investabroadnum;

  private Integer beedata;

  private DateTime createtime;

  private DateTime updatetime;

  private static final long serialVersionUID = 1L;
}