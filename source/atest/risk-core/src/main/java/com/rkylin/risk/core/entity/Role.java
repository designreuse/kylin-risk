package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Role implements Entity {
    private Short id;

    private String rolename;

    private String remark;

    private String status;

    private static final long serialVersionUID = 1L;
}