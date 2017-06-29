package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class WarningSet implements Entity {
    private Integer id;

    private Short operatorid;

    private String username;

    private LocalDate effdate;

    private LocalDate expdate;

    private String risklevel;

    private String warntype;

    private String status;

    private static final long serialVersionUID = 1L;
}