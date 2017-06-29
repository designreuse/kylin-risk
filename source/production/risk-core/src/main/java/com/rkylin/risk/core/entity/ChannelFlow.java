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
public class ChannelFlow implements Entity {
    private Integer id;

    private String channelcode;

    private String channelname;

    private String productcode;

    private String productname;

    private String crawler;

    private String idcardvalidate;

    private String bankcardvalidate;

    private String idcardhandvalidate;

    private String mitou;

    private String bairongeducational;

    private String bairongspecial;

    private String bairongquery;

    private String unionpayadvisors;

    private String spare1;

    private String spare2;

    private String spare3;

    private String operator;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}