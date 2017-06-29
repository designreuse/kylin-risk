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
public class AbnormalCountrycode implements Entity {
    private Integer id;

    private String cnnm;

    private String cnabb;

    private String ennm;

    private String enabb;

    private String twoletter;

    private String threelteeer;

    private String threenum;

    private String type;

    private Short lstmtausr;

    private DateTime updatetime;

    private DateTime createtime;

    private String username;

    private static final long serialVersionUID = 1L;
}