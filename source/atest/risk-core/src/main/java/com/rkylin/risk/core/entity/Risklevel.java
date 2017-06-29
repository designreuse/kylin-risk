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
public class Risklevel implements Entity {
    private Integer id;

    private String target;

    private String weight;

    private String project;

    private String classdesc;

    private String classscore;

    private String score;

    private String checkscore;

    private String account;

    private String remark;

    private String customertype;

    private String risktype;

    private Short userid;

    private String username;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}