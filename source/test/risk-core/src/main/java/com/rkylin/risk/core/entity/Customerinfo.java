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

    private String firstid;

    private String firstrelation;

    private String secondman;

    private String secondmobile;

    private String secondid;

    private String secondrelation;

    private String bankname;

    private String bancode;

    private String bankbranch;

    private String nation;

    private String address;

    private String qqnum;

    private String email;

    private String jdnum;

    private String alipaynum;

    private String taccountprovince;

    private String taccountcity;

    //配偶性别 &杏仁
    private String matesex;
    //配偶民族 &杏仁
    private String matenation;
    //配偶地址  &杏仁
    private String mateaddress;
    //配偶身份证生效日期  &杏仁
    private String matestartdate;
    //配偶身份证失效日期  &杏仁
    private String mateenddate;
    //身份证正反面urlkey  &杏仁
    private String mateurlkey;
    //孩子姓名  &杏仁
    private String childname;
    //孩子证件号  &杏仁
    private String childcardid;

    private static final long serialVersionUID = 1L;
}