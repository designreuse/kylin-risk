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
public class LoanFundsLimit implements Entity {
    private Integer id;

    private Float repayyear;

    private Integer annualsales;

    private String profilerate;

    private Double ebitda;

    private String companytype;

    private Double loanlimit;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}
