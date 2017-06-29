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
public class Rule implements Entity {
    private Short id;

    private Short groupid;

    private String rulename;

    private String rulecode;

    private String ruleclass;

    private String status;

    private Short createoperid;

    private String createopername;

    private String priority;

    private String remark;

    private String result;

    private String fields;

    private String conditions;

    private String conditionvals;

    private String logicsym;

    private DateTime createtime;

    private DateTime updatetime;

    private Group group;

    private static final long serialVersionUID = 1L;
}