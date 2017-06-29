package com.rkylin.risk.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by cuixiaofang on 2016-7-18.
 */
@Getter
@Setter
@ToString
public class Business {
    /**
     * 申请期限
     */
    private String reqesttime;
    /**
     * 申请者单位名称
     */
    private String orderplatformname;
    /**
     * 费率模板ID
     */
    private String ratetemplrate;

}
