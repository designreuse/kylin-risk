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
public class RiskEvent implements Entity {
    private Integer id;

    private Integer cusid;

    private String customerid;

    private String eventcode;

    private String eventname;

    private String eventtype;

    private String eventsource;

    private String eventdesc;

    private String eventresult;

    private String filepath;

    private DateTime createtime;

    private Short operatorid;

    private String operatorname;

    private String status;

    private static final long serialVersionUID = 1L;
}