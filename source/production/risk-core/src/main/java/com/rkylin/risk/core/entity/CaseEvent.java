package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class CaseEvent implements Entity {
    private Integer id;

    private Integer caseid;

    private Integer eventid;

    private String status;

    private static final long serialVersionUID = 1L;
}