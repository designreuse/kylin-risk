package com.rkylin.risk.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 201508031790 on 2015/9/25.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AmlDubiousTxnExportBean {

     private String customnum;
     private String customname;
     private String warnnum;
     private  String txncount;
     private  String txnaccount;
     private String risklevel;
     private String warndate;
     private String ruleid;
     private String dealopinion;
     private String source;
}
