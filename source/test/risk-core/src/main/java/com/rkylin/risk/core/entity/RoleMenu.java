package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class RoleMenu implements Entity {
    private Short id;

    private Short roleid;

    private Short menuid;

    private static final long serialVersionUID = 1L;
}