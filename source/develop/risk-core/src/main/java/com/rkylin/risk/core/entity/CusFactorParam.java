package com.rkylin.risk.core.entity;

/**
 * Created by 201508031790 on 2015/10/22.
 */

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class CusFactorParam implements Entity {
  private Integer id;
  private Long custid;
  private String customerid;
  private DateTime rectime;
  private Amount drecamount;
  private Short drecnum;
  private Amount drecmaxamount;
  private String drecmaxtype;
  private String trxwrongflag;
  private String drecmaxbankid;
  private String drecmaxcardno;
  private String remark1;
  private String remark2;
  private String remark3;
  private String remark4;
  private String remark5;
  private String remark6;
  private String remark7;
  private String remark8;
  private String remark9;
  private String remark10;
  private static final long serialVersionUID = 1L;
}
