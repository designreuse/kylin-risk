package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Account implements Entity {
    private Integer id;

    private String accountid;

    private String userid;

    private String bizsysid;

    private String accounttype;

    private String accountccy;

    private Amount accountbalance;

    private String status;

    private String customerid;

    private String merchid;

    private String delreason;

    private static final long serialVersionUID = 1L;
}