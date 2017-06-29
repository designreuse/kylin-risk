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
public class AbnorIndustry implements Entity {
    private Integer id;

    private String codeid;

    private String codenm;

    private String type;

    private Short lastmtauser;

    private DateTime updatetime;

    private DateTime createtime;

    private static final long serialVersionUID = 1L;
}