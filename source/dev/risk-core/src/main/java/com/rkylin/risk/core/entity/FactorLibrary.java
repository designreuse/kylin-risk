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
public class FactorLibrary implements Entity {
    private Short id;

    private String name;

    private String code;

    private String type;

    private String status;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}