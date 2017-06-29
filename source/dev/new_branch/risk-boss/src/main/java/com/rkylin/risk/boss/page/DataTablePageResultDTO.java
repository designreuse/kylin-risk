package com.rkylin.risk.boss.page;

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
public class DataTablePageResultDTO extends DataTableResultDTO {
    private String draw;
    private long recordsTotal;
    private long recordsFiltered;
}
