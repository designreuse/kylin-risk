package com.rkylin.risk.core.entity;

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
public class CustomebwgList implements Entity {

    private Integer id;

    private Long custid;

    private String customerid;

    private String customername;

    private String accountnum;

    private DateTime opendate;

    private LocalDate failuretime;

    private LocalDate effecttime;

    private String status;

    private String type;

    private String source;

    private Short userid;

    private String username;

    private DateTime updatetime;

    private String reason;

    private Customerinfo customerinfo;

    private DateTime createtime;

    private Short checkid;

    private String checkname;

    private DateTime checktime;

    private static final long serialVersionUID = 1L;
}