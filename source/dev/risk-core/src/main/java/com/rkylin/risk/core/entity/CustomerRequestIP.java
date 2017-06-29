package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CustomerRequestIP implements Entity {
    private Long id;

    private String customerid;

    private String requestip;

    private String constid;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}