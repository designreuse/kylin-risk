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
public class AbnormalArea implements Entity {
    private Integer id;

    private String code;

    private String provnm;

    private String citynm;

    private String countynm;

    private String name;

    private DateTime updatetime;

    private DateTime createtime;

    private String type;

    private static final long serialVersionUID = 1L;
}