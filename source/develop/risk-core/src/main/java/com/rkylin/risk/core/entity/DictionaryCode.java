package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class DictionaryCode implements Entity {
    private Integer id;

    private String dictcode;

    private String dictname;

    private String code;

    private String name;

    private static final long serialVersionUID = 1L;
}