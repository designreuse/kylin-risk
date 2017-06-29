package com.rkylin.risk.operation.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Getter
@Setter
@ToString
public class PersonMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resultCode;

    private String resultScore;

    private String resultMsg;

}
