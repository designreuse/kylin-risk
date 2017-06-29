package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Customerinfo implements Entity {
    private Long id;

    private String customerid;

    private String customername;

    private String age;

    private String certificatetype;

    private String certificateid;

    private String userrelatedid;

    private String channel;

    private String certificatestartdate;

    private String certificateexpiredate;

    private String tcaccount;

    private String mobilephone;

    private String isauthor;

    private String education;

    private Amount score;

    private String occupation;

    private DateTime createtime;

    private DateTime updatetime;

    private String urlkey;

    private String firstman;

    private String firstmobile;

    private String secondman;

    private String secondmobile;

    private static final long serialVersionUID = 1L;
}