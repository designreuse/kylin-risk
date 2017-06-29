package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RuleHis implements Entity {
    private Short id;

    private Short groupversionid;

    private String rulename;

    private String rulecode;

    private String ruleclass;

    private String status;

    private String priority;

    private String remark;

    private String result;

    private String fields;

    private String conditions;

    private String conditionvals;

    private String logicsym;

    private Short updateoperid;

    private String updateopername;

    private DateTime createtime;

    private DateTime updatetime;

    private GroupVersion groupVersion;

    private static final long serialVersionUID = 1L;
}