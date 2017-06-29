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
@EqualsAndHashCode
public class OperateFlow implements Entity {
    private Long id;

    private String checkorderid;

    private String  customerid;

    private String resultstatus;

    private String reason;

    private String riskmsg;

    private String ruleid;

    /**
     * 课程名称
     */
    private String classname;

    /**
     * 课程单价
     */
    private Amount classprice;

    private DateTime createtime;

    private DateTime updatetime;

    private static final long serialVersionUID = 1L;
}