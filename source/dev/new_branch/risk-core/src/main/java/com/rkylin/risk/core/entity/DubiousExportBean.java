package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cuixiaofang on 2016-6-7.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DubiousExportBean {
    private Date warndate;
    private String customnum;
    private String customname;
    private BigDecimal txnaccount;
    private String warnstatus;
    private String risklevel;
    private String rulename;
    private String dealopinion;

}
