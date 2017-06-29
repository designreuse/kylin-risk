package com.rkylin.risk.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * Created by wjr on 2016-8-24.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class CourseType implements Entity {

  private Integer id;

  private String coursetypename;

  private DateTime createtime;

  private DateTime updatetime;
}
