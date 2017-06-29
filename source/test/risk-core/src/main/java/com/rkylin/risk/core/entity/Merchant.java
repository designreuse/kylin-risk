package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Merchant implements Entity {
    private Integer id;

    private String merchantid;

    private String merchantname;

    private String channelid;

    private String checkorderid;

    private String corpname;

    private String corptype;

    private String corpproperty;

    private String industry;

    private String registrationorga;

    private String registfinance;

    private String province;

    private String city;

    private String district;

    private LocalDate merchstartdate;

    private LocalDate merchduedate;

    private String corporationid;

    private String ownercerttp;

    private String ownercertid;

    private String ownercertname;

    private String mobilephone;

    private String orginstdid;

    private String taxid;

    private String arrpid;

    private String icpid;

    private String merchantaddress;

    private String creditend;

    private Amount creditline;

    private Integer provincenum;

    /**
     * 分支机构数量
     */
    private Integer subagencynum;
    /**
     * 分支机构标记
     */
    private String branchflag;

    /**
     * 成立年限
     */
    private Amount foundage;
    /**
     * 年培训人次
     */
    private Amount trainnumyear;
    /**
     * 年培训收入
     */
    private Amount trainincomeyear;
    /**
     * 教学总面积
     */
    private Amount areateach;

    private Amount classprice;

    private Integer employeeno;

    private String merchantstatus;

    private String checkstatus;

    private String description;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}