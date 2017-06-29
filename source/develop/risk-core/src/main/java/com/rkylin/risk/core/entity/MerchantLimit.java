package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MerchantLimit implements Entity {
  private Integer id;

  private String userrelateid;

  private String constid;

  private LocalDate orderDate;

  private DateTime orderTime;

  private Amount daypay;

  private Amount monthpay;

  private Amount seasonpay;

  private Amount yearpay;

  private Integer dayloannum;

  private Integer monloannum;

  private Integer sealoannum;

  private Integer yearloannum;

  private DateTime createtime;

  private DateTime updatetime;

  private long version;

  private static final long serialVersionUID = 1L;
}