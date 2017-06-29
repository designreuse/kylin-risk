package com.rkylin.risk.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/31
 * version: 1.0
 */
@Data
@EqualsAndHashCode(of = "code")
public class RestResultDTO {
    private int code;
    private String message;
    private Object data;

}
