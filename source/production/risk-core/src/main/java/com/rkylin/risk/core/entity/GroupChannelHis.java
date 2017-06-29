package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GroupChannelHis implements Entity {
    private Short id;

    private Short grouphisid;

    private String channelcode;

    private String channelname;

    private String productcode;

    private String productname;

    private static final long serialVersionUID = 1L;
}