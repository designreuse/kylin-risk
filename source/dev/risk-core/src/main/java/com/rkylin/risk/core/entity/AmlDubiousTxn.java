package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class AmlDubiousTxn implements Entity {
    private Integer id;

    private String warnnum;

    private String txncount;

    private Amount txnaccount;

    private String risklevel;

    private LocalDate warndate;

    private Integer ruleid;

    private String customnum;

    private String customname;

    private String dealopinion;

    private String source;

    private String filepath;

    private static final long serialVersionUID = 1L;
}