package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * Created by 201508031790 on 2015/9/18.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class IdCardBlackList implements Entity {

  private Integer id;

  private String name;

  private String identtype;

  private String identnum;

  private String status;

  private Short userid;

  private String username;

  private DateTime createtime;

  private Short checkid;

  private String checkname;

  private DateTime checktime;

  private String reason;

  private static final long serialVersionUID = 1L;
}
