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
public class Riskrule implements Entity {
    private Integer id;

    private String rulename;

    private String status;

    private String category;

    private String discribe;

    private DateTime createtime;

    private String businesstype;

    private String behavior;

    private String risktype;

    private String riskscore;

    private DateTime updatetime;

    private Short userid;

    private String remark;

    private String username;

    private String filename;

    private static final long serialVersionUID = 1L;
}