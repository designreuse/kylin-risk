package com.rkylin.risk.spring.handler.page;

import lombok.Getter;
import lombok.Setter;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/9/1
 * version: 1.0
 */
@Setter
@Getter
public class DataTablePageResultDTO extends DataTableBaseResultDTO {
    private String draw;
    private int recordsTotal;
    private int recordsFiltered;
}
