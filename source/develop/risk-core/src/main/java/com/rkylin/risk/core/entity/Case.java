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
public class Case implements Entity {
    private Integer id;

    private String casename;

    private String casetype;

    private String casedesc;

    private DateTime createtime;

    private Short operatorid;

    private String operatorname;

    private String status;

    private static final long serialVersionUID = 1L;
}