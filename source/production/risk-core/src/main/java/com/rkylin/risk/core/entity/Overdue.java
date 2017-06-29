package com.rkylin.risk.core.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ChenFumin on 2016-8-30.
 */
@Setter
@Getter
public class Overdue implements Entity {
  private String overdueRate;
}
