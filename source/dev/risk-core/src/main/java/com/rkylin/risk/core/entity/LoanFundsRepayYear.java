package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class LoanFundsRepayYear implements Entity {
    private Integer id;

    private String customertype;

    private Integer annualsalesupper;

    private Integer annualsaleslower;

    private String companytype;

    private Float repayyear;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}
