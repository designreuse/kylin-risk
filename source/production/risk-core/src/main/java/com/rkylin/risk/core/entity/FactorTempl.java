package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class FactorTempl implements Entity {
    private Short id;

    private Short groupid;

    private Group group;

    private String name;

    private String code;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}