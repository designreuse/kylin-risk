package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class OperatorRole implements Entity {
    private Short id;

    private Short operatorid;

    private Short roleid;

    private static final long serialVersionUID = 1L;
}