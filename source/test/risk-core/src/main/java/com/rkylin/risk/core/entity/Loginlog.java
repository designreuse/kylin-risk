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
public class Loginlog implements Entity {
    private Integer id;

    private Short operatorid;

    private String username;

    private String loginip;

    private DateTime logintime;

    private DateTime logouttime;

    private static final long serialVersionUID = 1L;
}