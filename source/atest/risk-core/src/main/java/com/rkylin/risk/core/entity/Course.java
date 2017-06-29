package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Course implements Entity {
    private Integer id;

    private String merchantId;

    private String merchantName;

    private String courseName;

    private String coursePrice;

    private String courseType;

    private String courseTime;

    private String stuAge;

    private Date createdTime;

    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}