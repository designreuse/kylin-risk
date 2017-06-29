package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;


@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Dubioustxn implements Entity {
  private Integer id;

  private String txncount;

  private Amount txnaccount;

  private String risklevel;

  private LocalDate warndate;

  private String ruleid;

  private String customnum;

  private String customname;

  private String dealopinion;

  private String warnstatus;

  private String txnum;

  private String certificateid;

  private String telephonenum;

  private String bankcardnum;

  private String productid;

  private static final long serialVersionUID = 1L;
}