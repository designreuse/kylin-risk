package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by cuixiaofang on 2016-6-6.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderScoreExportBean {
    private Timestamp ordertime;
    private String orderid;
    private String userid;
    private String customername;
    private BigDecimal amount;
    private BigDecimal score;
    private String goodsname;
    private String goodsdetail;
    private Integer goodscnt;
    private BigDecimal unitprice;
    private String risklevel;
}
