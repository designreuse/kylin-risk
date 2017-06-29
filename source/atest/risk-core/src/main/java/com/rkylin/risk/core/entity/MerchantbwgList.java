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
@EqualsAndHashCode
public class MerchantbwgList implements Entity {
    private Integer id;

    private Integer merchid;

    private String merchantid;

    private String merchantname;

    private String source;

    private String type;

    private LocalDate effecttime;

    private LocalDate failuretime;

    private String status;

    private Short userid;

    private String username;

    private DateTime updatetime;

    private String reason;

    private Merchant merchant;

    private DateTime createtime;

    private Short checkid;

    private String checkname;

    private DateTime checktime;

    private static final long serialVersionUID = 1L;
}