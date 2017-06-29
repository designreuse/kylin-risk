package com.rkylin.risk.core.entity;

import com.rkylin.risk.commons.entity.Amount;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by 201508031790 on 2015/10/29.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class ScoreLevel implements Entity {
  private Short id;
  private Amount scoremin;
  private Amount scoremax;
  private String risklevel;
}
