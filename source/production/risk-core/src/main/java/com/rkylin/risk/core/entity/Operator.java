package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;


@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Operator implements Entity {
    private Short id;

    private String username;

    private String realname;

    private  String email;

    private String passwd;

    private String phone;

    private String opertype;

    private LocalDate passwdeffdate;

    private LocalDate passwdexpdate;

    private DateTime createtime;

    private String createoper;

    private String status;

    private LocalDate lastpwderrdate;

    private Short errtimes;

    private String products;

    private static final long serialVersionUID = 1L;
}