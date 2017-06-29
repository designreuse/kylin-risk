package com.rkylin.risk.core.dto;

import com.rkylin.risk.core.entity.Risklevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by 201508240185 on 2015/9/14.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RiskLevelBean {
    private List<Risklevel> levels;
}
