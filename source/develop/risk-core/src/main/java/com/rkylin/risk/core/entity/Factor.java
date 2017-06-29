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
public class Factor implements Entity {
    private Integer id;

    private String name;

    private String code;

    private String weight;

    private String description;

    private String score;

    private String account;

    private String remark;

    private String customertype;

    private String risktype;

    private String factorlevel;

    private Integer factorid;

    private String moduletype;

    private String status;

    private Short userid;

    private String username;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}