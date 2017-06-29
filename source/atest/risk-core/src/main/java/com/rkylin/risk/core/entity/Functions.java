package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Functions implements Entity {
    private Short id;

    private String functionname;

    private String functiontype;

    private String url;

    private String checkflag;

    private String status;

    private Short menuid;

    private static final long serialVersionUID = 1L;
}