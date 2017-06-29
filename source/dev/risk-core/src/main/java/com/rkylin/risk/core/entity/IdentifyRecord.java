package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class IdentifyRecord implements Entity {
    private Long id;

    private String customerid;

    private String interfacename;

    private String md5checksum1;

    private String md5checksum2;

    private String status;

    private String code;

    private String name;

    private String year;

    private String month;

    private String day;

    private String sex;

    private String number;

    private String address;

    private String authority;

    private String timelimit;

    private String cardnumber;

    private String bankname;

    private String cardtype;

    private String similarity;

    private String checkorderid;

    private String phone;

    private String namecheck;

    private String phonecheck;

    private String phonearea;

    private String phoneoperator;



    private static final long serialVersionUID = 1L;
}