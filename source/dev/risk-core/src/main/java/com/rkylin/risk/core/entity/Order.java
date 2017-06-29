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
@EqualsAndHashCode(of = {"id"})
public class Order implements Entity {
    private Long id;

    private String orderid;

    private String checkorderid;

    private String ordertypeid;

    private LocalDate orderdate;

    private DateTime ordertime;

    private String rootinstcd;

    private String productid;

    private String userid;

    private String providerid;

    private String userorderid;

    private String userrelateid;

    private Amount amount;

    private String mobile;

    private String cardNum;

    private String identityCard;

    private String remark;

    private String ordercontrol;

    private String respcode;

    private String statusid;

    private String goodsname;

    private String goodsdetail;

    private Integer goodscnt;

    private Amount unitprice;

    private String channel;

    private DateTime createtime;

    private DateTime updatetime;

    private DateTime gradetime;

    private Amount score;

    private Integer dubioustxnid;

    private Integer riskeventid;

    private Customerinfo customerinfo;

    private String riskstatus;

    private String businesstype;

    private String svcid;

    private String userrelatename;

    private String urlkey;

    private String longitude;

    private String latitude;

    private String fundprovider;

    private String loanterm;

    private static final long serialVersionUID = 1L;
}