package com.rkylin.risk.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 201508031790 on 2015/9/24.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DubioustxnExportBean {
    private String userid;
    private String customername;
    private String txnid;
    private String merchid;
    private String dbtridnum;
    private String txntime;
    private String txnamt;
    private String risklevel;
    private String prodid;
    private String bizsysid;


}
