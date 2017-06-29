package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class ModuelsChannel implements Entity {
    private Integer id;

    private String moduletype;

    private String channelcode;

    private String mercid;

    private String status;

    private static final long serialVersionUID = 1L;
}